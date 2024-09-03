package kr.co.vibevillage.experienceAndReviewBoard.dto;

import lombok.Data;

@Data
public class LikeDTO {
    private Long rId;
    private Long uNo;
    private java.sql.Timestamp lCreatedAt;
    public LikeDTO() {

    }
}
