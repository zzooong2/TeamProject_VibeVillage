package kr.co.vibevillage.usedBoard.controller;

import kr.co.vibevillage.common.UploadFile;
import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import kr.co.vibevillage.usedBoard.service.UsedBoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.util.List;

@Controller
@RequestMapping("/used")
public class UsedBoardController {
        private final UploadFile uploadFile;
        private final UsedBoardServiceImpl usedBoardService;
        private final String USE = "usedBoard/usedBoard";


        @Autowired
        public UsedBoardController(UsedBoardServiceImpl usedBoardService, UploadFile uploadFile){
            this.usedBoardService = usedBoardService;
            this.uploadFile = uploadFile;

        }

        @GetMapping("/boardList")
        public String getUsedBoardList(Model model){
            System.out.println("컨트롤러");
            List<UsedBoardDto> usedList = usedBoardService.getUsedBoardList();
            System.out.println("컨트롤러 나옴");
            System.out.println(usedList.size());
            for(UsedBoardDto i : usedList){
                System.out.println(i.getUsedBoardId());
            }
            System.out.println(usedList);
            model.addAttribute("usedList", usedList);
            return USE+"List";
        }
        @GetMapping("/boardCreate")
         public String createUsedBoard(Model model, UsedBoardDto usedBoard){
            model.addAttribute("usedBoard", usedBoard);
        return USE+"Create";
        }

    @PostMapping("/boardEnroll")
    public String enrollUsedBoard(@RequestParam("files") List<MultipartFile> files, @ModelAttribute("usedBoard") UsedBoardDto usedBoard){
        List<UsedBoardImageDto> images = uploadFile.upload(files);
        usedBoard.setImages(images);
        int result = usedBoardService.enrollUsedBoard(usedBoard);

        return USE+"Create";
    }


        @GetMapping("/boardDetail/{id}")
         public String getUsedBoardDetail(Model model, @PathVariable String id){
            System.out.println(id);
        UsedBoardDto board = usedBoardService.getUsedBoardDetail(id);

        model.addAttribute("usedBoard", board);
        return USE+"Detail";
        }


}
