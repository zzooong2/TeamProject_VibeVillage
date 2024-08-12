package kr.co.vibevillage.talentgroupboard.postmapper;

import kr.co.vibevillage.talentgroupboard.model.TalentGroupBoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<TalentGroupBoardDTO> findAll();
}
