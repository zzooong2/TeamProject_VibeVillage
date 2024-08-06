package kr.co.vibevillage.usedBoard.service;

import kr.co.vibevillage.usedBoard.mapper.UsedBoardImageMapper;
import kr.co.vibevillage.usedBoard.mapper.UsedBoardMapper;
import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsedBoardServiceImpl implements UsedBoardService {
    private final UsedBoardImageMapper imageMapper;
    private final UsedBoardMapper usedBoardMapper;

    @Autowired
    public UsedBoardServiceImpl(UsedBoardMapper usedBoardMapper,
                                UsedBoardImageMapper imageMapper)
    {
        this.imageMapper = imageMapper;
        this.usedBoardMapper = usedBoardMapper;

    }

    // 포스트 가져오기
    @Override
    public List<UsedBoardDto> getUsedBoardList() {
        List<UsedBoardDto> list = usedBoardMapper.usedBoardListXML();
        for (UsedBoardDto item : list) {
            int id = item.getUsedBoardId();
           item.setImages(imageMapper.usedBoardGetImageListXML(id));
        }
        return list;
    }
    @Override
    public String createUsedBoard(){

        return "";
    }
    @Override
    public UsedBoardDto getUsedBoardDetail(int id) {
        UsedBoardDto result = usedBoardMapper.usedBoardDetailXML(id);
        List<UsedBoardImageDto> imageList = imageMapper.usedBoardGetImageListXML(id);
        result.setImages(imageList);
        System.out.println(result.getImages().size()+"이미지 개수");
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

}
