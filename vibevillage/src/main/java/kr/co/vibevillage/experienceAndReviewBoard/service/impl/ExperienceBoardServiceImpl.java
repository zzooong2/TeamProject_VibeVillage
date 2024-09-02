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
    public void createPost(ExperienceBoardDTO experienceBoardDTO, int userNo) throws IOException {
        // 게시글 생성 로직
        experienceBoardDTO.setUNo((long) userNo);
        experienceBoardMapper.createPost(experienceBoardDTO);
        experienceBoardMapper.addWriteCount(userNo);

        // 이미지 URL들이 있는 경우 처리
        if (experienceBoardDTO.getImageUrls() != null && !experienceBoardDTO.getImageUrls().isEmpty()) {

            for (String imageUrl : experienceBoardDTO.getImageUrls()) {
                UploadDTO uploadDTO = new UploadDTO();
                uploadDTO.setRId(experienceBoardDTO.getRId());
                uploadDTO.setRuName(imageUrl);
                uploadDTO.setRuUniqueName(UUID.randomUUID().toString());
                uploadDTO.setRuLocalPath("C:\\dev\\workspace\\finalproject\\vibevillage\\src\\main\\resources\\static\\uploadReviewFile");
                uploadDTO.setRuServerPath(imageUrl);

                // 파일 타입 추출
                String fileType = extractFileTypeFromUrl(imageUrl);
                uploadDTO.setRuFileType(fileType != null ? fileType : "unknown");

                experienceBoardMapper.insertUpload(uploadDTO);
            }
        } else {
            System.out.println("이미지 URL이 없습니다. 게시글만 저장되었습니다.");
        }
    }


    private String extractFileTypeFromUrl(String url) {
        if (url != null) {
            if (url.endsWith(".jpg") || url.endsWith(".jpeg")) {
                return "image/jpeg";
            } else if (url.endsWith(".png")) {
                return "image/png";
            } else if (url.endsWith(".gif")) {
                return "image/gif";
            } else if (url.endsWith(".bmp")) {
                return "image/bmp";
            } else if (url.endsWith(".webp")) {
                return "image/webp";
            }
        }
        return null;
    }


    @Override
    public void uploadImage(MultipartFile[] files, Long rId) throws Exception {
        String uploadDirectory = "C:\\dev\\workspace\\finalproject\\vibevillage\\src\\main\\resources\\static\\uploadReviewFile";

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                File directory = new File(uploadDirectory);
                if (!directory.exists()) {
                    directory.mkdirs(); // 디렉터리 생성
                }

                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                File destinationFile = new File(uploadDirectory + "/" + fileName);

                file.transferTo(destinationFile);

                // 파일 정보를 데이터베이스에 저장하는 로직 추가
                UploadDTO uploadDTO = new UploadDTO();
                uploadDTO.setRId(rId); // R_ID 값을 설정
                uploadDTO.setRuName(file.getOriginalFilename());
                uploadDTO.setRuUniqueName(fileName);
                uploadDTO.setRuLocalPath(destinationFile.getAbsolutePath());
                uploadDTO.setRuServerPath("/uploadReviewFile/" + fileName);
                uploadDTO.setRuFileType(file.getContentType());

                try {
                    experienceBoardMapper.insertUpload(uploadDTO);
                } catch (Exception e) {
                    throw e;  // 예외를 다시 던져서 상위 계층에서 처리하도록 함
                }
            }
        }
    }

    private void handleFileUpload(MultipartFile file, Long rId) throws IOException {
        String uploadDirectory = "C:\\dev\\workspace\\finalproject\\vibevillage\\src\\main\\resources\\static\\uploadReviewFile";
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
        uploadDTO.setRuServerPath("/uploadReviewFile/" + fileName);
        uploadDTO.setRuFileType(file.getContentType());

        // 파일 정보 DB 저장
        experienceBoardMapper.insertUpload(uploadDTO);
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

        // 기존 이미지 URL이 있는 경우 처리
        if (experienceBoardDTO.getImageUrls() != null && !experienceBoardDTO.getImageUrls().isEmpty()) {
            for (String imageUrl : experienceBoardDTO.getImageUrls()) {
                UploadDTO uploadDTO = new UploadDTO();
                uploadDTO.setRId(rId);
                uploadDTO.setRuName(imageUrl);
                uploadDTO.setRuUniqueName(UUID.randomUUID().toString());
                uploadDTO.setRuLocalPath("C:\\dev\\workspace\\finalproject\\vibevillage\\src\\main\\resources\\static\\uploadReviewFile");
                uploadDTO.setRuServerPath(imageUrl);

                // 파일 타입 추출
                String fileType = extractFileTypeFromUrl(imageUrl);
                uploadDTO.setRuFileType(fileType != null ? fileType : "unknown");

                experienceBoardMapper.insertUpload(uploadDTO);
            }
        }

        // 새로운 파일 업로드 처리
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    handleFileUpload(file, rId);
                }
            }
        }
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

//    @Override
//    public boolean isCategoryValid(Long categoryId) {
//        // 데이터베이스에서 카테고리 ID의 존재 여부를 확인하는 로직
//        Integer count = experienceBoardMapper.countCategoryById(categoryId);
//        return count != null && count > 0;
//    }

}
