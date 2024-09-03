package kr.co.vibevillage.experienceAndReviewBoard.service;

import kr.co.vibevillage.experienceAndReviewBoard.dto.ExperienceBoardDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.UploadDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ExperienceBoardService {
    List<ExperienceBoardDTO> getAllPosts(int page, int size);
//    void createPost(ExperienceBoardDTO experienceBoardDto, int userNo, MultipartFile[] files) throws IOException;
    void createPost(ExperienceBoardDTO experienceBoardDTO, int userNo) throws IOException;

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


//    void uploadImage(MultipartFile[] files) throws Exception;

    void uploadImage(MultipartFile[] files, Long rId) throws Exception;

    void deleteUploadsByIds(Long[] deleteImageIds);

//    Map<String, Object> uploadImage(MultipartFile file) throws IOException;

//    boolean isCategoryValid(Long categoryId);
}

