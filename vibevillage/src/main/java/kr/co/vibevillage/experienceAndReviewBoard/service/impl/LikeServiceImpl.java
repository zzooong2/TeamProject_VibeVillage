package kr.co.vibevillage.experienceAndReviewBoard.service.impl;

import kr.co.vibevillage.experienceAndReviewBoard.domain.Like;
import kr.co.vibevillage.experienceAndReviewBoard.dto.LikeDTO;
import kr.co.vibevillage.experienceAndReviewBoard.listmapper.LikeMapper;
import kr.co.vibevillage.experienceAndReviewBoard.service.LikeService;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeMapper likeMapper;

    public LikeServiceImpl(LikeMapper likeMapper) {
        this.likeMapper = likeMapper;
    }

    @Override
    public void toggleLike(LikeDTO likeDto) {
        Like like = new Like();
        like.setRId(likeDto.getRId());
        like.setUNo(likeDto.getUNo());

        if (likeMapper.hasLiked(like) > 0) {
            likeMapper.delete(like);
        } else {
            likeMapper.insert(like);
        }
    }

    @Override
    public int countLikes(Long rId) {
        return likeMapper.countLikes(rId);
    }

    @Override
    public boolean hasLiked(Long rId, Long uNo) {
        return false;
    }

    @Override
    public void updateLikeCount(Long rId) {

    }

    @Override
    public void addLike(Long id, Long uNo) {

    }

    @Override
    public boolean hasLiked(LikeDTO likeDto) {
        Like like = new Like();
        like.setRId(likeDto.getRId());
        like.setUNo(likeDto.getUNo());
        return likeMapper.hasLiked(like) > 0;
    }

    @Override
    public void likePost(LikeDTO likeDTO) {

    }

    @Override
    public void deleteLike(Long rId, Long uNo) {

    }
}
