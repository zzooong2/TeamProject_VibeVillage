package kr.co.vibevillage.customerServiceBoard.service;

import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;

import java.util.List;


public interface CustomerServiceService {
    // �������� ���
    public List<CustomerServiceDTO> getnbCustomerService();

    // Q&A ���
    public List<CustomerServiceDTO> getqaCustomerService();

    // 1:1 ���� ���
    public List<CustomerServiceDTO> getiaCustomerService();

    // �������� �ۼ�
    public int setNoticeBoardEnroll(CustomerServiceDTO customerServiceDTO);

    // Q&A �ۼ�
    public int setQuestionAnswerEnroll(CustomerServiceDTO customerServiceDTO);

    // �������� ��ȸ�� ����
    public int nbAddViews(CustomerServiceDTO customerServiceDTO);

    // �������� Detail
    public CustomerServiceDTO getNoticeBoardDetail(int nbNo);

    // �������� ����
    public int nbDelete(CustomerServiceDTO customerServiceDTO);

    // �������� ����
    public int setNoticeBoardEdit(CustomerServiceDTO customerServiceDTO);

    // Q&A Detail
    public CustomerServiceDTO getQuestionAnswerDetail(int qaNo);

    // Q&A ����
    public int qaDelete(CustomerServiceDTO customerServiceDTO);

    // Q&A ����
    public int setQuestionAnswerEdit(CustomerServiceDTO customerServiceDTO);

    // ����ڰ� �ۼ� 1:1 ���� ����
    public int setibCustomerService(CustomerServiceDTO customerServiceDTO);

    // 1:1 ���� Detail
    public CustomerServiceDTO getInquiryAnswerDetail(int ibNo);
}
