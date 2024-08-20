package kr.co.vibevillage.usedBoard.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.vibevillage.common.UploadFile;
import kr.co.vibevillage.common.UsedPagination;
import kr.co.vibevillage.usedBoard.model.UsedBoardCommentDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import kr.co.vibevillage.usedBoard.model.UsedPageInfoDto;
import kr.co.vibevillage.usedBoard.service.UsedBoardCommentServiceImpl;
import kr.co.vibevillage.usedBoard.service.UsedBoardServiceImpl;
import kr.co.vibevillage.user.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/used")
public class UsedBoardController {
    private final UsedBoardCommentServiceImpl commentService;
    private final UsedPagination usedPagination;
    private final UploadFile uploadFile;
    private final UsedBoardServiceImpl usedBoardService;
    private final String USE = "usedBoard/usedBoard";
    public String kakaoMap;

    @Autowired
    public UsedBoardController(UsedBoardServiceImpl usedBoardService,
                               UploadFile uploadFile,
                               UsedPagination usedPagination,
                               UsedBoardCommentServiceImpl commentService) {
        this.usedBoardService = usedBoardService;
        this.uploadFile = uploadFile;
        this.usedPagination = usedPagination;
        this.commentService = commentService;
    }

    // 게시글 리스트 조회
    @GetMapping("/boardList/{cpage}")
    public String getUsedBoardList(Model model,
                                   @PathVariable(value = "cpage") int cpage,
                                   @RequestParam(value = "category", defaultValue = "0") int category,
                                   @RequestParam(value = "province", defaultValue = "") String province,
                                   @RequestParam(value = "citySelect", defaultValue = "") String citySelect) {
        List<UsedBoardDto> count = usedBoardService.getUsedBoardList(category, province, citySelect);
        UsedPageInfoDto pi = usedPagination.getPageInfo(count.size(), cpage, 10, 8);
        List<UsedBoardDto> usedList = usedBoardService.getFilteredUsedBoardList(pi, category, province, citySelect);
        model.addAttribute("usedList", usedList);
        model.addAttribute("pi", pi);
        model.addAttribute("category", category);
        model.addAttribute("province", province);
        model.addAttribute("citySelect", citySelect);
        return USE + "List";
    }

    // 게시글 작성 페이지 이동
    @GetMapping("/boardCreate")
    public String createUsedBoard(Model model, UsedBoardDto usedBoard) {
        model.addAttribute("usedBoard", usedBoard);
        return USE + "Create";
    }

    // 게시글 작성
    @PostMapping("/boardEnroll")
    @ResponseBody
    public ResponseEntity<Map<String, String>> enrollUsedBoard(@RequestParam("mainFile") MultipartFile mainFile,
                                                               @RequestParam("previewFiles") List<MultipartFile> previewFiles,
                                                               @ModelAttribute("usedBoard") UsedBoardDto usedBoard,
                                                               Model model
                                                               ) {
        Map<String, String> response = new HashMap<>();


        try {
            model.addAttribute("kakaoMap", kakaoMap);

            List<MultipartFile> mainImages = List.of(mainFile);
            List<UsedBoardImageDto> images = uploadFile.upload(mainImages, previewFiles);
            usedBoard.setImages(images);
            usedBoardService.enrollUsedBoard(usedBoard);
            response.put("message", "Upload successful!");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Upload failed!");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게시글 조회
    @GetMapping("/boardDetail/{id}")
    public String getUsedBoardDetail(@PathVariable("id") int id,
                                     UsedBoardCommentDto comment,
                                     Model model) {
        int result = usedBoardService.increaseViewCount(id);
        UsedBoardDto board = usedBoardService.getUsedBoardDetail(id);

        List<UsedBoardImageDto> mainImages = usedBoardService.getUsedBoardMainImage(id);
        List<UsedBoardImageDto> subImages = usedBoardService.getUsedBoardSubImage(id);
        List<UsedBoardCommentDto> commentList = commentService.getCommentList(id);

        model.addAttribute("usedBoard", board);
        model.addAttribute("mainImages", mainImages);
        model.addAttribute("subImages", subImages);
        model.addAttribute("comment", comment);
        model.addAttribute("commentList", commentList);
        model.addAttribute("commentSize", commentList.size());

        return USE + "Detail";
    }

    // 게시글 댓글 작성
    @PostMapping("/boardDetail/{id}/put")
    public String putUsedBoardComment(@PathVariable("id") int id,
                                      @ModelAttribute("comment") UsedBoardCommentDto comment) {
        comment.setUsedBoardId(id);
        commentService.putComment(comment);
        return "redirect:/used/boardDetail/" + id;
    }

    // 게시글 삭제
    @GetMapping("/delete_board/{id}")
    public String deleteUsedBoard(@PathVariable("id") int id) {
        int result = usedBoardService.deleteDetail(id);
        return "redirect:/used/boardList/1";
    }


}
