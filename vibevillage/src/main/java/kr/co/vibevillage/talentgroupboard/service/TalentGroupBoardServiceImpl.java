package kr.co.vibevillage.talentgroupboard.service;

import kr.co.vibevillage.talentgroupboard.model.TalentGroupBoardDTO;
import kr.co.vibevillage.talentgroupboard.postmapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalentGroupBoardServiceImpl implements TalentGroupBoardService {
    private PostMapper postMapper;
    @Autowired
    public TalentGroupBoardServiceImpl(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public List<TalentGroupBoardDTO> getAllPosts(){
        return postMapper.findAll();

    }
}
