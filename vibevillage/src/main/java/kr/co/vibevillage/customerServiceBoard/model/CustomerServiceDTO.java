package kr.co.vibevillage.customerServiceBoard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class CustomerServiceDTO {
    private int nbNo;
    private String nbTitle;
    private String nbContent;
    private String nbIndate;
    private String nbUpdate;
    private String nbDelete;
    private String nbDeleteYN;
    private String nbViews;

    private int qaNo;
    private String qaTitle;
    private String qaContent;
    private String qaIndate;
    private String qaUpdate;
    private String qaDelete;
    private String qaDeleteYN;

    private int ibNo;
    private String ibContent;
    private String ibIndate;
    private String ibUpdate;

    private int iaNo;
    private String iaContent;
    private String iaIndate;

    private int icNo;
    private String icName;

    private int uNo;

}
