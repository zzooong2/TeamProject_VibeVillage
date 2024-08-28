    package kr.co.vibevillage.usedBoard.mapper;

    import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
    import org.apache.ibatis.annotations.Mapper;
    import org.apache.ibatis.annotations.Param;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.List;

    @Mapper
    public interface UsedBoardImageMapper {

        // 이미지 등록
        public int usedBoardEnrollImageXML(@Param("image") UsedBoardImageDto image);
        // 디테일 이미지
        public List<UsedBoardImageDto> usedBoardGetImageListXML(@Param("id") int id);
        // 게시글 리스트 이미지
        public List<UsedBoardImageDto> usedBoardGetImageListOnceXML(@Param("id") int id);

        public int deleteImages(@Param("id")int id);
    }
