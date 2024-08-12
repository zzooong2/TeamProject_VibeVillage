package kr.co.vibevillage.experienceAndReviewBoard.dto;

import lombok.Data;

@Data
public class LikeDTO {
    private Long rId;
    private Long uNo;

    public LikeDTO(Long id, Long uNo) {
    }

    public LikeDTO() {

    }
}
