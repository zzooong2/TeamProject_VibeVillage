package kr.co.vibevillage.customerServiceBoard.service;

import kr.co.vibevillage.customerServiceBoard.mapper.CustomerServiceMapper;
import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;
import kr.co.vibevillage.env.Env;
import kr.co.vibevillage.user.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceServiceImpl implements CustomerServiceService {

    private final Env env;
    private final CustomerServiceMapper customerServiceMapper;

    @Autowired
    public CustomerServiceServiceImpl(Env env, CustomerServiceMapper customerServiceMapper) {
        this.env = env;
        this.customerServiceMapper = customerServiceMapper;
    }

    // 공지사항 목록
    @Override
    public List<CustomerServiceDTO> getnbCustomerService() {
        List<CustomerServiceDTO> nbList = customerServiceMapper.getnbCustomerService();
        return nbList;
    }

    // Q&A 목록
    @Override
    public List<CustomerServiceDTO> getqaCustomerService() {
        List<CustomerServiceDTO> qaList = customerServiceMapper.getqaCustomerService();
        return qaList;
    }

    // 1:1 문의 목록
    @Override
    public List<CustomerServiceDTO> getiaCustomerService(UserDTO userDTO, String userNickName) {
        List<CustomerServiceDTO> iaList = customerServiceMapper.getiaCustomerService(userDTO, userNickName);
        return iaList;
    }

    // 공지사항 작성
    @Override
    public int setNoticeBoardEnroll(CustomerServiceDTO customerServiceDTO, int uNo) {

        return customerServiceMapper.setNoticeBoardEnroll(customerServiceDTO, uNo);
    }

    // 공지사항 Detail
    @Override
    public CustomerServiceDTO getNoticeBoardDetail(int nbNo, UserDTO userDTO) {

        return customerServiceMapper.getNoticeBoardDetail(nbNo, userDTO);
    }

    // 조회수 증가
    @Override
    public int nbAddViews(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.nbAddViews(customerServiceDTO);
    }

    // 공지사항 삭제(nb_delete_yn y->n)
    @Override
    public int nbDelete(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.nbDelete(customerServiceDTO);
    }

    // 공지사항 수정
    @Override
    public int setNoticeBoardEdit(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.setNoticeBoardEdit(customerServiceDTO);
    }

    // Q&A 작성
    @Override
    public int setQuestionAnswerEnroll(CustomerServiceDTO customerServiceDTO, int uNo) {

        return customerServiceMapper.setQuestionAnswerEnroll(customerServiceDTO, uNo);
    }

    // Q&A Detail
    @Override
    public CustomerServiceDTO getQuestionAnswerDetail(int qaNo, String userNickName) {

        return customerServiceMapper.getQuestionAnswerDetail(qaNo, userNickName);
    }

    // Q&A 삭제(Q&A_yn y->n)
    @Override
    public int qaDelete(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.qaDelete(customerServiceDTO);
    }

    // Q&A 수정
    @Override
    public int setQuestionAnswerEdit(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.setQuestionAnswerEdit(customerServiceDTO);
    }

    // 사용자가 작성 1:1 문의 질문
    @Override
    public int setibCustomerService(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.setibCustomerService(customerServiceDTO);
    }

    // 1:1 질문 Detail
    @Override
    public CustomerServiceDTO getInquiryAnswerDetail(int ibNo, int icNo, String userNickName) {

        return customerServiceMapper.getInquiryAnswerDetail(ibNo, icNo, userNickName);
    }

    // 1:1 질문 수정
    @Override
    public int setInquiryAnswerEdit(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.setInquiryAnswerEdit(customerServiceDTO);
    }
}
