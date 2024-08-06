package kr.co.vibevillage.customerServiceBoard.controller;

import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;
import kr.co.vibevillage.customerServiceBoard.service.CustomerServiceService;
import kr.co.vibevillage.customerServiceBoard.service.CustomerServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sound.midi.Soundbank;
import java.util.List;

@Controller
public class CustomerServiceController {



    private final CustomerServiceServiceImpl customerServiceService;

    @Autowired
    public CustomerServiceController(CustomerServiceServiceImpl customerServiceService) {
        this.customerServiceService = customerServiceService;
    }

    @GetMapping("/customerService")
    public String getCustomerService(Model model, CustomerServiceDTO customerServiceDTO) {
        List<CustomerServiceDTO> csnbList = customerServiceService.getnbCustomerService();
        model.addAttribute("csnbList", csnbList);

        List<CustomerServiceDTO> csqaList = customerServiceService.getqaCustomerService();
        model.addAttribute("csqaList", csqaList);

        System.out.println(csnbList.toString());
        System.out.println(csqaList.toString());
        return "customerServiceBoard/customerServiceBoard";
    }

    @PostMapping("/inquiryBoard")
    public String setCustomerService(CustomerServiceDTO customerServiceDTO) {
        System.out.println(customerServiceDTO.getIbContent());
        System.out.println(customerServiceDTO);
        int result = customerServiceService.setibCustomerService(customerServiceDTO);

        return "redirect:/customerService";
    }


}

