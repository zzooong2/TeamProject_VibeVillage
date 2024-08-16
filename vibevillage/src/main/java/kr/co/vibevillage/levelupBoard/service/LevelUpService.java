package kr.co.vibevillage.levelupBoard.service;

import kr.co.vibevillage.levelupBoard.model.LevelUpDTO;

import java.util.List;

public interface LevelUpService {
    // 등업신청 목록
    public List<LevelUpDTO> getLevelUpList();

    // 등업신청 작성
    public int setLevelUpBoardEnroll(LevelUpDTO levelUpDTO);

    // 등업신청 Detail
    public LevelUpDTO getLevelUpBoardDetail(int lbNo);

    // 등업신청 수정
    public int setLevelUpBoardEdit(LevelUpDTO levelUpDTO);

    //등업신청 삭제
    public int lbDelete(LevelUpDTO levelUpDTO);
}


