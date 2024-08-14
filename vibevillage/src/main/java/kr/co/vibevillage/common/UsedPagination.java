package kr.co.vibevillage.common;

import kr.co.vibevillage.usedBoard.model.UsedPageInfoDto;
import org.springframework.stereotype.Component;

// 중고 거래 게시판 페이지네이션
@Component
public class UsedPagination {
    public UsedPageInfoDto getPageInfo(int listCount, int currentPage,
                                                   int pageLimit, int boardLimit) {
        int maxPage = (int)(Math.ceil(((double)listCount / boardLimit)));
        int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
        int endPage = startPage + pageLimit - 1;
        int row = listCount - (currentPage - 1) * boardLimit;
        int offset = (currentPage - 1) * boardLimit;
        int limit = offset + boardLimit;

        if (endPage > maxPage) {
            endPage = maxPage;
        }

        return new UsedPageInfoDto(listCount, currentPage, pageLimit, boardLimit,
                maxPage, startPage, endPage, row,
                offset, limit);
    }
}
