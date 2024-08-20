package kr.co.vibevillage.experienceAndReviewBoard.service;

import kr.co.vibevillage.experienceAndReviewBoard.dto.ExperienceBoardDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.UploadDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExperienceBoardService {
    List<ExperienceBoardDTO> getAllPosts(int page, int size);
    void createPost(ExperienceBoardDTO experienceBoardDto);
    void updatePost(Long rId, ExperienceBoardDTO experienceBoardDto);
    void deletePost(Long rId);
    ExperienceBoardDTO getPostById(Long rId);
    List<ExperienceBoardDTO> searchPosts(@Param("keyword") String keyword);

    List<ExperienceBoardDTO> getTopLikedPosts();

    List<ExperienceBoardDTO> getPostsByCategory(Long categoryId);
    List<ExperienceBoardDTO> getAllPosts();
    List<ExperienceBoardDTO> getTopLikedPosts(int page, int size);
    int getTotalPostCount();

    void saveUpload(UploadDTO uploadDTO);


    List<ExperienceBoardDTO> getOtherPosts(Long excludeId);

    void toggleLike(Long rId, Long uNo);
    boolean isPostLikedByUser(Long rId, Long uNo);
    int countLikes(Long rId);

    List<ExperienceBoardDTO> getPostsWithCommentCount();

    List<ExperienceBoardDTO> getPostsWithCommentCount(int page, int size);

    String getCategoryNameById(Long categoryId);

    int countTotalRecommendedPosts();

    int getTotalRecommendedPosts();
}

