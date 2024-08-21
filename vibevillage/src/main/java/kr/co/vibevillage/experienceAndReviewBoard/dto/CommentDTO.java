package kr.co.vibevillage.experienceAndReviewBoard.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CommentDTO {
    private Long commentId;
    private Long rId;
    private Long uNo;

    private LocalDateTime cCreatedAt;
    private LocalDateTime cUpdatedAt;
    private LocalDateTime cDeletedAt;

    private String userNickname;
    private String content;
}


