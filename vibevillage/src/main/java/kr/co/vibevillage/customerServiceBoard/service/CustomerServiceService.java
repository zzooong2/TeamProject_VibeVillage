package kr.co.vibevillage.customerServiceBoard.service;

import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;
import kr.co.vibevillage.user.model.dto.UserDTO;

import java.util.List;


public interface CustomerServiceService {
    // 공지사항 목록
    public List<CustomerServiceDTO> getnbCustomerService();

    // Q&A 목록
    public List<CustomerServiceDTO> getqaCustomerService();

    // 1:1 문의 목록
    public List<CustomerServiceDTO> getiaCustomerService(UserDTO userDTO, String userNickName, int icNo);

    // 1:1 문의 목록(관리자용)
    public List<CustomerServiceDTO> getiamCustomerService();

    // 공지사항 작성
    public int setNoticeBoardEnroll(CustomerServiceDTO customerServiceDTO, int uNo);

    public int nbCount(int uNo);

    // 공지사항 조회수 증가
    public int nbAddViews(CustomerServiceDTO customerServiceDTO);

    // 공지사항 Detail
    public CustomerServiceDTO getNoticeBoardDetail(int nbNo, UserDTO userDTO);

    // 공지사항 삭제
    public int nbDelete(CustomerServiceDTO customerServiceDTO);

    // 공지사항 삭제 조회수 감소
    public int nbCountMinus(int uNo);

    // 공지사항 수정
    public int setNoticeBoardEdit(CustomerServiceDTO customerServiceDTO);

    // Q&A 작성
    public int setQuestionAnswerEnroll(CustomerServiceDTO customerServiceDTO, int uNo);

    // Q&A 게시글 count 추가
    public int qaCount(int uNo);

    // Q&A Detail
    public CustomerServiceDTO getQuestionAnswerDetail(int qaNo, String userNickName);

    // Q&A 삭제
    public int qaDelete(CustomerServiceDTO customerServiceDTO);

    // Q&A 삭제 count 감소
    public int qaCountMinus(int uNo);

    // Q&A 수정
    public int setQuestionAnswerEdit(CustomerServiceDTO customerServiceDTO);

    // 사용자가 작성 1:1 문의 질문
    public int setibCustomerService(CustomerServiceDTO customerServiceDTO, int uNo, int icNo);

    // 사용자 게시글 count 추가
    public int ibCount(int uNo);

    // 1:1 질문 Detail
    public CustomerServiceDTO getInquiryAnswerDetail(int ibNo, int icNo, String userNickName);

    // 1:1 질문 답변
    public int setInquiryAnswerEdit(int ibNo, int uNo, CustomerServiceDTO customerServiceDTO);
}
