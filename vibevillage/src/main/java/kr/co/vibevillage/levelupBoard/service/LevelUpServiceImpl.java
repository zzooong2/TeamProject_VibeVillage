package kr.co.vibevillage.levelupBoard.service;

import kr.co.vibevillage.env.Env;
import kr.co.vibevillage.levelupBoard.mapper.LevelUpMapper;
import kr.co.vibevillage.levelupBoard.model.LevelUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelUpServiceImpl implements LevelUpService {

    private final Env env;
    private final LevelUpMapper levelUpMapper;

    @Autowired
    public LevelUpServiceImpl(Env env, LevelUpMapper levelUpMapper) {
        this.env = env;
        this.levelUpMapper = levelUpMapper;
    }

    // 등업신청 목록
    @Override
    public List<LevelUpDTO> getLevelUpList() {
        List<LevelUpDTO> lbList = levelUpMapper.getLevelUpList();
        return lbList;
    }

    // 등업신청 작성
    @Override
    public int setLevelUpBoardEnroll(LevelUpDTO levelUpDTO) {

        return levelUpMapper.setLevelUpBoardEnroll(levelUpDTO);
    }

    // 등업신청 Detail
    @Override
    public LevelUpDTO getLevelUpBoardDetail(int lbNo) {

        return levelUpMapper.getLevelUpBoardDetail(lbNo);
    }

    // 등업신청 수정
    @Override
    public int setLevelUpBoardEdit(LevelUpDTO levelUpDTO) {

        return levelUpMapper.setLevelUpBoardEdit(levelUpDTO);
    }

    // 등업신청 삭제
    @Override
    public int lbDelete(LevelUpDTO levelUpDTO) {

        return levelUpMapper.lbDelete(levelUpDTO);
    }
}
