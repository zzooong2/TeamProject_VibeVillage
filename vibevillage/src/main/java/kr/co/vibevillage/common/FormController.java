package kr.co.vibevillage.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class FormController {
//    @Autowired
//    public FormController() {
//
//    }
    @RequestMapping("/boardCreate")
    public String boardCreate() {
        return "/usedBoard/usedBoardCreate";
    }
    @RequestMapping("/boardList")
    public String boardList() {
        return "/usedBoard/usedBoardList";
    }
    @RequestMapping("/boardView")
    public String boardView() {
        return "usedBoardDetail";
    }
}
