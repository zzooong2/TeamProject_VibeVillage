package kr.co.vibevillage.levelupBoard.service;

import kr.co.vibevillage.levelupBoard.model.LevelUpDTO;
import kr.co.vibevillage.user.model.dto.UserDTO;

import java.util.List;

public interface LevelUpService {
    // 등업신청 목록
    public List<LevelUpDTO> getLevelUpList(String userNickName, String lrStatus);

    // 등업신청 작성
    public int setLevelUpBoardEnroll(LevelUpDTO levelUpDTO, int uNo, int lbNo);

    public int lbCount(int uNo);

    // 등업신청 Detail
    public LevelUpDTO getLevelUpBoardDetail(int lbNo, String userNickName);

    // 등업신청 수정
    public int setLevelUpBoardEdit(LevelUpDTO levelUpDTO, int lbNo);

    //등업신청 삭제
    public int lbDelete(LevelUpDTO levelUpDTO);

    public int lbCountMinus(int uNo);

    // 등업 승인
    public int levelUpApprove(int uNo, int lbNo, LevelUpDTO levelUpDTO);

    // 등업 반려
    public int levelUpReject(int lbNo, LevelUpDTO levelUpDTO);
}


