package kr.co.vibevillage.experienceAndReviewBoard.service.impl;

import kr.co.vibevillage.experienceAndReviewBoard.dto.ExperienceBoardDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.UploadDTO;
import kr.co.vibevillage.experienceAndReviewBoard.listmapper.ExperienceBoardMapper;
import kr.co.vibevillage.experienceAndReviewBoard.service.ExperienceBoardService;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ExperienceBoardServiceImpl implements ExperienceBoardService {

    private final ExperienceBoardMapper experienceBoardMapper;
    private final LoginServiceImpl loginServiceImpl;


    @Autowired
    public ExperienceBoardServiceImpl(ExperienceBoardMapper experienceBoardMapper, LoginServiceImpl loginServiceImpl) {
        this.experienceBoardMapper = experienceBoardMapper;
        this.loginServiceImpl = loginServiceImpl;
    }

    @Override
    public List<ExperienceBoardDTO> getAllPosts(int page, int size) {
        int offset = (page - 1) * size;
        return experienceBoardMapper.findAllWithPagination(size, offset);
    }

    @Override
    public void createPost(ExperienceBoardDTO experienceBoardDto, int userNo, MultipartFile[] files) throws IOException {
        experienceBoardDto.setUNo((long) userNo);  // 사용자 번호 설정

        // 게시글 생성
        experienceBoardMapper.createPost(experienceBoardDto);
        experienceBoardMapper.addWriteCount(userNo);

        // 생성된 게시글의 ID 가져오기 (MyBatis의 selectKey 사용)
        Long rId = experienceBoardDto.getRId();

        // 파일 업로드 처리
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    handleFileUpload(file, rId);  // 파일 개별 처리
                }
            }
        }
    }

    @Override
    public void deleteUploadsByIds(Long[] deleteImageIds) {
        for (Long id : deleteImageIds) {

            experienceBoardMapper.deleteUploadById(id);
        }
    }

    @Override
    public void updatePost(Long rId, ExperienceBoardDTO experienceBoardDTO, MultipartFile[] files) throws IOException {
        // 기존 게시글 업데이트
        experienceBoardMapper.updatePost(rId, experienceBoardDTO.getRTitle(), experienceBoardDTO.getRContent());

        // 파일 업로드 처리
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    handleFileUpload(file, rId);
                }
            }
        }
    }

    private void handleFileUpload(MultipartFile file, Long rId) throws IOException {
        String uploadDirectory = "C:\\dev\\final\\TeamProject_VibeVillage\\vibevillage\\src\\main\\resources\\static\\uploadReviewFile";
        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉터리 생성
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File destinationFile = new File(uploadDirectory + "/" + fileName);

        file.transferTo(destinationFile);

        UploadDTO uploadDTO = new UploadDTO();
        uploadDTO.setRId(rId);
        uploadDTO.setRuName(fileName);
        uploadDTO.setRuUniqueName(file.getOriginalFilename());
        uploadDTO.setRuLocalPath(destinationFile.getAbsolutePath());
        uploadDTO.setRuServerPath("/static/uploadReviewFile/" + fileName);
        uploadDTO.setRuFileType(file.getContentType());

        // 파일 정보 DB 저장
        experienceBoardMapper.insertUpload(uploadDTO);
    }

    @Override
    public void deletePost(Long rId) {

        UserDTO loginUser = loginServiceImpl.getLoginUserInfo();
        int loginUserNo = loginUser.getUserNo();
        experienceBoardMapper.subWriteCount(loginUserNo);

        // 게시글 삭제
        experienceBoardMapper.deletePost(rId);
        // 게시글에 관련된 파일도 삭제 (필요 시)
        experienceBoardMapper.deleteUploadsByPostId(rId);
    }

    @Override
    public void deletePostAndRelatedData(Long postId) {
        // 자식 테이블의 관련 데이터를 먼저 삭제
        experienceBoardMapper.deleteCommentsByPostId(postId);
        experienceBoardMapper.deleteUploadsByPostId(postId);

        // 그 후 부모 테이블의 레코드를 삭제
        experienceBoardMapper.deletePost(postId);
    }

    @Override
    public List<ExperienceBoardDTO> searchPosts(String keyword) {
        return experienceBoardMapper.searchPosts(keyword);
    }

    @Override
    public ExperienceBoardDTO getPostById(Long rId) {
        // 조회수 증가
        experienceBoardMapper.incrementViewCount(rId);

        // 게시글 상세정보 가져오기
        ExperienceBoardDTO experienceBoardDTO = experienceBoardMapper.findById(rId);

        if (experienceBoardDTO == null) {
            return null;
        }

        // 업로드된 파일 정보 가져오기
        List<UploadDTO> uploadDTOs = experienceBoardMapper.findUploadsByPostId(rId);
        experienceBoardDTO.setUploadDTOs(uploadDTOs);

        return experienceBoardDTO;
    }


    @Override
    public List<ExperienceBoardDTO> getPostsByCategory(Long categoryId) {
        List<ExperienceBoardDTO> posts = experienceBoardMapper.findPostsByCategory(categoryId);

        if (posts == null || posts.isEmpty()) {
            return List.of(); // 게시글이 없을 경우 빈 리스트 반환
        }

        for (ExperienceBoardDTO post : posts) {
            if (post != null) {
                String categoryName = experienceBoardMapper.getCategoryNameById(post.getCategoryId());
                post.setCategoryName(categoryName);
            } else {
                // post가 null일 경우 적절한 처리
                System.err.println("Null post encountered!");
            }
        }

        return posts;
    }

    @Override
    public List<ExperienceBoardDTO> getAllPosts() {
        List<ExperienceBoardDTO> posts = experienceBoardMapper.findAllPosts();  // 모든 게시글 조회

        // 각 게시글에 카테고리 이름을 설정
        for (ExperienceBoardDTO post : posts) {
            String categoryName = experienceBoardMapper.getCategoryNameById(post.getCategoryId());
            post.setCategoryName(categoryName);
        }

        return posts;
    }

    @Override
    public String getCategoryNameById(Long categoryId) {
        return experienceBoardMapper.getCategoryNameById(categoryId);
    }

    @Override
    public int countTotalRecommendedPosts() {
        return 0;
    }

    @Override
    public int getTotalPostCount() {
        return experienceBoardMapper.countTotalPosts();
    }


    @Override
    public List<ExperienceBoardDTO> getTopLikedPosts(int page, int size) {
        int offset = (page - 1) * size;
        return experienceBoardMapper.findTopLikedPosts(offset, size);
    }

}
