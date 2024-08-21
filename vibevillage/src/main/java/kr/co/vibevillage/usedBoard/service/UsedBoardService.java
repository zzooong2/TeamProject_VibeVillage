package kr.co.vibevillage.usedBoard.service;

import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import kr.co.vibevillage.usedBoard.model.UsedPageInfoDto;
import kr.co.vibevillage.user.model.dto.UserDTO;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UsedBoardService {

    // 중고 거래 리스트 GET
    public List<UsedBoardDto> getUsedBoardList(int category,String province,String citySelect);
    public List<UsedBoardDto> getUsedBoardList(UsedPageInfoDto pi,int category,String province,String citySelect);
    // 중고 거래 게시글 정보 GET
    public UsedBoardDto getUsedBoardDetail(int id);
    // 중고 거래 게시글 메인 이미지 GET
    public List<UsedBoardImageDto> getUsedBoardMainImage(int id);
    // 중고 거래 게시글 서브 이미지 GET
    public List<UsedBoardImageDto> getUsedBoardSubImage(int id);
    // 중고 거래 게시글 리스트 메인 이미지 GET
    public List<UsedBoardDto> getFilteredUsedBoardList(UsedPageInfoDto pi,int category,String province,String citySelect);
    // 중고 거래 게시글 등록
    public int enrollUsedBoard(UsedBoardDto usedBoard);
    // 게시글 조회수 증가
    public int increaseViewCount(int boardId);
    // 게시글 삭제
    public int deleteDetail(int id);
    public List<UsedBoardDto> getMyBoards(int id);

    void updateUsedBoard(UsedBoardDto usedBoard);

}
