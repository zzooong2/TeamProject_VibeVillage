package kr.co.vibevillage.customerServiceBoard.service;

import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;

import java.util.List;


public interface CustomerServiceService {
    public List<CustomerServiceDTO> getnbCustomerService();

    public List<CustomerServiceDTO> getqaCustomerService();

    public int setibCustomerService(CustomerServiceDTO customerServiceDTO);

    public List<CustomerServiceDTO> getiaCustomerService();

    public int setNoticeBoardEnroll(CustomerServiceDTO customerServiceDTO);

    public int setQuestionAnswerEnroll(CustomerServiceDTO customerServiceDTO);
}
