package kr.co.vibevillage.customerServiceBoard.controller;

import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;
import kr.co.vibevillage.customerServiceBoard.service.CustomerServiceService;
import kr.co.vibevillage.customerServiceBoard.service.CustomerServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sound.midi.Soundbank;
import java.util.List;

@Controller
public class CustomerServiceController {

    private final CustomerServiceServiceImpl customerServiceService;

    @Autowired
    public CustomerServiceController(CustomerServiceServiceImpl customerServiceService) {
        this.customerServiceService = customerServiceService;
    }

    @GetMapping("/customerService")
    public String getCustomerService(Model model, CustomerServiceDTO customerServiceDTO) {
        // 공지사항 목록
        List<CustomerServiceDTO> csnbList = customerServiceService.getnbCustomerService();
        model.addAttribute("csnbList", csnbList);
        // Q&A 목록
        List<CustomerServiceDTO> csqaList = customerServiceService.getqaCustomerService();
        model.addAttribute("csqaList", csqaList);
        // 1:1 문의 목록
        List<CustomerServiceDTO> csiaList = customerServiceService.getiaCustomerService();
        model.addAttribute("csiaList", csiaList);

//        for(CustomerServiceDTO item : csiaList){
//            System.out.println(item.getIbContent());
//            System.out.println(item.getIaContent());
//        }

        return "customerServiceBoard/customerServiceBoard";
    }

    // 사용자가 작성 1:1 문의 질문
    @PostMapping("/inquiryBoard")
    public String setCustomerService(CustomerServiceDTO customerServiceDTO) {
        //System.out.println(customerServiceDTO.getIbContent());
        int result = customerServiceService.setibCustomerService(customerServiceDTO);

        return "redirect:/customerService";
    }

    @GetMapping("/noticeBoard")
    public String noticeBoard(CustomerServiceDTO customerServiceDTO) {

        return "noticeBoard/noticeBoardEnroll";
    }

    // 공지사항 작성
    @PostMapping("/noticeBoardEnroll")
    public String setNoticeBoardEroll(CustomerServiceDTO customerServiceDTO) {

        int result = customerServiceService.setNoticeBoardEnroll(customerServiceDTO);

        System.out.println(customerServiceDTO.getNbContent());
        return "redirect:/customerService";
    }

    @GetMapping("/questionAnswer")
    public String questionAnswer(CustomerServiceDTO customerServiceDTO) {

        return "questionAnswer/questionAnswerEnroll";
    }

    @PostMapping("/questionAnswerEnroll")
    public String setquestionAnswerEnroll(CustomerServiceDTO customerServiceDTO) {
        System.out.println(customerServiceDTO.getQaTitle());
        int result = customerServiceService.setQuestionAnswerEnroll(customerServiceDTO);
        System.out.println(customerServiceDTO.getQaContent());
        return "redirect:/customerService";
    }


}

