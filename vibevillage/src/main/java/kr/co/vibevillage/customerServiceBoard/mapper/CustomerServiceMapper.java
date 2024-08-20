package kr.co.vibevillage.customerServiceBoard.mapper;

import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;
import kr.co.vibevillage.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerServiceMapper {
    public List<CustomerServiceDTO> customerServiceXML();

    // 공지사항 목록
    List<CustomerServiceDTO> getnbCustomerService();

    // Q&A 목록
    List<CustomerServiceDTO> getqaCustomerService();

    // 1:1 문의 목록
    List<CustomerServiceDTO> getiaCustomerService(UserDTO userDTO);

    // 공지사항 작성
    int setNoticeBoardEnroll(CustomerServiceDTO customerServiceDTO);

    // 공지사항 Detail
    CustomerServiceDTO getNoticeBoardDetail(int nbNo, UserDTO userDTO);

    // 공지사항 조회수 증가
    int nbAddViews(CustomerServiceDTO customerServiceDTO);

    // 공지사항 삭제(nb_delete_yn y->n)
    int nbDelete(CustomerServiceDTO customerServiceDTO);

    // 공지사항 수정
    int setNoticeBoardEdit(CustomerServiceDTO customerServiceDTO);

    // Q&A 작성
    int setQuestionAnswerEnroll(CustomerServiceDTO customerServiceDTO);

    // Q&A Detail
    CustomerServiceDTO getQuestionAnswerDetail(int qaNo);

    // Q&A 삭제(Q&A_yn y->n)
    int qaDelete(CustomerServiceDTO customerServiceDTO);

    // Q&A 수정
    int setQuestionAnswerEdit(CustomerServiceDTO customerServiceDTO);

    // 사용자가 작성 1:1 문의 질문
    int setibCustomerService(CustomerServiceDTO customerServiceDTO);

    // 1:1 질문 Detail
    CustomerServiceDTO getInquiryAnswerDetail(int ibNo);

    // 1:1 질문 수정
    int setInquiryAnswerEdit(CustomerServiceDTO customerServiceDTO);

}
