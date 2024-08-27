package kr.co.vibevillage.usedBoard.controller;

import kr.co.vibevillage.common.UploadFile;
import kr.co.vibevillage.common.UsedPagination;
import kr.co.vibevillage.usedBoard.model.UsedBoardCommentDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import kr.co.vibevillage.usedBoard.model.UsedPageInfoDto;
import kr.co.vibevillage.usedBoard.service.UsedBoardCommentServiceImpl;
import kr.co.vibevillage.usedBoard.service.UsedBoardServiceImpl;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@Controller
@RequestMapping("/used")
public class UsedBoardController {
    private final UsedBoardCommentServiceImpl commentService;
    private final UsedPagination usedPagination;
    private final UploadFile uploadFile;
    private final UsedBoardServiceImpl usedBoardService;
    private final LoginServiceImpl loginService;
    private final String USE = "usedBoard/usedBoard";

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
                                                               @ModelAttribute("usedBoard") UsedBoardDto usedBoard) {
        Map<String, String> response = new HashMap<>();
        try {
            UserDTO user = loginService.getLoginUserInfo();
            List<MultipartFile> mainImages = List.of(mainFile);
            List<UsedBoardImageDto> images = uploadFile.upload(mainImages, previewFiles);
            usedBoard.setImages(images);
            usedBoard.setUserNo(user.getUserNo());
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
        UserDTO user = loginService.getLoginUserInfo();
        List<UsedBoardImageDto> mainImages = usedBoardService.getUsedBoardMainImage(id);
        List<UsedBoardImageDto> subImages = usedBoardService.getUsedBoardSubImage(id);
        List<UsedBoardCommentDto> commentList = commentService.getCommentList(id);

        model.addAttribute("usedBoard", board);
        model.addAttribute("mainImages", mainImages);
        model.addAttribute("subImages", subImages);
        model.addAttribute("userNickName",user.getUserNickName());
        model.addAttribute("comment", comment);
        model.addAttribute("commentList", commentList);
        model.addAttribute("commentSize", commentList.size());

        return USE + "Detail";
    }

    // 게시글 댓글 작성
    @PostMapping("/boardDetail/{id}/put")
    public String putUsedBoardComment(@PathVariable("id") int id,
                                      @ModelAttribute("comment") UsedBoardCommentDto comment) {
        UserDTO user = loginService.getLoginUserInfo();
        comment.setUNo(user.getUserNo());
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
    @GetMapping("/my_boards")
    public String getMyBoards(Model model) {
        UserDTO user = loginService.getLoginUserInfo();
        List<UsedBoardDto> usedBoard =  usedBoardService.getMyBoards(user.getUserNo());
        model.addAttribute("usedList",usedBoard);
        return "usedBoard/myUsedBoardList";


    }

    @GetMapping("/update_board/{id}")
    public String updateUsedBoard(@PathVariable("id") int id,Model model){
        UsedBoardDto board = usedBoardService.getUsedBoardDetail(id);
        UserDTO user = loginService.getLoginUserInfo();
        List<UsedBoardImageDto> mainImages = usedBoardService.getUsedBoardMainImage(id);
        List<UsedBoardImageDto> subImages = usedBoardService.getUsedBoardSubImage(id);
        model.addAttribute("usedBoard", board);
        model.addAttribute("mainImages", mainImages);
        model.addAttribute("subImages", subImages);
        model.addAttribute("userNickname",user.getUserNickName());
        return USE + "Update";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateBoard(@ModelAttribute UsedBoardDto usedBoard,
                                                           @RequestParam("mainFile") MultipartFile mainFile,
                                                           @RequestParam("previewFiles") List<MultipartFile> previewFiles) {
        try {
            // 서비스 호출하여 게시물 및 이미지 업데이트
            System.out.println(usedBoard.getUsedBoardId());
            System.out.println(usedBoard.getUsedBoardContent());
            List<MultipartFile> mainImages = List.of(mainFile);
            List<UsedBoardImageDto> images = uploadFile.upload(mainImages, previewFiles);
            usedBoard.setImages(images);
            usedBoardService.updateUsedBoard(usedBoard);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Update successful!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 예외 처리: 예외 메시지를 로그에 기록하고 클라이언트에 응답
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Update failed");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/convert_status/{id}/{status}")
    public String convertProductStatus(@PathVariable(value = "status") String status,
                                       @PathVariable(value = "id" ) int id){
        usedBoardService.convertProductStatus(id,status);

        return "redirect:/used/my_boards";
    }
    @GetMapping("/delete_comment/{commentId}")
    public String deleteComment(@PathVariable("commentId") int commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/used/boardList/1";
    }


}
