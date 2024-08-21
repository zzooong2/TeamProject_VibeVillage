package kr.co.vibevillage.levelupBoard.mapper;

import kr.co.vibevillage.levelupBoard.model.LevelUpDTO;
import kr.co.vibevillage.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LevelUpMapper {
    // 등업신청 목록
    List<LevelUpDTO> getLevelUpList(String userNickName);
    
    // 등업신청 작성
    int setLevelUpBoardEnroll(LevelUpDTO levelUpDTO);

    int levelResultBoard(LevelUpDTO levelUpDTO);

    // 등업신청 Detail
    LevelUpDTO getLevelUpBoardDetail(int lbNo, String userNickName);

    //등업신청 수정
    int setLevelUpBoardEdit(LevelUpDTO levelUpDTO, String userNickName);

    // 등업신청 삭제
    int lbDelete(LevelUpDTO levelUpDTO);

    // 등업신청 승인
    int levelUpApprove(@Param("lbNo") int lbNo, LevelUpDTO levelUpDTO, UserDTO userDTO);

    int levelUpXML(@Param("uNo") int uNo, LevelUpDTO levelUpDTO, UserDTO userDTO);

}
