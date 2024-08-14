package kr.co.vibevillage.usedBoard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public class UsedPageInfoDto {
    private int listCount;
    private int currentPage;
    private int pageLimit;
    private int boardLimit;

    private int maxPage;
    private int startPage;
    private int endPage;

    private int row;
    private int offset;
    private int limit;
}
