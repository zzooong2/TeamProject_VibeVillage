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

    @Override
    public List<CustomerServiceDTO> getnbCustomerService() {
    List<CustomerServiceDTO> nbList = customerServiceMapper.getnbCustomerService();
    return nbList;
    }

    @Override
    public List<CustomerServiceDTO> getqaCustomerService() {
    List<CustomerServiceDTO> qaList = customerServiceMapper.getqaCustomerService();
    return qaList;
    }

    @Override
    public int setibCustomerService(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.setibCustomerService(customerServiceDTO);
    }

    @Override
    public List<CustomerServiceDTO> getiaCustomerService() {
        List<CustomerServiceDTO> iaList = customerServiceMapper.getiaCustomerService();
        return iaList;
    }

    @Override
    public int setNoticeBoardEnroll(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.setNoticeBoardEnroll(customerServiceDTO);
    }


    @Override
    public int nbAddViews(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.nbAddViews(customerServiceDTO);
    }

    @Override
    public CustomerServiceDTO getNoticeBoardDetail(int nbNo) {

        return customerServiceMapper.getNoticeBoardDetail(nbNo);
    }

    @Override
    public int nbDelete(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.nbDelete(customerServiceDTO);
    }

    @Override
    public int setNoticeBoardEdit(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.setNoticeBoardEdit(customerServiceDTO);
    }


    @Override
    public int setQuestionAnswerEnroll(CustomerServiceDTO customerServiceDTO) {

        return customerServiceMapper.setQuestionAnswerEnroll(customerServiceDTO);
    }
}
