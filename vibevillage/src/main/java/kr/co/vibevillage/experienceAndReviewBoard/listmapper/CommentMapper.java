package kr.co.vibevillage.experienceAndReviewBoard.listmapper;

import kr.co.vibevillage.experienceAndReviewBoard.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    void insertComment(CommentDTO commentDTO);
    List<CommentDTO> findByRId(Long rId); // 게시물 ID로 댓글 목록 조회
    void deleteComment(Long commentId); // 댓글 삭제

    List<CommentDTO> getCommentsByPostId(Long rId);

    int countCommentsByPostId(Long rId);

    CommentDTO getCommentById(Long commentId);
}
