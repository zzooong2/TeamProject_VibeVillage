package kr.co.vibevillage.experienceAndReviewBoard.listmapper;

import kr.co.vibevillage.experienceAndReviewBoard.dto.ExperienceBoardDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.UploadDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;

@Mapper
public interface ExperienceBoardMapper {
    List<ExperienceBoardDTO> findAllWithPagination(@Param("limit") int limit, @Param("offset") int offset);

    void insertUpload(UploadDTO uploadDTO);

    void update(ExperienceBoardDTO experienceBoard);

    void delete(@Param("rId") Long rId);

    List<ExperienceBoardDTO> search(@Param("keyword") String keyword);

    ExperienceBoardDTO findById(Long rId);

    List<ExperienceBoardDTO> findOtherPosts(Long excludeId);
    // 좋아요 추가
    void insertLike(@Param("rId") Long rId, @Param("uNo") Long uNo);

    // 좋아요 삭제
    void deleteLike(@Param("rId") Long rId, @Param("uNo") Long uNo);

    // 특정 게시글에 대한 특정 사용자의 좋아요 상태 확인
    boolean isLiked(@Param("rId") Long rId, @Param("uNo") Long uNo);

    // 게시글의 전체 좋아요 수
    int countLikes(Long rId);

    void incrementViewCount(@Param("rId") Long rId);

    List<ExperienceBoardDTO> searchPosts(String keyword);
    // 댓글 수 카운트
    List<ExperienceBoardDTO> getPostsWithCommentCount();

    List<ExperienceBoardDTO> getTopLikedPosts();

    List<ExperienceBoardDTO> findPostsByCategory(@Param("categoryId")Long categoryId);

    String getCategoryNameById(@Param("categoryId")Long categoryId);

    List<ExperienceBoardDTO> findAllPosts();

    int countTotalPosts();

    List<ExperienceBoardDTO> findTopLikedPosts(@Param("offset") int offset, @Param("size") int size);

    void createPost(ExperienceBoardDTO experienceBoardDto) throws IOException;

    List<UploadDTO> findUploadsByPostId(Long rId);

    void deletePost(Long rId);

    void deleteUploadsByPostId(Long rId);

    void deleteCommentsByPostId(Long postId);

    void updatePost(@Param("rId") Long rId, @Param("rTitle") String rTitle, @Param("rContent") String rContent);

    void deleteUploadById(Long id);

    void addWriteCount(int userNo);

    void subWriteCount(int userNo);
}




