package kr.co.vibevillage.experienceAndReviewBoard.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExperienceBoard {
    private Long rId;
    private Long uNo;
    private Long categoryId;
    private String rTitle;
    private String rContent;
    private LocalDateTime rCreatedAt;
    private LocalDateTime rUpdatedAt;
    private int rViewCount = 0;
    private int rLikeCount = 0;
    private int commentCount = 0;
}
