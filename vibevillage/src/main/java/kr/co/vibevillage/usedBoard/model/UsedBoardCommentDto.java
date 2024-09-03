package kr.co.vibevillage.usedBoard.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsedBoardCommentDto {
    private int usedCommentId;
    private int uNo;
    private String userNickName;
    private String usedCommentContent;
    private String usedCommentCreateAt;
    private String usedCommentUpdateAt;
    private String usedCommentDeleteAt;
    private int usedBoardId;
    private int commentSize;
}
