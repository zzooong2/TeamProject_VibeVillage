package kr.co.vibevillage.customerServiceBoard.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;
import kr.co.vibevillage.customerServiceBoard.service.CustomerServiceService;
import kr.co.vibevillage.customerServiceBoard.service.CustomerServiceServiceImpl;
import kr.co.vibevillage.user.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.nio.file.FileStore;
import java.util.List;

@Controller
@RequestMapping("/customerService")
public class CustomerServiceController {

    private final CustomerServiceServiceImpl customerServiceService;

    @Autowired
    public CustomerServiceController(CustomerServiceServiceImpl customerServiceService) {
        this.customerServiceService = customerServiceService;
    }

    @GetMapping("/csList")
    public String getCustomerService(Model model, CustomerServiceDTO customerServiceDTO, UserDTO userDTO) {

        // 공지사항 목록
        List<CustomerServiceDTO> csnbList = customerServiceService.getnbCustomerService();
        model.addAttribute("csnbList", csnbList);
        // Q&A 목록
        List<CustomerServiceDTO> csqaList = customerServiceService.getqaCustomerService();
        model.addAttribute("csqaList", csqaList);
        // 1:1 문의 목록
        List<CustomerServiceDTO>csiaList = customerServiceService.getiaCustomerService(userDTO);
        model.addAttribute("csiaList", csiaList);

//        for(CustomerServiceDTO item : csiaList){
//            System.out.println(item.getIbContent());
//            System.out.println(item.getIaContent());
//        }

        return "customerServiceBoard/customerServiceBoard";
    }

    // 공지사항 작성 페이지 이동
    @GetMapping("/noticeBoard")
    public String noticeBoard(CustomerServiceDTO customerServiceDTO) {

        return "noticeBoard/noticeBoardEnroll";
    }

    // 공지사항 작성
    @PostMapping("/noticeBoardEnroll")
    public String setNoticeBoardEnroll(CustomerServiceDTO customerServiceDTO, Model model) {

        int result = customerServiceService.setNoticeBoardEnroll(customerServiceDTO);

//        // 로그인한 사용자의 프로필 정보를 가져오기 위해 getLoggedInUsername 메서드 호출
//        String loginUserId = getLoggedInUsername();
//
//        model.addAttribute("loginUserId", loginUserId);
//        System.out.println("loginUserId: " + loginUserId);

        return "redirect:/customerService/csList";
    }

    // 공지사항 Detail
    @GetMapping("/noticeBoardDetail/{nbNo}")
    public String getNoticeBoardDetail(@PathVariable("nbNo") int nbNo, Model model, CustomerServiceDTO customerServiceDTO, UserDTO userDTO) {

        // 조회수 증가
        int result = customerServiceService.nbAddViews(customerServiceDTO);
        CustomerServiceDTO nbDetail = customerServiceService.getNoticeBoardDetail(nbNo, userDTO);
        model.addAttribute("nbDetail", nbDetail);

        return "noticeBoard/noticeBoardDetail";
    }

    // 공지사항 수정/삭제폼
    @GetMapping("/noticeBoardEditForm/{nbNo}")
    public String noticeBoardEditForm(@PathVariable("nbNo") int nbNo, Model model, CustomerServiceDTO customerServiceDTO, UserDTO userDTO) {

        CustomerServiceDTO nbDetail = customerServiceService.getNoticeBoardDetail(nbNo, userDTO);
        model.addAttribute("nbDetail", nbDetail);
        return "noticeBoard/noticeBoardEdit";
    }

    // 공지사항 수정
    @PostMapping("/noticeBoardEdit/{nbNo}")
    public String noticeBoardEdit(@PathVariable("nbNo") int nbNo, Model model, CustomerServiceDTO customerServiceDTO) {

        int nbEdit = customerServiceService.setNoticeBoardEdit(customerServiceDTO);
        model.addAttribute("nbEdit", nbEdit);

        return "redirect:/customerService/csList";
    }

    // 공지사항 삭제(nb_delete_yn y->n)
    @GetMapping("/noticeBoardDelete/{nbNo}")
    public String noticeBoardDelete(@PathVariable("nbNo") int nbNo, CustomerServiceDTO customerServiceDTO) {

        int result = customerServiceService.nbDelete(customerServiceDTO);

        return "redirect:/customerService/csList";
    }

    // Q&A 작성 페이지 이동
    @GetMapping("/questionAnswer")
    public String questionAnswer(CustomerServiceDTO customerServiceDTO) {

        return "questionAnswer/questionAnswerEnroll";
    }

    // Q&A 작성
    @PostMapping("/questionAnswerEnroll")
    public String setQuestionAnswerEnroll(CustomerServiceDTO customerServiceDTO) {

        int result = customerServiceService.setQuestionAnswerEnroll(customerServiceDTO);

        return "redirect:/customerService/csList";
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

        return "redirect:/customerService/csList";
    }

    // Q&A 삭제(Q&A_yn y->n)
    @GetMapping("/questionAnswerDelete/{qaNo}")
    public String questionAnswerDelete(@PathVariable("qaNo") int qaNo, CustomerServiceDTO customerServiceDTO) {

        int result = customerServiceService.qaDelete(customerServiceDTO);

        return "redirect:/customerService/csList";
    }

    // 사용자가 작성 1:1 문의 질문
    @PostMapping("/inquiryBoard")
    public String setCustomerService(Model model, CustomerServiceDTO customerServiceDTO) {

        int result = customerServiceService.setibCustomerService(customerServiceDTO);
        System.out.println(customerServiceDTO.getIcNo());
        System.out.println(customerServiceDTO.getIcName());

        return "redirect:/customerService/csList";
    }

    // 1:1 질문 Detail
    @GetMapping("/inquiryAnswer/{ibNo}")
    public String getInquiryAnswerDetail(@PathVariable("ibNo") int ibNo,
                                         Model model, CustomerServiceDTO customerServiceDTO) {

        CustomerServiceDTO ibDetail = customerServiceService.getInquiryAnswerDetail(ibNo);
        model.addAttribute("ibDetail", ibDetail);

        return "inquiryBoard/inquiryAnswerDetail";
    }

    // 1:1 문의 답변 폼
    @GetMapping("/inquiryAnswerForm/{ibNo}")
    public String inquiryAnswerEditForm(@PathVariable("ibNo") int ibNo,
                                        Model model, CustomerServiceDTO customerServiceDTO) {

        CustomerServiceDTO ibDetail = customerServiceService.getInquiryAnswerDetail(ibNo);
        model.addAttribute("ibDetail", ibDetail);

        return "inquiryBoard/inquiryAnswer";
    }

    // 1:1 문의 답변
    @PostMapping("/inquiryAnswerPage/{ibNo}")
    public String inquiryAnswerEdit(@PathVariable("ibNo") int qaNo, Model model, CustomerServiceDTO customerServiceDTO) {

        int ibEdit = customerServiceService.setInquiryAnswerEdit(customerServiceDTO);
        model.addAttribute("ibEdit", ibEdit);

        return "redirect:/customerService/csList";
    }



}

