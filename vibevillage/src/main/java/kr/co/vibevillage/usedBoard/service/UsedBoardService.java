package kr.co.vibevillage.usedBoard.service;

import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import kr.co.vibevillage.usedBoard.model.UsedPageInfoDto;
import org.springframework.ui.Model;

import java.util.List;

public interface UsedBoardService {

    // 중고 거래 리스트 GET
    public List<UsedBoardDto> getUsedBoardList();
    public List<UsedBoardDto> getUsedBoardList(UsedPageInfoDto pi);
    // 중고 거래 게시글 정보 GET
    public UsedBoardDto getUsedBoardDetail(int id);
    // 중고 거래 게시글 메인 이미지 GET
    public List<UsedBoardImageDto> getUsedBoardMainImage(int id);
    // 중고 거래 게시글 서브 이미지 GET
    public List<UsedBoardImageDto> getUsedBoardSubImage(int id);
    // 중고 거래 게시글 리스트 메인 이미지 GET
    public List<UsedBoardDto> getFilteredUsedBoardList(UsedPageInfoDto pi);
    // 중고 거래 게시글 등록
    int enrollUsedBoard(UsedBoardDto usedBoard);
}
