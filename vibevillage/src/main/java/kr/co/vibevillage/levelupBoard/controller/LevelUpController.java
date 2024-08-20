package kr.co.vibevillage.levelupBoard.controller;

import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;
import kr.co.vibevillage.customerServiceBoard.service.CustomerServiceServiceImpl;
import kr.co.vibevillage.levelupBoard.model.LevelUpDTO;
import kr.co.vibevillage.levelupBoard.service.LevelUpServiceImpl;
import kr.co.vibevillage.user.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/levelUp")
public class LevelUpController {

    private final LevelUpServiceImpl levelUpService;

    @Autowired
    public LevelUpController(LevelUpServiceImpl levelUpService) {
        this.levelUpService = levelUpService;
    }

    @GetMapping("/levelUpBoard")
    public String getLevelUp(Model model, LevelUpDTO levelUpDTO) {

        // 등업신청 목록
        List<LevelUpDTO> lbList = levelUpService.getLevelUpList();
        model.addAttribute("lbList", lbList);

//        for(LevelUpDTO item : lbList) {
//            System.out.println(item.getLbTitle());
//            System.out.println(item.getLbIndate());
//        }

        return "levelUpBoard/levelUpBoard";
    }

    // 등업신청 작성 페이지 이동
    @GetMapping("/levelUpEnroll")
    public String levelUpEnroll(LevelUpDTO levelUpDTO) {

        return "levelUpBoard/levelUpBoardEnroll";
    }

    // 등업신청 작성
    @PostMapping("/levelUpBoardEnroll")
    public String setLevelUpBoardEnroll(LevelUpDTO levelUpDTO) {
        int result = levelUpService.setLevelUpBoardEnroll(levelUpDTO);

        return "redirect:/levelUp/levelUpBoard";
    }

    // 등업신청 Detail
    @GetMapping("/levelUpBoardDetail/{lbNo}")
    public String getLevelUpBoardDetail(@PathVariable("lbNo") int lbNo, Model model, LevelUpDTO levelUpDTO) {

        LevelUpDTO lbDetail = levelUpService.getLevelUpBoardDetail(lbNo);
        model.addAttribute("lbDetail", lbDetail);

        return "levelUpBoard/levelUpBoardDetail";
    }
    
    // 등업신청서 수정/삭제 폼
    @GetMapping("/levelUpBoardEditForm/{lbNo}")
    public String levelUpBoardEditForm(@PathVariable("lbNo") int lbNo, Model model, LevelUpDTO levelUpDTO) {

        LevelUpDTO lbDetail = levelUpService.getLevelUpBoardDetail(lbNo);
        model.addAttribute("lbDetail", lbDetail);

        return "levelUpBoard/levelUpBoardEdit";
    }

    // 등업신청 수정
    @PostMapping("/levelUpBoardEdit/{lbNo}")
    public String levelUpBoardEdit(@PathVariable("lbNo") int lbNo, Model model, LevelUpDTO levelUpDTO) {

        int lbEdit = levelUpService.setLevelUpBoardEdit(levelUpDTO);
        model.addAttribute("lbEdit", lbEdit);

        return "redirect:/levelUp/levelUpBoard";
    }

    // 등업신청 삭제
    @GetMapping("/levelUpBoardDelete/{lbNo}")
    public String levelUpBoardDelete(@PathVariable("lbNo") int lbNo, LevelUpDTO levelUpDTO) {

        int result = levelUpService.lbDelete(levelUpDTO);

        return "redirect:/levelUp/levelUpBoard";
    }

    // 등업 승인
    @GetMapping("/levelUpApprove/{lbNo}/{uNo}")
    public String levelUpApprove(@PathVariable("lbNo") int lbNo,
                                 @PathVariable("uNo") int uNo,
                                 LevelUpDTO levelUpDTO, UserDTO userDTO) {

         int result = levelUpService.levelUpApprove(uNo, lbNo, levelUpDTO, userDTO);

         return "redirect:/levelUp/levelUpBoard";
    }
}
