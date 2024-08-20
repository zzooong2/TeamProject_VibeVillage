package kr.co.vibevillage.experienceAndReviewBoard.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CommentDTO {
    private Long commentId;
    private String uNickname;
    private Long rId;
    private Long uNo;
    private String cContent;
    private LocalDateTime cCreatedAt;
    private LocalDateTime cUpdatedAt;
    private LocalDateTime cDeletedAt;
}


