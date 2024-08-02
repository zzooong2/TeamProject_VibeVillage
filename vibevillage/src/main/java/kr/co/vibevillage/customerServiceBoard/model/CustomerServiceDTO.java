package kr.co.vibevillage.customerServiceBoard.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerServiceDTO {
    private int csbNo;
    private String csbTitle;
    private String csbContent;
    private String csbIndate;
    private String csbUpdate;
    private String csbDelete;
    private String csbDeleteYN;
    private String csbViews;
    private int userNo;
}
