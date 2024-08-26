package kr.co.vibevillage.customerServiceBoard.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;
import kr.co.vibevillage.customerServiceBoard.service.CustomerServiceService;
import kr.co.vibevillage.customerServiceBoard.service.CustomerServiceServiceImpl;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
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
    private final LoginServiceImpl loginServiceImpl;

    @Autowired
    public CustomerServiceController(CustomerServiceServiceImpl customerServiceService, LoginServiceImpl loginServiceImpl) {
        this.customerServiceService = customerServiceService;
        this.loginServiceImpl = loginServiceImpl;
    }

    // 고객센터 목록
    @GetMapping("/csList/{category}")
    public String getCustomerService(@PathVariable("category") int icNo, Model model, CustomerServiceDTO customerServiceDTO, UserDTO userDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();
        String userLevel = loginInfo.getUserLevel();
        model.addAttribute("userLevel", userLevel);

        // 공지사항 목록
        List<CustomerServiceDTO> csnbList = customerServiceService.getnbCustomerService();
        model.addAttribute("csnbList", csnbList);

        // Q&A 목록
        List<CustomerServiceDTO> csqaList = customerServiceService.getqaCustomerService();
        model.addAttribute("csqaList", csqaList);

        // 1:1 문의 목록(본인 닉네임과 일치해야만 나옴)
        List<CustomerServiceDTO>csiaList = customerServiceService.getiaCustomerService(userDTO, userNickName, icNo);
        model.addAttribute("csiaList", csiaList);

        // 1:1 문의 목록(관리자용)
        List<CustomerServiceDTO>csiamList = customerServiceService.getiamCustomerService();
        model.addAttribute("csiamList", csiamList);

//        for(CustomerServiceDTO item : csiaList){
//            System.out.println(item.getIbContent());
//            System.out.println(item.getIaContent());
//        }

        return "customerServiceBoard/customerServiceBoard";
    }

    // 공지사항 작성 페이지 이동
    @GetMapping("/noticeBoard")
    public String noticeBoard(CustomerServiceDTO customerServiceDTO, Model model) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();

        model.addAttribute("userNickName", userNickName);

        return "noticeBoard/noticeBoardEnroll";
    }

    // 공지사항 작성
    @PostMapping("/noticeBoardEnroll")
    public String setNoticeBoardEnroll(CustomerServiceDTO customerServiceDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        int uNo = loginInfo.getUserNo();

        // 공지사항 조회수 증가
        int resultDto = customerServiceService.nbCount(uNo);

        int result = customerServiceService.setNoticeBoardEnroll(customerServiceDTO, uNo);

        return "redirect:/customerService/csList/0";
    }

    // 공지사항 Detail
    @GetMapping("/noticeBoardDetail/{nbNo}")
    public String getNoticeBoardDetail(@PathVariable("nbNo") int nbNo, Model model, CustomerServiceDTO customerServiceDTO, UserDTO userDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();
        String userLevel = loginInfo.getUserLevel();

        // 조회수 증가
        int result = customerServiceService.nbAddViews(customerServiceDTO);

        CustomerServiceDTO nbDetail = customerServiceService.getNoticeBoardDetail(nbNo, userDTO);
        model.addAttribute("nbDetail", nbDetail);

        return "noticeBoard/noticeBoardDetail";
    }

    // 공지사항 수정/삭제폼
    @GetMapping("/noticeBoardEditForm/{nbNo}")
    public String noticeBoardEditForm(@PathVariable("nbNo") int nbNo, Model model, CustomerServiceDTO customerServiceDTO, UserDTO userDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();

        CustomerServiceDTO nbDetail = customerServiceService.getNoticeBoardDetail(nbNo, userDTO);
        model.addAttribute("nbDetail", nbDetail);

        return "noticeBoard/noticeBoardEdit";
    }

    // 공지사항 수정
    @GetMapping("/noticeBoardEdit/{nbNo}")
    public String noticeBoardEdit(@PathVariable("nbNo") int nbNo, Model model, CustomerServiceDTO customerServiceDTO) {

        int nbEdit = customerServiceService.setNoticeBoardEdit(customerServiceDTO);
        model.addAttribute("nbEdit", nbEdit);

        return "redirect:/customerService/csList/0";
    }

    // 공지사항 삭제(nb_delete_yn y->n)
    @GetMapping("/noticeBoardDelete/{nbNo}")
    public String noticeBoardDelete(@PathVariable("nbNo") int nbNo, CustomerServiceDTO customerServiceDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        int uNo = loginInfo.getUserNo();

        int result = customerServiceService.nbDelete(customerServiceDTO);

        // 공지사항 삭제 조회수 감소
        int resultDto = customerServiceService.nbCountMinus(uNo);

        return "redirect:/customerService/csList/0";
    }

    // Q&A 작성 페이지 이동
    @GetMapping("/questionAnswer")
    public String questionAnswer(CustomerServiceDTO customerServiceDTO, Model model) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();

        model.addAttribute("userNickName", userNickName);

        return "questionAnswer/questionAnswerEnroll";
    }

    // Q&A 작성
    @PostMapping("/questionAnswerEnroll")
    public String setQuestionAnswerEnroll(CustomerServiceDTO customerServiceDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        int uNo = loginInfo.getUserNo();

        int result = customerServiceService.setQuestionAnswerEnroll(customerServiceDTO, uNo);

        // Q&A 게시글 count 추가
        int resultDto = customerServiceService.qaCount(uNo);

        return "redirect:/customerService/csList/0";
    }

    // Q&A Detail
    @GetMapping("/questionAnswerDetail/{qaNo}")
    public String getQuestionAnswerDetail(@PathVariable("qaNo") int qaNo, Model model, CustomerServiceDTO customerServiceDTO, UserDTO userDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();

        CustomerServiceDTO qaDetail = customerServiceService.getQuestionAnswerDetail(qaNo, userNickName);
        model.addAttribute("qaDetail", qaDetail);

        return "questionAnswer/questionAnswerDetail";
    }

    // Q&A 수정/삭제 폼
    @GetMapping("/questionAnswerEditForm/{qaNo}")
    public String questionAnswerEditForm(@PathVariable("qaNo") int qaNo, Model model, CustomerServiceDTO customerServiceDTO, UserDTO userDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();

        CustomerServiceDTO qaDetail = customerServiceService.getQuestionAnswerDetail(qaNo, userNickName);
        model.addAttribute("qaDetail", qaDetail);

        return "questionAnswer/questionAnswerEdit";
    }

    // Q&A 수정
    @GetMapping("/questionAnswerEdit/{qaNo}")
    public String questionAnswerEdit(@PathVariable("qaNo") int qaNo, Model model, CustomerServiceDTO customerServiceDTO) {

        int nbEdit = customerServiceService.setQuestionAnswerEdit(customerServiceDTO);
        model.addAttribute("nbEdit", nbEdit);

        return "redirect:/customerService/csList/0";
    }

    // Q&A 삭제(Q&A_yn y->n)
    @GetMapping("/questionAnswerDelete/{qaNo}")
    public String questionAnswerDelete(@PathVariable("qaNo") int qaNo, CustomerServiceDTO customerServiceDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        int uNo = loginInfo.getUserNo();

        int result = customerServiceService.qaDelete(customerServiceDTO);

        // Q&A 삭제 count 감소
        int resultDto = customerServiceService.qaCountMinus(uNo);

        return "redirect:/customerService/csList/0";
    }

    // 사용자가 작성 1:1 문의 질문
    @PostMapping("/inquiryBoard")
    public String setCustomerService(Model model, CustomerServiceDTO customerServiceDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        int uNo = loginInfo.getUserNo();

        int result = customerServiceService.setibCustomerService(customerServiceDTO, uNo, customerServiceDTO.getIcNo());

        // 사용자 게시글 count 추가
        int resultDto = customerServiceService.ibCount(uNo);

        return "redirect:/customerService/csList/0";
    }

    // 1:1 질문 Detail
    @GetMapping("/inquiryAnswer/{ibNo}/{icNo}")
    public String getInquiryAnswerDetail(@PathVariable("ibNo") int ibNo,
                                         @PathVariable("icNo") int icNo,
                                         Model model, CustomerServiceDTO customerServiceDTO, UserDTO userDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();

        CustomerServiceDTO ibDetail = customerServiceService.getInquiryAnswerDetail(ibNo, icNo, userNickName);
        model.addAttribute("ibDetail", ibDetail);

//        model.addAttribute("userNickName", userNickName);

        return "inquiryBoard/inquiryAnswerDetail";
    }

    // 1:1 문의 답변 폼
    @GetMapping("/inquiryAnswerForm/{ibNo}/{icNo}")
    public String inquiryAnswerEditForm(@PathVariable("ibNo") int ibNo,
                                        @PathVariable("icNo") int icNo,
                                        Model model, CustomerServiceDTO customerServiceDTO, UserDTO userDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();

        CustomerServiceDTO ibDetail = customerServiceService.getInquiryAnswerDetail(ibNo, icNo, userNickName);
        model.addAttribute("ibDetail", ibDetail);
//        model.addAttribute("userNickName", userNickName);

        return "inquiryBoard/inquiryAnswer";
    }

    // 1:1 문의 답변
    @PostMapping("/inquiryAnswerPage/{ibNo}")
    public String inquiryAnswerEdit(@PathVariable("ibNo") int ibNo, Model model, CustomerServiceDTO customerServiceDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        int uNo = loginInfo.getUserNo();

        int ibEdit = customerServiceService.setInquiryAnswerEdit(ibNo, uNo, customerServiceDTO);
        model.addAttribute("ibEdit", ibEdit);

        return "redirect:/customerService/csList/0";
    }



}

