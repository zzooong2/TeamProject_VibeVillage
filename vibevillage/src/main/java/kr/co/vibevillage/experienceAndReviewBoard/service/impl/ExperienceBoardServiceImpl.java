package kr.co.vibevillage.experienceAndReviewBoard.service.impl;

import kr.co.vibevillage.experienceAndReviewBoard.domain.ExperienceBoard;
import kr.co.vibevillage.experienceAndReviewBoard.dto.ExperienceBoardDTO;
import kr.co.vibevillage.experienceAndReviewBoard.listmapper.ExperienceBoardMapper;
import kr.co.vibevillage.experienceAndReviewBoard.service.ExperienceBoardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceBoardServiceImpl implements ExperienceBoardService {
    private final ExperienceBoardMapper experienceBoardMapper;

    public ExperienceBoardServiceImpl(ExperienceBoardMapper experienceBoardMapper) {
        this.experienceBoardMapper = experienceBoardMapper;
    }

    @Override
    public List<ExperienceBoard> getAllPosts(int page, int size) {
        int offset = (page - 1) * size;
        return experienceBoardMapper.findAllWithPagination(size, offset);
    }

    @Override
    public void createPost(ExperienceBoardDTO experienceBoardDto) {
        ExperienceBoard experienceBoard = new ExperienceBoard();
        experienceBoard.setUNo(experienceBoardDto.getUNo());
        experienceBoard.setCategoryId(experienceBoardDto.getCategoryId());
        experienceBoard.setRTitle(experienceBoardDto.getRTitle());
        experienceBoard.setRContent(experienceBoardDto.getRContent());
        experienceBoardMapper.insert(experienceBoard); // Mapper 호출 추가
    }

    @Override
    public void updatePost(Long rId, ExperienceBoardDTO experienceBoardDto) {
        ExperienceBoard experienceBoard = new ExperienceBoard();
        experienceBoard.setRId(rId);
        experienceBoard.setRTitle(experienceBoardDto.getRTitle());
        experienceBoard.setRContent(experienceBoardDto.getRContent());
        experienceBoardMapper.update(experienceBoard); // Mapper 호출 추가
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
        ExperienceBoard experienceBoard = experienceBoardMapper.findById(rId);
        if (experienceBoard == null) {
            return null;
        }

        ExperienceBoardDTO dto = new ExperienceBoardDTO();
        dto.setRId(experienceBoard.getRId());
        dto.setUNo(experienceBoard.getUNo());
        dto.setCategoryId(experienceBoard.getCategoryId());
        dto.setRTitle(experienceBoard.getRTitle());
        dto.setRContent(experienceBoard.getRContent());
        dto.setRCreatedAt(experienceBoard.getRCreatedAt());
        dto.setRUpdatedAt(experienceBoard.getRUpdatedAt());
        dto.setRViewCount(Math.toIntExact(experienceBoard.getRViewCount()));
        dto.setRLikeCount(Math.toIntExact(experienceBoard.getRLikeCount()));

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
    public List<ExperienceBoard> getPostsWithCommentCount() {
        return experienceBoardMapper.getPostsWithCommentCount();
    }

}
