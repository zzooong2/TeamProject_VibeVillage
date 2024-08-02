package kr.co.vibevillage.usedBoard.mapper;

import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsedBoardMapper {
    public List<UsedBoardDto> usedBoardListXML();
    public UsedBoardDto usedBoardDetailXML(String id);
    public int usedBoardEnrollXML(UsedBoardDto usedBoard);
    public int usedBoardImageXML(UsedBoardImageDto image);
}
