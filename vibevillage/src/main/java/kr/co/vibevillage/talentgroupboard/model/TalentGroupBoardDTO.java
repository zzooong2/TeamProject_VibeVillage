package kr.co.vibevillage.talentgroupboard.model;

import lombok.Data;

import java.util.Date;


@Data
public class TalentGroupBoardDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private Date indate;
    private int commentCount;
}
