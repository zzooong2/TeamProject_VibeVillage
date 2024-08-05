package kr.co.vibevillage.usedBoard.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsedBoardImageDto extends UsedBoardDto {
    private String uploadOriginName;
    private String uploadUniqueName;
    private String uploadImagePath;
    private String uploadImageType;
}
