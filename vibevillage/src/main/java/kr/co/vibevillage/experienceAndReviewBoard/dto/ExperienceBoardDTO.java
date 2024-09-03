package kr.co.vibevillage.experienceAndReviewBoard.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExperienceBoardDTO {
    private Long rId;          // 게시글 ID
    private Long uNo;          // 작성자 번호
    private String uNickname;
    private Long categoryId;   // 카테고리 ID
    private String rTitle;     // 제목
    private String rContent;   // 내용
    private LocalDateTime rCreatedAt; // 생성일시
    private LocalDateTime rUpdatedAt; // 수정일시
    private int rViewCount = 0;    // 조회수
    private int rLikeCount = 0;    // 좋아요 수
    private int commentCount = 0;
    private List<UploadDTO> uploadDTOs;
    private String categoryName;
    private String imageUrl;
    private String userId;


    private List<String> imageUrls;  // 다중 파일 업로드를 위한

}

