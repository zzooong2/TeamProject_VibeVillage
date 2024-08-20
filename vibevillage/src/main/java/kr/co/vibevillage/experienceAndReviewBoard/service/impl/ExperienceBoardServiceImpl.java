package kr.co.vibevillage.experienceAndReviewBoard.service.impl;

import kr.co.vibevillage.experienceAndReviewBoard.dto.ExperienceBoardDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.UploadDTO;
import kr.co.vibevillage.experienceAndReviewBoard.listmapper.ExperienceBoardMapper;
import kr.co.vibevillage.experienceAndReviewBoard.service.ExperienceBoardService;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceBoardServiceImpl implements ExperienceBoardService {

    private final ExperienceBoardMapper experienceBoardMapper;
    private final LoginServiceImpl loginServiceImpl;

    @Autowired
    public ExperienceBoardServiceImpl(ExperienceBoardMapper experienceBoardMapper, LoginServiceImpl loginServiceImpl) {
        this.experienceBoardMapper = experienceBoardMapper;
        this.loginServiceImpl = loginServiceImpl;
    }

    @Override
    public List<ExperienceBoardDTO> getAllPosts(int page, int size) {
        int offset = (page - 1) * size;
        return experienceBoardMapper.findAllWithPagination(size, offset);
    }

    @Override
    public void createPost(ExperienceBoardDTO experienceBoardDto, int userNo) {
        System.out.println("================================ExperienceboardService==================================");

        experienceBoardDto.setUNo((long) userNo);  // 사용자 번호 설정
        experienceBoardMapper.createPost(experienceBoardDto);


        ExperienceBoardDTO experienceBoard = new ExperienceBoardDTO();
        experienceBoard.setUNo(experienceBoardDto.getUNo());
        experienceBoard.setCategoryId(experienceBoardDto.getCategoryId());
        experienceBoard.setRTitle(experienceBoardDto.getRTitle());
        experienceBoard.setRContent(experienceBoardDto.getRContent());

        System.out.println("No: " + userNo);
        System.out.println("CategoryId: " + experienceBoardDto.getCategoryId());
        System.out.println("Title: " + experienceBoardDto.getRTitle());
        System.out.println("Content: " + experienceBoardDto.getRContent());


        experienceBoardMapper.insert(experienceBoard, userNo); // Mapper 호출 추가
    }

    @Override
    public void updatePost(Long rId, ExperienceBoardDTO experienceBoardDto) {
        ExperienceBoardDTO experienceBoard = new ExperienceBoardDTO();
        experienceBoard.setRId(rId);
        experienceBoard.setRTitle(experienceBoardDto.getRTitle());
        experienceBoard.setRContent(experienceBoardDto.getRContent());
        experienceBoardMapper.update(experienceBoardDto); // Mapper 호출 추가
    }

    @Override
    public void deletePost(Long rId) {
        experienceBoardMapper.delete(rId); // Mapper 호출 추가
    }

    @Override
    public List<ExperienceBoardDTO> searchPosts(String keyword) {
        return experienceBoardMapper.searchPosts(keyword);
    }

    @Override
    public ExperienceBoardDTO getPostById(Long rId) {
        // 조회수 증가
        experienceBoardMapper.incrementViewCount(rId);
        // 게시글 상세정보 가져오기
        ExperienceBoardDTO experienceBoardDTO = experienceBoardMapper.findById(rId);
        if (experienceBoardDTO == null) {
            return null;
        }

        ExperienceBoardDTO dto = new ExperienceBoardDTO();
        dto.setRId(experienceBoardDTO.getRId());
        dto.setUNo(experienceBoardDTO.getUNo());
        dto.setCategoryId(experienceBoardDTO.getCategoryId());
        dto.setRTitle(experienceBoardDTO.getRTitle());
        dto.setRContent(experienceBoardDTO.getRContent());
        dto.setRCreatedAt(experienceBoardDTO.getRCreatedAt());
        dto.setRUpdatedAt(experienceBoardDTO.getRUpdatedAt());
        dto.setRViewCount(Math.toIntExact(experienceBoardDTO.getRViewCount()));
        dto.setRLikeCount(Math.toIntExact(experienceBoardDTO.getRLikeCount()));

        return dto;
    }
    @Override
    public List<ExperienceBoardDTO> getOtherPosts(Long excludeId) {
        return experienceBoardMapper.findOtherPosts(excludeId);
    }

    @Override
    public void toggleLike(Long rId, Long uNo) {
        if (experienceBoardMapper.isLiked(rId, uNo)) {
            experienceBoardMapper.deleteLike(rId, uNo);
        } else {
            experienceBoardMapper.insertLike(rId, uNo);
        }
    }



    @Override
    public boolean isPostLikedByUser(Long rId, Long uNo) {
        return experienceBoardMapper.isLiked(rId, uNo);
    }

    @Override
    public int countLikes(Long rId) {
        return experienceBoardMapper.countLikes(rId);
    }

    @Override
    public List<ExperienceBoardDTO> getPostsWithCommentCount() {
        return experienceBoardMapper.getPostsWithCommentCount();
    }

    @Override
    public List<ExperienceBoardDTO> getPostsWithCommentCount(int page, int size) {
        return List.of();
    }

    @Override
    public List<ExperienceBoardDTO> getTopLikedPosts() {
        return experienceBoardMapper.getTopLikedPosts();
    }

    @Override
    public List<ExperienceBoardDTO> getPostsByCategory(Long categoryId) {
        List<ExperienceBoardDTO> posts = experienceBoardMapper.findPostsByCategory(categoryId);

        if (posts == null || posts.isEmpty()) {
            return List.of(); // 게시글이 없을 경우 빈 리스트 반환
        }

        for (ExperienceBoardDTO post : posts) {
            if (post != null) {
                String categoryName = experienceBoardMapper.getCategoryNameById(post.getCategoryId());
                post.setCategoryName(categoryName);
            } else {
                // post가 null일 경우 적절한 처리
                System.err.println("Null post encountered!");
            }
        }

        return posts;
    }

    @Override
    public List<ExperienceBoardDTO> getAllPosts() {
        List<ExperienceBoardDTO> posts = experienceBoardMapper.findAllPosts();  // 모든 게시글 조회

        // 각 게시글에 카테고리 이름을 설정
        for (ExperienceBoardDTO post : posts) {
            String categoryName = experienceBoardMapper.getCategoryNameById(post.getCategoryId());
            post.setCategoryName(categoryName);
        }

        return posts;
    }


    @Override
    public void saveUpload(UploadDTO uploadDTO) {
        experienceBoardMapper.insertUpload(uploadDTO);
    }

    @Override
    public String getCategoryNameById(Long categoryId) {
        return experienceBoardMapper.getCategoryNameById(categoryId);
    }

    @Override
    public int countTotalRecommendedPosts() {
        return 0;
    }

    @Override
    public int getTotalPostCount() {
        return experienceBoardMapper.countTotalPosts();
    }

    @Override
    public List<ExperienceBoardDTO> getTopLikedPosts(int page, int size) {
        int offset = (page - 1) * size;
        return experienceBoardMapper.findTopLikedPosts(offset, size);
    }

    @Override
    public int getTotalRecommendedPosts() {
        return experienceBoardMapper.countTotalRecommendedPosts();
    }
}
