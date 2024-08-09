package kr.co.vibevillage.customerServiceBoard.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;
import kr.co.vibevillage.customerServiceBoard.service.CustomerServiceService;
import kr.co.vibevillage.customerServiceBoard.service.CustomerServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.nio.file.FileStore;
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

    // 공지사항 페이지 이동
    @GetMapping("/noticeBoard")
    public String noticeBoard(CustomerServiceDTO customerServiceDTO) {

        return "noticeBoard/noticeBoardEnroll";
    }

    // 공지사항 작성
    @PostMapping("/noticeBoardEnroll")
    public String setNoticeBoardEroll(CustomerServiceDTO customerServiceDTO) {

        int result = customerServiceService.setNoticeBoardEnroll(customerServiceDTO);

//        System.out.println(customerServiceDTO.getNbContent());
        return "redirect:/customerService";
    }

    // 공지사항 Detail
    @GetMapping("/noticeBoardDetail/{nbNo}")
    public String getNoticeBoardDetail(@PathVariable("nbNo") String nbNo, Model model, CustomerServiceDTO customerServiceDTO) {
        int nbno = Integer.parseInt(nbNo);

        int result = customerServiceService.nbAddViews(customerServiceDTO);
        CustomerServiceDTO nbDetail = customerServiceService.getNoticeBoardDetail(nbno);
        model.addAttribute("nbDetail", nbDetail);

        return "noticeBoard/noticeBoardDetail";
    }


    // 공지사항 수정/삭제폼
    @GetMapping("/noticeBoardEditForm/{nbNo}")
    public String noticeBoardEditForm(@PathVariable("nbNo") int nbNo, Model model, CustomerServiceDTO customerServiceDTO) {

        CustomerServiceDTO nbDetail = customerServiceService.getNoticeBoardDetail(nbNo);
        model.addAttribute("nbDetail", nbDetail);
        return "noticeBoard/noticeBoardEdit";
    }

    // 공시사항 수정
    @PostMapping("/noticeBoardEdit/{nbNo}")
    public String noticeBoardEdit(@PathVariable("nbNo") int nbNo, Model model, CustomerServiceDTO customerServiceDTO) {

        int nbEdit = customerServiceService.setNoticeBoardEdit(customerServiceDTO);
        model.addAttribute("nbEdit", nbEdit);

        return "redirect:/customerService";
    }

    // 공지사항 삭제(update문)
    @GetMapping("/noticeBoardDelete/{nbNo}")
    public String noticeBoardDelete(@PathVariable("nbNo") int nbNo, CustomerServiceDTO customerServiceDTO) {

        int result = customerServiceService.nbDelete(customerServiceDTO);

        return "redirect:/customerService";
    }

    // Q&A 등록 페이지 이동
    @GetMapping("/questionAnswer")
    public String questionAnswer(CustomerServiceDTO customerServiceDTO) {

        return "questionAnswer/questionAnswerEnroll";
    }

    // Q&A 작성
    @PostMapping("/questionAnswerEnroll")
    public String setQuestionAnswerEnroll(CustomerServiceDTO customerServiceDTO) {
        System.out.println(customerServiceDTO.getQaTitle());
        int result = customerServiceService.setQuestionAnswerEnroll(customerServiceDTO);
        System.out.println(customerServiceDTO.getQaContent());
        return "redirect:/customerService";
    }


}

