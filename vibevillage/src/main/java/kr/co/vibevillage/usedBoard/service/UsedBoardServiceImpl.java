package kr.co.vibevillage.usedBoard.service;

import kr.co.vibevillage.usedBoard.mapper.UsedBoardImageMapper;
import kr.co.vibevillage.usedBoard.mapper.UsedBoardMapper;
import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import kr.co.vibevillage.usedBoard.model.UsedPageInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsedBoardServiceImpl implements UsedBoardService {
    private final UsedBoardImageMapper imageMapper;
    private final UsedBoardMapper usedBoardMapper;
    private final UsedBoardImageMapper usedBoardImageMapper;

    @Autowired
    public UsedBoardServiceImpl(UsedBoardMapper usedBoardMapper,
                                UsedBoardImageMapper imageMapper, UsedBoardImageMapper usedBoardImageMapper)
    {
        this.imageMapper = imageMapper;
        this.usedBoardMapper = usedBoardMapper;
        this.usedBoardImageMapper = usedBoardImageMapper;
    }

    // 전체 리스트 가져오기
    @Override
    public List<UsedBoardDto> getUsedBoardList() {
        List<UsedBoardDto> list = usedBoardMapper.usedBoardListXML();
        return list;
    }
    @Override
    public List<UsedBoardDto> getUsedBoardList(UsedPageInfoDto pi) {

        List<UsedBoardDto> list = usedBoardMapper.usedBoardPaginationXML(pi);
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
        List<UsedBoardImageDto> imageList = imageMapper.usedBoardGetImageListXML(id);
        result.setImages(imageList);
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
        }
        return result > 0 ? 1 : 0; // 성공하면 1, 실패하면 0
    }

    @Override
    public List<UsedBoardDto> getFilteredUsedBoardList(UsedPageInfoDto pi) {
        // 전체 게시글 리스트를 가져옵니다.
        List<UsedBoardDto> list = getUsedBoardList(pi);
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


}
