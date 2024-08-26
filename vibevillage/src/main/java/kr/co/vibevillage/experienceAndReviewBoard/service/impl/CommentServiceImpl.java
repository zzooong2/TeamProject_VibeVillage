package kr.co.vibevillage.experienceAndReviewBoard.service.impl;

import kr.co.vibevillage.experienceAndReviewBoard.dto.CommentDTO;
import kr.co.vibevillage.experienceAndReviewBoard.listmapper.CommentMapper;
import kr.co.vibevillage.experienceAndReviewBoard.service.CommentService;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final LoginServiceImpl loginService;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper, LoginServiceImpl loginService) {
        this.commentMapper = commentMapper;
        this.loginService = loginService;
    }

    @Override
    public void addComment(CommentDTO commentDTO) {
        UserDTO loginUser = loginService.getLoginUserInfo();
        int userNo = loginUser.getUserNo();
        commentMapper.addCommentCount(userNo);
        commentDTO.setCCreatedAt(LocalDateTime.now());
        commentMapper.insertComment(commentDTO);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(Long rId) {
        return commentMapper.getCommentsByPostId(rId);
    }

    @Override
    public List<CommentDTO> getCommentsByRId(Long rId) {
        return commentMapper.findByRId(rId);
    }

    @Override
    public void deleteComment(Long commentId) {
        UserDTO loginUser = loginService.getLoginUserInfo();
        int userNo = loginUser.getUserNo();
        commentMapper.subCommentCount(userNo);

        commentMapper.deleteComment(commentId);
    }

    @Override
    public int getCommentCountByPostId(Long rId) {
        return commentMapper.countCommentsByPostId(rId);
    }

    @Override
    public CommentDTO getCommentById(Long commentId) {
        return commentMapper.getCommentById(commentId);
    }
}
