package kr.co.vibevillage.customerServiceBoard.mapper;

import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerServiceMapper {
    public List<CustomerServiceDTO> customerServiceXML();

    // �������� ���
    List<CustomerServiceDTO> getnbCustomerService();

    // Q&A ���
    List<CustomerServiceDTO> getqaCustomerService();

    // 1:1 ���� ���
    List<CustomerServiceDTO> getiaCustomerService();

    // �������� �ۼ�
    int setNoticeBoardEnroll(CustomerServiceDTO customerServiceDTO);

    // �������� Detail
    CustomerServiceDTO getNoticeBoardDetail(int nbNo);

    // �������� ��ȸ�� ����
    int nbAddViews(CustomerServiceDTO customerServiceDTO);

    // �������� ����(nb_delete_yn y->n)
    int nbDelete(CustomerServiceDTO customerServiceDTO);

    // �������� ����
    int setNoticeBoardEdit(CustomerServiceDTO customerServiceDTO);

    // Q&A �ۼ�
    int setQuestionAnswerEnroll(CustomerServiceDTO customerServiceDTO);

    // Q&A Detail
    CustomerServiceDTO getQuestionAnswerDetail(int qaNo);

    // Q&A ����(Q&A_yn y->n)
    int qaDelete(CustomerServiceDTO customerServiceDTO);

    // Q&A ����
    int setQuestionAnswerEdit(CustomerServiceDTO customerServiceDTO);

    // ����ڰ� �ۼ� 1:1 ���� ����
    int setibCustomerService(CustomerServiceDTO customerServiceDTO);

    // 1:1 ���� Detail
    CustomerServiceDTO getInquiryAnswerDetail(int ibNo);
}
