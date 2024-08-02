package kr.co.vibevillage.talentgroupboard.controller;

import kr.co.vibevillage.database.service.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TalentGroupBoardController {

    private final TestServiceImpl testService;

    @Autowired
    public TalentGroupBoardController(TestServiceImpl testService) {
        this.testService = testService;
    }

    @GetMapping("/boardTest")
    public String databaseConnectionTest() {


        return "\\talentGroupBoard\\talentGroupBoard";
    }

    @GetMapping("/test1")
    public String review() {


        return "\\experienceAndReviewBoard\\experienceAndReviewBoard";
    }
    @GetMapping("/test2")
    public String group() {


        return "\\talentGroupBoard\\createGroup";
    }

    @GetMapping("/test3")
    public String writeReview() {


        return "\\experienceAndReviewBoard\\writeReview.html";
    }
}
