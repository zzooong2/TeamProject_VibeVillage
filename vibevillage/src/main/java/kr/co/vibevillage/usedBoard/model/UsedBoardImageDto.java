package kr.co.vibevillage.usedBoard.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsedBoardImageDto{
    private int imageId;
    private int usedBoardId; // Foreign key to UsedBoard
    private String uploadOriginName;
    private String uploadUniqueName;
    private String uploadImagePath;
    private String PROJECTREACTOR ="/uploadUsedImages/";
    private String uploadImageType;
}
