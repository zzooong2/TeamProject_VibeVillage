package kr.co.vibevillage.usedBoard.Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import kr.co.vibevillage.usedBoard.mapper.UsedBoardMapper;
import kr.co.vibevillage.usedBoard.model.UsedBoardDto;
import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import kr.co.vibevillage.usedBoard.service.UsedBoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class UsedBoardServiceImplTest {

    @InjectMocks
    private UsedBoardServiceImpl usedBoardService;

    @Mock
    private UsedBoardMapper usedBoardMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @DisplayName("리스트 조회 테스트")
    @Test
    void testGetUsedBoardList() {
        // Given
        List<UsedBoardDto> mockList = new ArrayList<>();
        UsedBoardDto dto = new UsedBoardDto();
        dto.setUsedBoardId(1);
        dto.setUsedBoardTitle("Test Title");
        int testCategory =  0;
        String testProvince = " ";
        String testCity = " ";
        mockList.add(dto);
        mockList.add(dto);

        when(usedBoardMapper.usedBoardListXML(testCategory,testProvince,testCity)).thenReturn(mockList);

        // When
        List<UsedBoardDto> result = usedBoardService.getUsedBoardList(testCategory,testProvince,testCity);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Title", result.get(0).getUsedBoardTitle());
        verify(usedBoardMapper, times(1)).usedBoardListXML(testCategory,testProvince,testCity);
    }
    @DisplayName("디테일 조회 테스트")
    @Test
    void testGetUsedBoardDetail() {
        // Given
        int boardId = 1;
        UsedBoardDto dto = new UsedBoardDto();
        dto.setUsedBoardId(boardId);
        dto.setUsedBoardTitle("Test Detail");
        when(usedBoardMapper.usedBoardDetailXML(boardId)).thenReturn(dto);

        // When
        UsedBoardDto result = usedBoardService.getUsedBoardDetail(boardId);

        // Then
        assertNotNull(result);
        assertEquals("Test Detail", result.getUsedBoardTitle());
        verify(usedBoardMapper, times(1)).usedBoardDetailXML(boardId);
    }
    @DisplayName("등록 테스트")
    @Test
    void testEnrollUsedBoard() {
        // Given
        int expectedValue = 1;
        UsedBoardDto dto = new UsedBoardDto();
        dto.setUserNo(1);
        dto.setUsedBoardTitle("New Board");
        List<UsedBoardImageDto> images = new ArrayList<>();
        dto.setImages(images);
        when(usedBoardMapper.usedBoardEnrollXML(dto)).thenReturn(expectedValue);

        // When
        usedBoardService.enrollUsedBoard(dto);

        // Then
        verify(usedBoardMapper, times(1)).usedBoardEnrollXML(dto);
    }

    // 추가적인 테스트 케이스 작성 가능
}
