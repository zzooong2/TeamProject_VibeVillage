package kr.co.vibevillage;

import kr.co.vibevillage.user.model.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/form")
public class FormController {

    @GetMapping("")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm(Model model, UserDTO userDTO) {
        model.addAttribute("userDTO", userDTO);
        return "user/loginRegister";
    }

    @GetMapping("/myPage")
    public String myPageForm() {
        return "user/myPage";
    }
}
