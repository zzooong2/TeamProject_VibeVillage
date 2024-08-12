package kr.co.vibevillage.experienceAndReviewBoard.listmapper;

import kr.co.vibevillage.experienceAndReviewBoard.domain.ExperienceBoard;
import kr.co.vibevillage.experienceAndReviewBoard.dto.ExperienceBoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExperienceBoardMapper {
    List<ExperienceBoard> findAllWithPagination(@Param("limit") int limit, @Param("offset") int offset);

    void insert(ExperienceBoard experienceBoard);

    void update(ExperienceBoard experienceBoard);

    void delete(@Param("rId") Long rId);

    List<ExperienceBoard> search(@Param("keyword") String keyword);

    ExperienceBoard findById(Long rId);

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
}
