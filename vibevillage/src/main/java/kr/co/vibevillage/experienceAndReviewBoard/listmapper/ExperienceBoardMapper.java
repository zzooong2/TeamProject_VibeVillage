package kr.co.vibevillage.experienceAndReviewBoard.listmapper;

import kr.co.vibevillage.experienceAndReviewBoard.dto.ExperienceBoardDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.UploadDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExperienceBoardMapper {
    List<ExperienceBoardDTO> findAllWithPagination(@Param("limit") int limit, @Param("offset") int offset);

    void insertUpload(UploadDTO uploadDTO);

//    void insert(ExperienceBoardDTO experienceBoard, int userNo);

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

    // 추천글을 가져오는 메서드 (페이징 처리)
    List<ExperienceBoardDTO> findTopLikedPosts(@Param("offset") int offset, @Param("size") int size);

    // 추천글의 총 개수를 세는 메서드
    int countTotalRecommendedPosts();

    void createPost(ExperienceBoardDTO experienceBoardDTO);
    void updatePost(ExperienceBoardDTO experienceBoardDTO);
}
