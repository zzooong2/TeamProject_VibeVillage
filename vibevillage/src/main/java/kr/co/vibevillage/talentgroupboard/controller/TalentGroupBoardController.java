package kr.co.vibevillage.talentgroupboard.controller;

import kr.co.vibevillage.talentgroupboard.model.TalentGroupBoardDTO;
import kr.co.vibevillage.talentgroupboard.service.TalentGroupBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TalentGroupBoardController {

    @Autowired
    private TalentGroupBoardService talentGroupBoardService;


    @GetMapping("/TalentGroupBoard")
    public String getPosts(Model model) {
        List<TalentGroupBoardDTO> posts = talentGroupBoardService.getAllPosts();
        model.addAttribute("posts", posts);
        return "/talentGroupBoard/talentGroupBoard";
    }

    @GetMapping("/createGroup")
    public String getCreateGroup() {
        return "/talentGroupBoard/createGroup";
    }
}


