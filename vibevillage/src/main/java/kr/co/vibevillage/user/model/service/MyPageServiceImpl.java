package kr.co.vibevillage.user.model.service;

import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    private final MyPageMapper myPageMapper;

    // 프로필 업로드
    @Override
    public int uploadProfile(UserDTO userDTO, MultipartFile uploadFile) {
        // 유저가 업로드한 파일을 저장할 위치
        String uploadPath = "/Users/keem/finalProject/vibevillage/src/main/resources/static/images/userProfile/";
        // 위치 + 유니크 파일명
        String filePathName = uploadPath + userDTO.getUploadFileUniqueName();
        // Path 타입으로 형변환
        Path filePath = Paths.get(filePathName);

        try {
            uploadFile.transferTo(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return myPageMapper.uploadProfile(userDTO);
    }

    // 프로필 조회
    public UserDTO getProfileInfo(int loginUserNo) {
        return myPageMapper.getProfileInfo(loginUserNo);
    }
}
