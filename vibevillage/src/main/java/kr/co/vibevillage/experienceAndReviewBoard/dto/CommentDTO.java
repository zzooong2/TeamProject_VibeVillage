package kr.co.vibevillage.experienceAndReviewBoard.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    private Long cId;
    private Long rId; // 게시물 ID
    private Long uNo; // 회원 번호
    private String cContent; // 댓글 내용
    private Date cCreatedAt; // 생성 시간
    private Date cUpdatedAt; // 수정 시간
    private Boolean cDeleteAt; // 댓글 삭제 여부
}
