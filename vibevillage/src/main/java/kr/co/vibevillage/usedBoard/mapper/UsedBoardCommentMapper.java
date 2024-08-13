package kr.co.vibevillage.usedBoard.mapper;

import kr.co.vibevillage.usedBoard.model.UsedBoardCommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UsedBoardCommentMapper {
    public int putCommentXML(@Param("comment")UsedBoardCommentDto commentDto);
    public List<UsedBoardCommentDto> getCommentListXML(@Param("id") int id, @Param("comment")UsedBoardCommentDto commentDto);
}
