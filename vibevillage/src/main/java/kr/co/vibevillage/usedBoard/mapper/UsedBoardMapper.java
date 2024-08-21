package kr.co.vibevillage.usedBoard.mapper;

import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import kr.co.vibevillage.usedBoard.model.UsedPageInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UsedBoardMapper {
    public List<UsedBoardDto> usedBoardListXML(@Param("category")int category,@Param("province")String province,@Param("citySelect")String citySelect);
    public List<UsedBoardDto> usedBoardPaginationXML(@Param("pi") UsedPageInfoDto pi,@Param("category")int category,@Param("province")String province,@Param("citySelect")String citySelect);
    public UsedBoardDto usedBoardDetailXML(@Param("id") int id);
    public int usedBoardEnrollXML(@Param("usedBoard") UsedBoardDto usedBoard);
    public int usedBoardProductEnrollXML(@Param("usedBoard")UsedBoardDto usedBoard);
    public int increaseViewCountXML(@Param("boardId")int boardId);
    public int deleteDetail(int id);
    public List<UsedBoardDto> getMyBoards(@Param("id")int id);
    void updateUsedBoard(UsedBoardDto usedBoard);
    void updateProduct(UsedBoardDto usedBoard);
}
