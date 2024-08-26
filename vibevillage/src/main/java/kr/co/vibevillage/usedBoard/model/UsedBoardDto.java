package kr.co.vibevillage.usedBoard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsedBoardDto {
 // 유저 번호
 private int userNo;

 // 중고 거래 게시판

 // 자동입력
 private int usedBoardId;
 private String usedBoardCreateAt;
 private String usedBoardUpdate;
 private double gpsLatitude;
 private double gpsLongitude;
 private String usedBoardStatus;
 private int usedBoardViews;
 private String userNickName;

 // 직접 입력
 private String usedBoardTitle;
 private String usedBoardContent;
 private String usedBoardLocation;

 private String province;
 private String city;

 private String usedBoardComment;

 // 업로드 관련;
 private List<UsedBoardImageDto> images;

 // 물품
 private String usedBoardProductName;
 private int usedBoardProductPrice;

 // 카테고리
 private int categoryId;
 private String categoryName;

 // 유저
 private int userWriteCount, userCommentCount;


}

