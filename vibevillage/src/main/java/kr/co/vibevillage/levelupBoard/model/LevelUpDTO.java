package kr.co.vibevillage.levelupBoard.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LevelUpDTO {
    private int lbNo;
    private String lbTitle;
    private String lbContent;
    private String lbIndate;
    private String lbUpdate;
    private String lbDelete;
    private String lbDelete_YN;

    private int lrNo;
    private String lrContent;
    private String lrStatus;
    private String lrIndate;

    private int uNo;
    private String userNickName;
}
