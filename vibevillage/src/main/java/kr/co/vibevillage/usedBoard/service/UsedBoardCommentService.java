package kr.co.vibevillage.usedBoard.service;

import kr.co.vibevillage.usedBoard.model.UsedBoardCommentDto;

import java.util.List;

public interface UsedBoardCommentService {
    public int putComment(UsedBoardCommentDto commentDto);
    public List<UsedBoardCommentDto> getCommentList(int id);
    public int deleteComment(int commentId);
}
