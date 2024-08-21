package kr.co.vibevillage.levelupBoard.service;

import kr.co.vibevillage.env.Env;
import kr.co.vibevillage.levelupBoard.mapper.LevelUpMapper;
import kr.co.vibevillage.levelupBoard.model.LevelUpDTO;
import kr.co.vibevillage.user.model.dto.UserDTO;
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
    public List<LevelUpDTO> getLevelUpList(String userNickName) {
        List<LevelUpDTO> lbList = levelUpMapper.getLevelUpList(userNickName);
        return lbList;
    }

    // 등업신청 작성
    @Override
    public int setLevelUpBoardEnroll(LevelUpDTO levelUpDTO) {

        int result = levelUpMapper.levelResultBoard(levelUpDTO);

        return levelUpMapper.setLevelUpBoardEnroll(levelUpDTO);
    }

    // 등업신청 Detail
    @Override
    public LevelUpDTO getLevelUpBoardDetail(int lbNo, String userNickName) {

        return levelUpMapper.getLevelUpBoardDetail(lbNo, userNickName);
    }

    // 등업신청 수정
    @Override
    public int setLevelUpBoardEdit(LevelUpDTO levelUpDTO, String userNickName) {

        return levelUpMapper.setLevelUpBoardEdit(levelUpDTO, userNickName);
    }

    // 등업신청 삭제
    @Override
    public int lbDelete(LevelUpDTO levelUpDTO) {

        return levelUpMapper.lbDelete(levelUpDTO);
    }

    @Override
    public int levelUpApprove(int uNo, int lbNo, LevelUpDTO levelUpDTO, UserDTO userDTO) {

        int result = levelUpMapper.levelUpXML(uNo, levelUpDTO, userDTO);

        return levelUpMapper.levelUpApprove(lbNo, levelUpDTO, userDTO);
    }

}