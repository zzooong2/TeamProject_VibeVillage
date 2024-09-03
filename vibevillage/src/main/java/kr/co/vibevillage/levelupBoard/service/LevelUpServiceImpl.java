package kr.co.vibevillage.levelupBoard.service;

import kr.co.vibevillage.env.Env;
import kr.co.vibevillage.levelupBoard.mapper.LevelUpMapper;
import kr.co.vibevillage.levelupBoard.model.LevelUpDTO;
import kr.co.vibevillage.user.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;

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
    public List<LevelUpDTO> getLevelUpList(String userNickName, String lrStatus) {
        List<LevelUpDTO> lbList = levelUpMapper.getLevelUpList(userNickName, lrStatus);
        return lbList;
    }

    // 등업신청 작성
    @Override
    public int setLevelUpBoardEnroll(LevelUpDTO levelUpDTO, int uNo, int lbNo) {

        int resultDTO = levelUpMapper.setLevelUpBoardEnroll(levelUpDTO, uNo);

        int result = levelUpMapper.levelResultBoard(levelUpDTO, uNo, lbNo);

        return result;
    }

    @Override
    public int lbCount(int uNo) {

        return levelUpMapper.lbCount(uNo);
    }

    // 등업신청 Detail
    @Override
    public LevelUpDTO getLevelUpBoardDetail(int lbNo, String userNickName) {

        return levelUpMapper.getLevelUpBoardDetail(lbNo, userNickName);
    }

    // 등업신청 수정
    @Override
    public int setLevelUpBoardEdit(LevelUpDTO levelUpDTO, int lbNo) {

        return levelUpMapper.setLevelUpBoardEdit(levelUpDTO, lbNo);
    }

    // 등업신청 삭제
    @Override
    public int lbDelete(LevelUpDTO levelUpDTO) {

        return levelUpMapper.lbDelete(levelUpDTO);
    }

    @Override
    public int lbCountMinus(int uNo) {

        return levelUpMapper.lbCountMinus(uNo);
    }

    // 등업 승인
    @Override
    public int levelUpApprove(int uNo, int lbNo, LevelUpDTO levelUpDTO) {

        int result = levelUpMapper.levelUpXML(uNo, levelUpDTO);

        return levelUpMapper.levelUpApprove(lbNo, levelUpDTO);
    }

    // 등업 반려
    @Override
    public int levelUpReject(int uNo, LevelUpDTO levelUpDTO) {

        return levelUpMapper.levelUpReject(uNo, levelUpDTO);
    }

}
