package kr.co.vibevillage.experienceAndReviewBoard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadDTO {
    private Long ruNo;
    private Long rId;
    private String ruName;
    private String ruUniqueName;
    private String ruLocalPath;
    private String ruServerPath;
    private String ruFileType;
    private String filePath;
}
