package kr.co.vibevillage.experienceAndReviewBoard.service.impl;

import kr.co.vibevillage.experienceAndReviewBoard.domain.Comment;
import kr.co.vibevillage.experienceAndReviewBoard.dto.CommentDTO;
import kr.co.vibevillage.experienceAndReviewBoard.listmapper.CommentMapper;
import kr.co.vibevillage.experienceAndReviewBoard.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public void addComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setRId(commentDTO.getRId());
        comment.setUNo(commentDTO.getUNo());
        comment.setCContent(commentDTO.getCContent());
        commentMapper.insertComment(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long rId) {
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
}
