package kr.co.vibevillage.levelupBoard.controller;

import kr.co.vibevillage.customerServiceBoard.model.CustomerServiceDTO;
import kr.co.vibevillage.customerServiceBoard.service.CustomerServiceServiceImpl;
import kr.co.vibevillage.levelupBoard.model.LevelUpDTO;
import kr.co.vibevillage.levelupBoard.service.LevelUpServiceImpl;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;
import org.apache.ibatis.annotations.Param;
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
    private final LoginServiceImpl loginServiceImpl;

    @Autowired
    public LevelUpController(LevelUpServiceImpl levelUpService, LoginServiceImpl loginServiceImpl) {
        this.levelUpService = levelUpService;
        this.loginServiceImpl = loginServiceImpl;
    }

    // 등업 신청 목록
    @GetMapping("/levelUpBoard")
    public String getLevelUp(Model model, LevelUpDTO levelUpDTO, UserDTO userDTO) {

        // 로그인한 유저 정보
        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();
        int uNo = loginInfo.getUserNo();
        String loginUserId = loginInfo.getUserId();

        // 등업신청 목록
        List<LevelUpDTO> lbList = levelUpService.getLevelUpList(userNickName);
        model.addAttribute("lbList", lbList);
        model.addAttribute("loginUserId", loginUserId);
//        model.addAttribute("userNickName", userNickName);

//        for(LevelUpDTO item : lbList) {
//            System.out.println(item.getLbTitle());
//            System.out.println(item.getLbIndate());
//        }

        return "levelUpBoard/levelUpBoard";
    }

    // 등업신청 작성 페이지 이동
    @GetMapping("/levelUpEnroll")
    public String levelUpEnroll(LevelUpDTO levelUpDTO, Model model) {

        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();

        model.addAttribute("userNickName", userNickName);

        return "levelUpBoard/levelUpBoardEnroll";
    }

    // 등업신청 작성
    @PostMapping("/levelUpBoardEnroll")
    public String setLevelUpBoardEnroll(LevelUpDTO levelUpDTO) {

        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();
        int uNo = loginInfo.getUserNo();

        int result = levelUpService.setLevelUpBoardEnroll(levelUpDTO, uNo, levelUpDTO.getLbNo());

        int resultDto = levelUpService.lbCount(uNo);

        return "redirect:/levelUp/levelUpBoard";
    }

    // 등업신청 Detail
    @GetMapping("/levelUpBoardDetail/{lbNo}")
    public String getLevelUpBoardDetail(@PathVariable("lbNo") int lbNo, Model model, LevelUpDTO levelUpDTO, UserDTO userDTO) {

        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();
        String userLevel = loginInfo.getUserLevel();

        LevelUpDTO lbDetail = levelUpService.getLevelUpBoardDetail(lbNo, userNickName);
        model.addAttribute("lbDetail", lbDetail);
        model.addAttribute("userLevel", userLevel);
        model.addAttribute("userNickName", userNickName);

        return "levelUpBoard/levelUpBoardDetail";
    }
    
    // 등업신청서 수정/삭제 폼
    @GetMapping("/levelUpBoardEditForm/{lbNo}")
    public String levelUpBoardEditForm(@PathVariable("lbNo") int lbNo, Model model, LevelUpDTO levelUpDTO) {

        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();

        LevelUpDTO lbDetail = levelUpService.getLevelUpBoardDetail(lbNo, userNickName);
        model.addAttribute("lbDetail", lbDetail);
//        model.addAttribute("userNickName", userNickName);

        return "levelUpBoard/levelUpBoardEdit";
    }

    // 등업신청 수정
    @GetMapping("/levelUpBoardEdit/{lbNo}")
    public String levelUpBoardEdit(@PathVariable("lbNo") int lbNo, Model model, LevelUpDTO levelUpDTO) {

        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        String userNickName = loginInfo.getUserNickName();

        int lbEdit = levelUpService.setLevelUpBoardEdit(levelUpDTO, lbNo);
        model.addAttribute("lbEdit", lbEdit);

        return "redirect:/levelUp/levelUpBoard";
    }

    // 등업신청 삭제
    @GetMapping("/levelUpBoardDelete/{lbNo}")
    public String levelUpBoardDelete(@PathVariable("lbNo") int lbNo, LevelUpDTO levelUpDTO) {

        UserDTO loginInfo = loginServiceImpl.getLoginUserInfo();
        int uNo = loginInfo.getUserNo();

        int result = levelUpService.lbDelete(levelUpDTO);

        // 게시글수 count 감소
        int resultDto = levelUpService.lbCountMinus(uNo);

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
