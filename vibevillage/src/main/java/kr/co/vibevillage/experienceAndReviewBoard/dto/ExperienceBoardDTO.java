package kr.co.vibevillage.experienceAndReviewBoard.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExperienceBoardDTO {
    private Long rId;          // 게시글 ID
    private Long uNo;          // 작성자 번호
    private Long categoryId;   // 카테고리 ID
    private String rTitle;     // 제목
    private String rContent;   // 내용
    private LocalDateTime rCreatedAt; // 생성일시
    private LocalDateTime rUpdatedAt; // 수정일시
    private int rViewCount;    // 조회수
    private int rLikeCount;    // 좋아요 수

}


    //    private Long id;
//    private String title;
//    private String content;
//    private String writer;
//    private LocalDateTime createdDate;
//    private int views;
//    private int likes;
//    private String author;

