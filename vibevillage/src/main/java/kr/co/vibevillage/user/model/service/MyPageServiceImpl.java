package kr.co.vibevillage.user.model.service;

import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    private final MyPageMapper myPageMapper;
    private final PasswordEncoder passwordEncoder;

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
            // 기존 파일 삭제
            if (userDTO.getUploadFileUniqueName() != null) {
                deleteExistingFile(userDTO.getUploadFileUniqueName(), uploadPath);
            }

            // 새로운 파일 업로드
            uploadFile.transferTo(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return myPageMapper.uploadProfile(userDTO);
    }

    // 기존 파일 삭제
    private void deleteExistingFile(String fileName, String uploadPath) {
        File file = new File(uploadPath + fileName);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                throw new RuntimeException("기존 파일 삭제에 실패했습니다.");
            }
        }
    }

    // 프로필 조회
    @Override
    public UserDTO getProfileInfo(int loginUserNo) {
        return myPageMapper.getProfileInfo(loginUserNo);
    }

    // 프로필 삭제
    @Override
    public int deleteProfile(int loginUserNo) {
        UserDTO profile = myPageMapper.getProfileInfo(loginUserNo);
        if (profile != null) {
            deleteExistingFile(profile.getUploadFileUniqueName(), "/Users/keem/finalProject/vibevillage/src/main/resources/static/images/userProfile/");
        }
        return myPageMapper.deleteProfile(loginUserNo);
    }

    // 프로필 변경
    @Override
    public int updateProfile(UserDTO userDTO) {
        System.out.println(userDTO.toString());
        return myPageMapper.updateProfile(userDTO);
    }

    @Override
    public int updatePassword(UserDTO userDTO) {
        // 유저가 입력한 비밀번호를 passwordEncoder객체를 이용하여 암호화를 진행한다.
        String bcryptPassword = passwordEncoder.encode(userDTO.getUserPassword());
        // 암호화된 비밀번호를 userDTO 객체에 초기화
        userDTO.setUserPassword(bcryptPassword);

        return myPageMapper.updatePassword(userDTO);
    }

    // 비밀번호 변경 (암호화 없는 임시 비밀번호)
    @Override
    public int updatePasswordNoneProtected(UserDTO userDTO) {
        return myPageMapper.updatePassword(userDTO);
    }
}
