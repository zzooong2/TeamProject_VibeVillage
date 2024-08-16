package kr.co.vibevillage.levelupBoard.mapper;

import kr.co.vibevillage.levelupBoard.model.LevelUpDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LevelUpMapper {
    // 등업신청 목록
    List<LevelUpDTO> getLevelUpList();
    
    // 등업신청 작성
    int setLevelUpBoardEnroll(LevelUpDTO levelUpDTO);

    // 등업신청 Detail
    LevelUpDTO getLevelUpBoardDetail(int lbNo);

    //등업신청 수정
    int setLevelUpBoardEdit(LevelUpDTO levelUpDTO);

    // 등업신청 삭제
    int lbDelete(LevelUpDTO levelUpDTO);
}
