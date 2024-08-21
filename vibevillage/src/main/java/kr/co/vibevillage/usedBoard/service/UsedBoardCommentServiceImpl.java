package kr.co.vibevillage.usedBoard.service;

import kr.co.vibevillage.usedBoard.mapper.UsedBoardCommentMapper;
import kr.co.vibevillage.usedBoard.model.UsedBoardCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsedBoardCommentServiceImpl implements UsedBoardCommentService{
    private final UsedBoardCommentMapper usedBoardCommentMapper;
    private UsedBoardCommentMapper commentMapper;
    @Autowired
    public UsedBoardCommentServiceImpl(UsedBoardCommentMapper commentMapper, UsedBoardCommentMapper usedBoardCommentMapper){
        this.commentMapper = commentMapper;
        this.usedBoardCommentMapper = usedBoardCommentMapper;
    }
    @Override
    public int putComment(UsedBoardCommentDto comment){
        int result = usedBoardCommentMapper.putCommentXML(comment);
        return result;
    }

    @Override
    public List<UsedBoardCommentDto> getCommentList(int id){
        List<UsedBoardCommentDto> commentList = usedBoardCommentMapper.getCommentListXML(id,new UsedBoardCommentDto());
        for(UsedBoardCommentDto item : commentList){
            String nickName = commentMapper.getCommentListUser(item.getUNo());
            item.setUserNickName(nickName);
        }
        return commentList;
    }

}
