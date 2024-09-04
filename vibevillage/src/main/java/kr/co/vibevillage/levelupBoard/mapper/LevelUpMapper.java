package kr.co.vibevillage.levelupBoard.mapper;

import kr.co.vibevillage.levelupBoard.model.LevelUpDTO;
import kr.co.vibevillage.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LevelUpMapper {
    // 등업신청 목록
    List<LevelUpDTO> getLevelUpList(String userNickName, String lrStatus);
    
    // 등업신청 작성
    int setLevelUpBoardEnroll(@Param("levelUpDTO") LevelUpDTO levelUpDTO, @Param("uNo") int uNo);

    // 등업 진행 상황
    int levelResultBoard(@Param("levelUpDTO") LevelUpDTO levelUpDTO, @Param("uNo") int uNo, @Param("lbNo") int lbNo);

    int lbCount(int uNo);

    // 등업신청 Detail
    LevelUpDTO getLevelUpBoardDetail(@Param("lbNo") int lbNo, String userNickName);

    //등업신청 수정
    int setLevelUpBoardEdit(@Param("levelUpDTO") LevelUpDTO levelUpDTO, @Param("lbNo") int lbNo);

    // 등업신청 삭제
    int lbDelete(LevelUpDTO levelUpDTO);

    int lbCountMinus(int uNo);

    // 등업신청 승인
    int levelUpApprove(@Param("lbNo") int lbNo, @Param("levelUpDTO") LevelUpDTO levelUpDTO);

    //등업
    int levelUpXML(@Param("uNo") int uNo, LevelUpDTO levelUpDTO);

    // 반려
    int levelUpReject(@Param("lbNo") int lbNo, @Param("levelUpDTO") LevelUpDTO levelUpDTO);

}
