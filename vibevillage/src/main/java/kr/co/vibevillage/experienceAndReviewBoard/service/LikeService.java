package kr.co.vibevillage.experienceAndReviewBoard.service;

import kr.co.vibevillage.experienceAndReviewBoard.dto.LikeDTO;

public interface LikeService {
    void toggleLike(LikeDTO likeDto);
    boolean hasLiked(LikeDTO likeDto);

    void likePost(LikeDTO likeDTO);
    // 좋아요 삭제
    void deleteLike(Long rId, Long uNo);

    // 특정 게시글의 좋아요 수 조회
    int countLikes(Long rId);

    // 사용자가 해당 게시글에 좋아요를 눌렀는지 확인
    boolean hasLiked(Long rId, Long uNo);

    // 게시글의 좋아요 수 업데이트
    void updateLikeCount(Long rId);
    // 좋아요 추가
    void addLike(Long id, Long uNo);

    // 추가된 메서드
    void insertLike(LikeDTO likeDto);

    // 추가된 메서드
}
