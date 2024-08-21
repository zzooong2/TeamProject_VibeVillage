package kr.co.vibevillage.experienceAndReviewBoard.service.impl;

import kr.co.vibevillage.experienceAndReviewBoard.dto.CommentDTO;
import kr.co.vibevillage.experienceAndReviewBoard.listmapper.CommentMapper;
import kr.co.vibevillage.experienceAndReviewBoard.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public void addComment(CommentDTO commentDTO) {
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
