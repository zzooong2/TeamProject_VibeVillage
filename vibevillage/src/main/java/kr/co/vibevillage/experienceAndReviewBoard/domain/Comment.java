package kr.co.vibevillage.experienceAndReviewBoard.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Long commentId;
    private Long rId;
    private Long uNo;
    private String cContent;
    private LocalDateTime cCreatedAt;
    private LocalDateTime cUpdatedAt;
    private LocalDateTime cDeletedAt;
}
