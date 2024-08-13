package kr.co.vibevillage.experienceAndReviewBoard.service;

import kr.co.vibevillage.experienceAndReviewBoard.domain.Comment;
import kr.co.vibevillage.experienceAndReviewBoard.dto.CommentDTO;
import java.util.List;

public interface CommentService {
    void addComment(CommentDTO commentDTO);
    List<Comment> getCommentsByPostId(Long postId);

    List<CommentDTO> getCommentsByRId(Long rId);

    void deleteComment(Long id);

    int getCommentCountByPostId(Long rId);
}
