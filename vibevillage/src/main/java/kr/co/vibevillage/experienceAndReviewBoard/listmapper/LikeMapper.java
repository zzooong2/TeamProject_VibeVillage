package kr.co.vibevillage.experienceAndReviewBoard.listmapper;

import kr.co.vibevillage.experienceAndReviewBoard.dto.LikeDTO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LikeMapper {

    void insert(LikeDTO likeDTO);

    void delete(LikeDTO likeDTO);

    int countLikes(Long rId);

    int hasLiked(LikeDTO likeDTO);

    void updateLikeCount(Long rId, int safeLikeCount);
}
