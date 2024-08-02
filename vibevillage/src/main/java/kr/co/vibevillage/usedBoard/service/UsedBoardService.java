package kr.co.vibevillage.usedBoard.service;

import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import org.springframework.ui.Model;

import java.util.List;

public interface UsedBoardService {

    // 포스트 가져오기
    public List<UsedBoardDto> getUsedBoardList();
    public UsedBoardDto getUsedBoardDetail(String id);
    public String createUsedBoard();

    int enrollUsedBoard(UsedBoardDto usedBoard);
}
