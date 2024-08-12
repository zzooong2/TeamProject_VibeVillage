package kr.co.vibevillage.experienceAndReviewBoard.listmapper;

import kr.co.vibevillage.experienceAndReviewBoard.domain.Like;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LikeMapper {

    void insert(Like like);

    void delete(Like like);

    int countLikes(Long rId);

    int hasLiked(Like like);
}
