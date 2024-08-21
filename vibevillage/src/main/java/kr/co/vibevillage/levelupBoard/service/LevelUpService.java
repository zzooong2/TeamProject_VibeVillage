package kr.co.vibevillage.levelupBoard.service;

import kr.co.vibevillage.levelupBoard.model.LevelUpDTO;
import kr.co.vibevillage.user.model.dto.UserDTO;

import java.util.List;

public interface LevelUpService {
    // 등업신청 목록
    public List<LevelUpDTO> getLevelUpList(String userNickName);

    // 등업신청 작성
    public int setLevelUpBoardEnroll(LevelUpDTO levelUpDTO);

    // 등업신청 Detail
    public LevelUpDTO getLevelUpBoardDetail(int lbNo, String userNickName);

    // 등업신청 수정
    public int setLevelUpBoardEdit(LevelUpDTO levelUpDTO, String userNickName);

    //등업신청 삭제
    public int lbDelete(LevelUpDTO levelUpDTO);

    // 등언 신청 승인
    public int levelUpApprove(int uNo, int lbNo, LevelUpDTO levelUpDTO, UserDTO userDTO);
}


