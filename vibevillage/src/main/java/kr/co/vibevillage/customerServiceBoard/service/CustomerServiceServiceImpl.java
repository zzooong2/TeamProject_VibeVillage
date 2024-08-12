package kr.co.vibevillage.customerServiceBoard.service;

import kr.co.vibevillage.customerServiceBoard.mapper.CustomerServiceMapper;
import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;
import kr.co.vibevillage.env.Env;
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

    // �������� ���
    @Override
    public List<CustomerServiceDTO> getnbCustomerService() {
    List<CustomerServiceDTO> nbList = customerServiceMapper.getnbCustomerService();
    return nbList;
    }

    // Q&A ���
    @Override
    public List<CustomerServiceDTO> getqaCustomerService() {
    List<CustomerServiceDTO> qaList = customerServiceMapper.getqaCustomerService();
    return qaList;
    }

    // 1:1 ���� ���
    @Override
    public List<CustomerServiceDTO> getiaCustomerService() {
        List<CustomerServiceDTO> iaList = customerServiceMapper.getiaCustomerService();
        return iaList;
    }

    // �������� �ۼ�
    @Override
    public int setNoticeBoardEnroll(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.setNoticeBoardEnroll(customerServiceDTO);
    }

    // �������� Detail
    @Override
    public CustomerServiceDTO getNoticeBoardDetail(int nbNo) {

        return customerServiceMapper.getNoticeBoardDetail(nbNo);
    }

    // ��ȸ�� ����
    @Override
    public int nbAddViews(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.nbAddViews(customerServiceDTO);
    }

    // �������� ����(nb_delete_yn y->n)
    @Override
    public int nbDelete(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.nbDelete(customerServiceDTO);
    }

    // �������� ����
    @Override
    public int setNoticeBoardEdit(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.setNoticeBoardEdit(customerServiceDTO);
    }

    // Q&A �ۼ�
    @Override
    public int setQuestionAnswerEnroll(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.setQuestionAnswerEnroll(customerServiceDTO);
    }

    // Q&A Detail
    @Override
    public CustomerServiceDTO getQuestionAnswerDetail(int qaNo) {

        return customerServiceMapper.getQuestionAnswerDetail(qaNo);
    }

    // Q&A ����(Q&A_yn y->n)
    @Override
    public int qaDelete(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.qaDelete(customerServiceDTO);
    }

    // Q&A ����
    @Override
    public int setQuestionAnswerEdit(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.setQuestionAnswerEdit(customerServiceDTO);
    }

    // ����ڰ� �ۼ� 1:1 ���� ����
    @Override
    public int setibCustomerService(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.setibCustomerService(customerServiceDTO);
    }

    // 1:1 ���� Detail
    @Override
    public CustomerServiceDTO getInquiryAnswerDetail(int ibNo) {

        return customerServiceMapper.getInquiryAnswerDetail(ibNo);
    }
}
