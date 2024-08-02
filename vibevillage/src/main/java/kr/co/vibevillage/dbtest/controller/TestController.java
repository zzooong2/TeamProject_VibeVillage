package kr.co.vibevillage.dbtest.controller;

import kr.co.vibevillage.dbtest.service.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    private final TestServiceImpl testService;

    @Autowired
    public TestController(TestServiceImpl testService) {
        this.testService = testService;
    }

    @GetMapping("/test")
    public String databaseConnectionTest(Model model) {
        String result = testService.testXML();

        model.addAttribute("result", result);

        return "thymeleaf/index";
    }
}
