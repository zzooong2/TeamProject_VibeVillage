package kr.co.vibevillage.usedBoard.controller;

import kr.co.vibevillage.common.UploadFile;
import kr.co.vibevillage.common.UsedPagination;
import kr.co.vibevillage.usedBoard.model.UsedBoardCommentDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import kr.co.vibevillage.usedBoard.model.UsedPageInfoDto;
import kr.co.vibevillage.usedBoard.service.UsedBoardCommentServiceImpl;
import kr.co.vibevillage.usedBoard.service.UsedBoardServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import kr.co.vibevillage.env.Env;


import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/used")
public class UsedBoardController {
        private final UsedBoardCommentServiceImpl commentService;
        private final UsedPagination usedPagination;
        private final Env env;
        private final UploadFile uploadFile;
        private final UsedBoardServiceImpl usedBoardService;
        private final String USE = "usedBoard/usedBoard";
    private final UsedBoardCommentServiceImpl usedBoardCommentServiceImpl;

    public String kakaoMap;

        @Autowired
        public UsedBoardController(UsedBoardServiceImpl usedBoardService,
                                   UploadFile uploadFile,
                                   Env env,
                                   UsedPagination usedPagination,
                                   UsedBoardCommentServiceImpl commentService, UsedBoardCommentServiceImpl usedBoardCommentServiceImpl){
            this.usedBoardService = usedBoardService;
            this.uploadFile = uploadFile;
            this.env = env;
            this.kakaoMap = env.test().get("KAKAOMAP");
            this.usedPagination = usedPagination;
            this.commentService = commentService;
            this.usedBoardCommentServiceImpl = usedBoardCommentServiceImpl;
        }
// 게시글 리스트 조회
    @GetMapping("/boardList/{cpage}")
    public String getUsedBoardList(Model model,
                                   @RequestParam(value = "cpage", defaultValue = "1") int cpage) {
     //페이지네이션
        List<UsedBoardDto> count = usedBoardService.getUsedBoardList();
        int listCount = count.size();
        int pageLimit = 10;
        int boardLimit = 20;
        int row = listCount - (cpage-1) * boardLimit;
        UsedPageInfoDto pi = usedPagination.getPageInfo(listCount, cpage, pageLimit, boardLimit);
        List<UsedBoardDto> usedList = usedBoardService.getFilteredUsedBoardList(pi);
        model.addAttribute("usedList", usedList);
        model.addAttribute("pi", pi);
        return USE + "List";
    }
// 게시글 작성 페이지 이동
    @GetMapping("/boardCreate")
         public String createUsedBoard(Model model,UsedBoardDto usedBoard){
            model.addAttribute("kakaoMap",kakaoMap);
            model.addAttribute("usedBoard", usedBoard);
        return USE+"Create";
        }
// 게시글 작성
    @PostMapping("/boardEnroll")
    @ResponseBody
    public ResponseEntity<Map<String, String>> enrollUsedBoard(@RequestParam("mainFile") MultipartFile mainFile,
                                                               @RequestParam("previewFiles") List<MultipartFile> previewFiles,
                                                               @ModelAttribute("usedBoard") UsedBoardDto usedBoard,
                                                               Model model){
        Map<String, String> response = new HashMap<>();
        try {
            model.addAttribute("kakaoMap", kakaoMap);

            List<MultipartFile> mainImages = List.of(mainFile);
            List<UsedBoardImageDto> images = uploadFile.upload(mainImages, previewFiles);
            usedBoard.setImages(images);

            int result = usedBoardService.enrollUsedBoard(usedBoard);

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
         public String getUsedBoardDetail(Model model,
                                          @PathVariable("id") String id,
                                          UsedBoardCommentDto comment){
            int boardId = Integer.parseInt(id);
            UsedBoardDto board = usedBoardService.getUsedBoardDetail(boardId);

            List<UsedBoardImageDto> mainImages = usedBoardService.getUsedBoardMainImage(boardId);
            List<UsedBoardImageDto> subImages = usedBoardService.getUsedBoardSubImage(boardId);
            List<UsedBoardCommentDto> commentList = commentService.getCommentList(boardId);
        model.addAttribute("usedBoard", board);
        model.addAttribute("mainImages", mainImages);
        model.addAttribute("subImages", subImages);
        model.addAttribute("comment",comment);
        model.addAttribute("commentList",commentList);
        model.addAttribute("commentSize",commentList.size());

        return USE+"Detail";
        }
// 게시글 댓글 작성
    @PostMapping("/boardDetail/{id}/put")
    public String putUsedBoardComment(@PathVariable("id") int id,
                                      @ModelAttribute("comment")UsedBoardCommentDto comment){
        System.out.println(id+"보드 아이디");
        System.out.println(comment.getUsedCommentContent());
        comment.setUsedBoardId(id);
        commentService.putComment(comment);
    return "redirect:/used/boardDetail/"+id;
    }

}
