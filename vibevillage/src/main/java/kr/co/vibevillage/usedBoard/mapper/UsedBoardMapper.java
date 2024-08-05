package kr.co.vibevillage.usedBoard.mapper;

import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UsedBoardMapper {
    public List<UsedBoardDto> usedBoardListXML();
    public UsedBoardDto usedBoardDetailXML(int id);
    public int usedBoardEnrollXML(UsedBoardDto usedBoard);
    public int usedBoardImageXML(UsedBoardImageDto image);
    public List<UsedBoardImageDto> usedBoardImageListXML(int id);
    public int usedBoardProductEnrollXML(UsedBoardDto usedBoard);
}
