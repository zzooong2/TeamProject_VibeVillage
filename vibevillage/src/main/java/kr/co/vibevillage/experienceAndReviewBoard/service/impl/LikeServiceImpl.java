package kr.co.vibevillage.experienceAndReviewBoard.service.impl;

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
        LikeDTO like = new LikeDTO();
        like.setRId(likeDto.getRId());
        like.setUNo(likeDto.getUNo());

        if (likeMapper.hasLiked(like) > 0) {
            likeMapper.delete(like);
        } else {
            likeMapper.insert(like);
        }
    }

    @Override
    public boolean hasLiked(Long rId, Long uNo) {
        LikeDTO like = new LikeDTO();
        like.setRId(rId);
        like.setUNo(uNo);
        return likeMapper.hasLiked(like) > 0;
    }


    @Override
    public int countLikes(Long rId) {
        return likeMapper.countLikes(rId);
    }

    @Override
    public boolean hasLiked(LikeDTO likeDto) {
        LikeDTO like = new LikeDTO();
        like.setRId(likeDto.getRId());
        like.setUNo(likeDto.getUNo());
        return likeMapper.hasLiked(like) > 0;
    }

    @Override
    public void updateLikeCount(Long rId) {
        Integer likeCount = likeMapper.countLikes(rId);
        int safeLikeCount = (likeCount != null) ? likeCount : 0;

        likeMapper.updateLikeCount(rId, safeLikeCount);
    }

    @Override
    public void addLike(Long rId, Long uNo) {
        LikeDTO like = new LikeDTO();
        like.setRId(rId);
        like.setUNo(uNo);
        likeMapper.insert(like);
    }

    @Override
    public void deleteLike(Long rId, Long uNo) {
        LikeDTO like = new LikeDTO();
        like.setRId(rId);
        like.setUNo(uNo);
        likeMapper.delete(like);
    }
    @Override
    public void likePost(LikeDTO likeDTO) {
        addLike(likeDTO.getRId(), likeDTO.getUNo());
    }

}
