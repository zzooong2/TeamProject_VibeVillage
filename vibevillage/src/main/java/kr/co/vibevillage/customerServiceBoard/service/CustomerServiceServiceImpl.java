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
}
