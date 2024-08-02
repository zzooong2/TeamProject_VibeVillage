package kr.co.vibevillage.database.controller;

import kr.co.vibevillage.database.service.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    private final TestServiceImpl testService;

    @Autowired
    public TestController(TestServiceImpl testService) {
        this.testService = testService;
    }

    @RequestMapping("/test")
    public String databaseConnectionTest() {
        return "/usedBoard/usedBoardCreate";
    }
}
