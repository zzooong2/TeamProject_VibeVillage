package kr.co.vibevillage.experienceAndReviewBoard.service;

import kr.co.vibevillage.experienceAndReviewBoard.dto.ExperienceBoardDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.UploadDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExperienceBoardService {
    List<ExperienceBoardDTO> getAllPosts(int page, int size);
    void createPost(ExperienceBoardDTO experienceBoardDto, int userNo, MultipartFile[] files) throws IOException;

    void updatePost(Long rId, ExperienceBoardDTO experienceBoardDto, MultipartFile[] files) throws IOException;

    void deletePost(Long rId);
    ExperienceBoardDTO getPostById(Long rId);

    void deletePostAndRelatedData(Long postId);

    List<ExperienceBoardDTO> searchPosts(@Param("keyword") String keyword);
    List<ExperienceBoardDTO> getPostsByCategory(Long categoryId);
    List<ExperienceBoardDTO> getAllPosts();
    List<ExperienceBoardDTO> getTopLikedPosts(int page, int size);
    int getTotalPostCount();
    String getCategoryNameById(Long categoryId);
    int countTotalRecommendedPosts();

    void deleteUploadsByIds(Long[] deleteImageIds);







}

