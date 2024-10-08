package kr.co.vibevillage.usedBoard.service;

import kr.co.vibevillage.usedBoard.mapper.UsedBoardImageMapper;
import kr.co.vibevillage.usedBoard.mapper.UsedBoardMapper;
import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import kr.co.vibevillage.usedBoard.model.UsedPageInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsedBoardServiceImpl implements UsedBoardService {
    private final UsedBoardImageMapper imageMapper;
    private final UsedBoardMapper usedBoardMapper;
    private final UsedBoardImageMapper usedBoardImageMapper;



    // 전체 리스트 가져오기
    @Override
    public List<UsedBoardDto> getUsedBoardList(int category,String province,String citySelect) {
        List<UsedBoardDto> list = usedBoardMapper.usedBoardListXML(category,province,citySelect);
        return list;
    }
    @Override
    public List<UsedBoardDto> getUsedBoardList(UsedPageInfoDto pi,int category,String province,String citySelect) {
        List<UsedBoardDto> list = usedBoardMapper.usedBoardPaginationXML(pi,category,province,citySelect);
        return list;
    }

    @Override
    public List<UsedBoardImageDto> getUsedBoardMainImage(int id){
        List<UsedBoardImageDto> mainImage = usedBoardImageMapper.usedBoardGetImageListOnceXML(id);
        return mainImage;
    }
    @Override
    public List<UsedBoardImageDto> getUsedBoardSubImage(int id){
        List<UsedBoardImageDto> subImage = usedBoardImageMapper.usedBoardGetImageListXML(id);
        return subImage;
    }
    @Override
    public UsedBoardDto getUsedBoardDetail(int id) {
        UsedBoardDto result = usedBoardMapper.usedBoardDetailXML(id);
        return result;
    }
    @Override
    public int enrollUsedBoard(UsedBoardDto usedBoard){
        int result = usedBoardMapper.usedBoardEnrollXML(usedBoard);
        usedBoardMapper.usedBoardProductEnrollXML(usedBoard);
        if (result > 0) {
            for (UsedBoardImageDto image : usedBoard.getImages()) {
                image.setUsedBoardId(usedBoard.getUsedBoardId()); // 새로 생성된 게시물 ID 설정
                imageMapper.usedBoardEnrollImageXML(image);
            }
            usedBoardMapper.increaseUserWriteCount(usedBoard.getUserNo());
        }
        return result > 0 ? 1 : 0; // 성공하면 1, 실패하면 0
    }

    @Override
    public List<UsedBoardDto> getFilteredUsedBoardList(UsedPageInfoDto pi,int category,String province,String citySelect) {
        // 전체 게시글 리스트를 가져옵니다.
        List<UsedBoardDto> list = getUsedBoardList(pi,category,province,citySelect);
        // 각 게시글에 대해 반복문을 돌립니다.
        for (UsedBoardDto board : list) {
            // 현재 게시글의 이미지 리스트를 가져옵니다.
            List<UsedBoardImageDto> mainImages = imageMapper.usedBoardGetImageListOnceXML(board.getUsedBoardId());
            // MAIN 타입의 이미지들로 현재 게시글의 이미지 리스트를 설정합니다.
            board.setImages(mainImages);
        }
        // 필터링된 게시글 리스트를 반환합니다.
        return list;
    }

    @Override
    @Transactional
    public int increaseViewCount(int boardId) {
        return usedBoardMapper.increaseViewCountXML(boardId);  // 조회수 증가
    }

    @Override
    public int deleteDetail(int id){
        return usedBoardMapper.deleteDetail(id);
    }


    @Override
    public List<UsedBoardDto> getMyBoards(int id){
        List<UsedBoardDto> list =  usedBoardMapper.getMyBoards(id);;
        // 각 게시글에 대해 반복문을 돌립니다.
        for (UsedBoardDto board : list) {
            // 현재 게시글의 이미지 리스트를 가져옵니다.
            List<UsedBoardImageDto> mainImages = imageMapper.usedBoardGetImageListOnceXML(board.getUsedBoardId());
            // MAIN 타입의 이미지들로 현재 게시글의 이미지 리스트를 설정합니다.
            board.setImages(mainImages);
        }
        return list;
    }
    @Override
    public void updateUsedBoard(UsedBoardDto usedBoard,List<Integer> deleteList) {
        // 게시물 정보 업데이트
        usedBoardMapper.updateUsedBoard(usedBoard);
        usedBoardMapper.updateProduct(usedBoard);
        int deleteResult;
        // 삭제할 이미지가 있을 경우
        if(deleteList != null){
            for (Integer id : deleteList) {
                deleteResult = usedBoardImageMapper.deleteImages(id);
            }
        }
        // 등록할 이미지가 있을 경우
        if( usedBoard.getImages() != null) {
            for (UsedBoardImageDto image : usedBoard.getImages()) {
                image.setUsedBoardId(usedBoard.getUsedBoardId()); // 새로 생성된 게시물 ID 설정
                imageMapper.usedBoardEnrollImageXML(image);
            }
        }
    }
    @Override
    public int convertProductStatus(int id ,String status){
        int result = usedBoardMapper.convertProductStatus(id,status);
        return result;
    }

    @Override
    public List<UsedBoardDto> searchUsedBoard(String keyword){

     List<UsedBoardDto> searchList = usedBoardMapper.searchUsedBoard(keyword);
     for (UsedBoardDto board : searchList) {
         List<UsedBoardImageDto> mainImage = getUsedBoardMainImage(board.getUsedBoardId());
         board.setImages(mainImage);
     }
     return searchList;
    }



}
