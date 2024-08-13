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
    public String getNoticeBoardDetail(@PathVariable("nbNo") int nbNo, Model model, CustomerServiceDTO customerServiceDTO) {

        // 조회수 증가
        int result = customerServiceService.nbAddViews(customerServiceDTO);
        CustomerServiceDTO nbDetail = customerServiceService.getNoticeBoardDetail(nbNo);
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

    // 공지사항 수정
    @PostMapping("/noticeBoardEdit/{nbNo}")
    public String noticeBoardEdit(@PathVariable("nbNo") int nbNo, Model model, CustomerServiceDTO customerServiceDTO) {

        int nbEdit = customerServiceService.setNoticeBoardEdit(customerServiceDTO);
        model.addAttribute("nbEdit", nbEdit);

        return "redirect:/customerService";
    }

    // 공지사항 삭제(nb_delete_yn y->n)
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

        int result = customerServiceService.setQuestionAnswerEnroll(customerServiceDTO);

        return "redirect:/customerService";
    }

    // Q&A Detail
    @GetMapping("/questionAnswerDetail/{qaNo}")
    public String getQuestionAnswerDetail(@PathVariable("qaNo") int qaNo, Model model, CustomerServiceDTO customerServiceDTO) {

        CustomerServiceDTO qaDetail = customerServiceService.getQuestionAnswerDetail(qaNo);
        model.addAttribute("qaDetail", qaDetail);

        return "questionAnswer/questionAnswerDetail";
    }

    // Q&A 수정/삭제 폼
    @GetMapping("/questionAnswerEditForm/{qaNo}")
    public String questionAnswerEditForm(@PathVariable("qaNo") int qaNo, Model model, CustomerServiceDTO customerServiceDTO) {

        CustomerServiceDTO qaDetail = customerServiceService.getQuestionAnswerDetail(qaNo);
        model.addAttribute("qaDetail", qaDetail);
        return "questionAnswer/questionAnswerEdit";
    }

    // Q&A 수정
    @PostMapping("/questionAnswerEdit/{qaNo}")
    public String questionAnswerEdit(@PathVariable("qaNo") int qaNo, Model model, CustomerServiceDTO customerServiceDTO) {

        int nbEdit = customerServiceService.setQuestionAnswerEdit(customerServiceDTO);
        model.addAttribute("nbEdit", nbEdit);

        return "redirect:/customerService";
    }

    // Q&A 삭제(Q&A_yn y->n)
    @GetMapping("/questionAnswerDelete/{qaNo}")
    public String questionAnswerDelete(@PathVariable("qaNo") int qaNo, CustomerServiceDTO customerServiceDTO) {

        int result = customerServiceService.qaDelete(customerServiceDTO);

        return "redirect:/customerService";
    }

    // 사용자가 작성 1:1 문의 질문
    @PostMapping("/inquiryBoard")
    public String setCustomerService(Model model, CustomerServiceDTO customerServiceDTO) {

        int result = customerServiceService.setibCustomerService(customerServiceDTO);

        return "redirect:/customerService";
    }

    // 1:1 질문 Detail
    @GetMapping("/inquiryAnswer/{ibNo}/{icNo}")
    public String getInquiryAnswerDetail(@PathVariable("ibNo") int ibNo,
                                         @PathVariable("icNo") int icNo,
                                         Model model, CustomerServiceDTO customerServiceDTO) {

        CustomerServiceDTO ibDetail = customerServiceService.getInquiryAnswerDetail(ibNo);
        model.addAttribute("ibDetail", ibDetail);

        System.out.println(ibDetail.getIbNo());
        System.out.println(ibDetail.getIbContent());
        System.out.println(ibDetail.getUNickName());

        return "inquiryBoard/inquiryAnswerDetail";
    }

    // 1:1 질문 수정/삭제 폼
    @GetMapping("/inquiryAnswerEditForm/{ibNo}")
    public String inquiryAnswerEditForm(@PathVariable("ibNo") int ibNo,
                                        Model model, CustomerServiceDTO customerServiceDTO) {

        CustomerServiceDTO ibDetail = customerServiceService.getInquiryAnswerDetail(ibNo);
        model.addAttribute("ibDetail", ibDetail);

        return "inquiryBoard/inquiryAnswerEdit";
    }
}

