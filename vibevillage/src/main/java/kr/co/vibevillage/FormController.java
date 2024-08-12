package kr.co.vibevillage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/form")
public class FormController {

    @GetMapping("/login")
    public String loginForm() {
        return "member/loginRegister";
    }

    @GetMapping("/myPage")
    public String myPageForm() {
        return "member/myPage";
    }

    // 성오
    @GetMapping("/talentGroupBoard")
    public String talentGroupBoardForm() { return "/talentGroupBoard/talentGroupBoard"; }

    @GetMapping("/exprienceAndReviewBoard")
    public String exprienceAndReviewBoardForm() { return "/exprienceAndReviewBoard/exprienceAndReviewBoard"; }
}
