package kr.co.vibevillage.experienceAndReviewBoard.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.vibevillage.experienceAndReviewBoard.dto.CommentDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.ExperienceBoardDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.LikeDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.UploadDTO;
import kr.co.vibevillage.experienceAndReviewBoard.service.impl.CommentServiceImpl;
import kr.co.vibevillage.experienceAndReviewBoard.service.impl.ExperienceBoardServiceImpl;
import kr.co.vibevillage.experienceAndReviewBoard.service.impl.LikeServiceImpl;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/experienceBoard")
public class ExperienceBoardController {

    @Autowired
    private final ExperienceBoardServiceImpl experienceBoardService;
    private final CommentServiceImpl commentService;
    private final LikeServiceImpl likeServiceImpl;
    private final LoginServiceImpl loginServiceImpl;

    public ExperienceBoardController(ExperienceBoardServiceImpl experienceBoardService, CommentServiceImpl commentService, LikeServiceImpl likeServiceImpl, LoginServiceImpl loginServiceImpl) {
        this.experienceBoardService = experienceBoardService;
        this.commentService = commentService;
        this.likeServiceImpl = likeServiceImpl;
        this.loginServiceImpl = loginServiceImpl;
    }

    @GetMapping
    public String getAllPosts(@RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size,
                              Model model) {
        int totalPosts = experienceBoardService.getTotalPostCount();
        int totalPages = (int) Math.ceil((double) totalPosts / size);

        List<ExperienceBoardDTO> posts;

        if (keyword != null && !keyword.isEmpty()) {
            List<ExperienceBoardDTO> searchResults = experienceBoardService.searchPosts(keyword);

            for (ExperienceBoardDTO post : searchResults) {
                int commentCount = commentService.getCommentCountByPostId(post.getRId());
                int likeCount = likeServiceImpl.countLikes(post.getRId());
                String categoryName = experienceBoardService.getCategoryNameById(post.getCategoryId());

                post.setCommentCount(commentCount);
                post.setRLikeCount(likeCount);
                post.setCategoryName(categoryName);
            }

            model.addAttribute("posts", searchResults);
        } else {
            posts = experienceBoardService.getAllPosts(page, size);

            for (ExperienceBoardDTO post : posts) {
                int commentCount = commentService.getCommentCountByPostId(post.getRId());
                int likeCount = likeServiceImpl.countLikes(post.getRId());
                String categoryName = experienceBoardService.getCategoryNameById(post.getCategoryId());

                post.setCommentCount(commentCount);
                post.setRLikeCount(likeCount);
                post.setCategoryName(categoryName);
            }

            model.addAttribute("posts", posts);
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", totalPages);

        return "experienceAndReviewBoard/experienceAndReviewBoard";
    }





    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new ExperienceBoardDTO());
        return "experienceAndReviewBoard/writeReview";
    }


    @PostMapping("/write")
    public String createPost(@ModelAttribute ExperienceBoardDTO experienceBoardDTO,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             RedirectAttributes redirectAttributes) {


        UserDTO loginUserInfo = loginServiceImpl.getLoginUserInfo();
        int userNo = loginUserInfo.getUserNo();
        experienceBoardDTO.setUNo((long) userNo);  // 사용자 번호 설정


        try {

            // 파일 업로드 및 게시글 작성 처리
            if (file != null && !file.isEmpty()) {
                handleFileUpload(file, experienceBoardDTO);
            }
            experienceBoardService.createPost(experienceBoardDTO, userNo);
            redirectAttributes.addFlashAttribute("message", "글이 성공적으로 작성되었습니다.");
            return "redirect:/experienceBoard";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
            return "redirect:/experienceBoard/new";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "게시글 작성 중 오류가 발생했습니다.");
            return "redirect:/experienceBoard/new";
        }
    }

    // 파일 업로드 처리 로직
    private void handleFileUpload(MultipartFile file, ExperienceBoardDTO experienceBoardDTO) throws IOException {
        if (!file.isEmpty()) {
            // 파일 업로드 경로 설정
            String uploadDirectory = "C:\\dev\\final\\TeamProject_VibeVillage\\vibevillage\\src\\main\\resources\\static\\uploadReviewFile";
            File directory = new File(uploadDirectory);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉터리 생성
            }

            // 파일 이름 충돌 방지
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File destinationFile = new File(uploadDirectory + "/" + fileName);

            // 파일 전송
            file.transferTo(destinationFile);

            // 파일 정보 설정
            UploadDTO uploadDTO = new UploadDTO();
            uploadDTO.setRuName(fileName);
            uploadDTO.setFilePath(destinationFile.getAbsolutePath());
            experienceBoardDTO.setUploadDTO(uploadDTO);
        }
    }



    // 카테고리

    @GetMapping("/filter")
    public String filterByCategory(@RequestParam("categoryName") String categoryName, Model model, HttpSession session) {
        List<ExperienceBoardDTO> filteredPosts;

        try {
            if (categoryName == null || categoryName.isEmpty()) {
                // categoryName이 빈 문자열이거나 null인 경우 전체 게시글을 조회
                filteredPosts = experienceBoardService.getAllPosts();
            } else {
                // 카테고리에 따른 게시글을 조회
                Long categoryId = Long.parseLong(categoryName);
                filteredPosts = experienceBoardService.getPostsByCategory(categoryId);
            }
        } catch (NumberFormatException e) {
            // categoryName이 유효한 숫자가 아닌 경우 전체 게시글을 조회
            filteredPosts = experienceBoardService.getAllPosts();
        }
        // 조회된 게시글을 모델에 추가
        model.addAttribute("posts", filteredPosts);

        return "experienceAndReviewBoard/experienceAndReviewBoard";
    }



    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        ExperienceBoardDTO post = experienceBoardService.getAllPosts(1, 10).stream()
                .filter(p -> p.getRId().equals(id))
                .map(p -> {
                    ExperienceBoardDTO dto = new ExperienceBoardDTO();
                    dto.setRId(p.getRId());
                    dto.setRTitle(p.getRTitle());
                    dto.setRContent(p.getRContent());
                    dto.setUNo(p.getUNo());
                    dto.setCategoryName(p.getCategoryName());
                    return dto;
                })
                .findFirst().orElse(null);
        model.addAttribute("post", post);
        return "experienceBoard/form";
    }
    
    //조회, 수정, 삭제
    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute ExperienceBoardDTO post) {
        experienceBoardService.updatePost(id, post);
        return "redirect:/experienceBoard";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        experienceBoardService.deletePost(id);
        return "redirect:/experienceBoard";
    }

    @GetMapping("/post/{id}")
    public String getPostDetail(@PathVariable Long id, Model model, HttpSession session) {
        ExperienceBoardDTO post = experienceBoardService.getPostById(id);
        if (post == null) {
            return "redirect:/experienceBoard"; // 또는 적절한 예외 처리
        }
        model.addAttribute("post", post);


        Long uNo = (Long) session.getAttribute("uNo");
        boolean hasLiked = false;
        if (uNo != null) {
            LikeDTO likeDto = new LikeDTO();
            likeDto.setRId(id);
            likeDto.setUNo(uNo);
            hasLiked = likeServiceImpl.hasLiked(likeDto);
        }

        List<ExperienceBoardDTO> otherPosts = experienceBoardService.getAllPosts(1, 10);
        List<CommentDTO> comments = commentService.getCommentsByPostId(id);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("otherPosts", otherPosts);
        model.addAttribute("hasLiked", hasLiked);
        model.addAttribute("likeCount", likeServiceImpl.countLikes(id));
        return "experienceAndReviewBoard/postDetail";
    }

    // 댓글
    @PostMapping("/post/{No}/comment")
    public String addComment(@PathVariable("No") Long rId,
                             @ModelAttribute CommentDTO commentDTO, Model model) {

        // 로그인한 유저의 정보를 가져와서 user 객체에 초기화 시킨다
        UserDTO user = loginServiceImpl.getLoginUserInfo();
        // user 객체에 있는 nickname 정보를 userNickname 변수에 초기화 시킨다
        String userNickname = user.getUserNickName();

        // CommentDTO에 선언되어있는 변수에 값을 초기화 시킨다
        commentDTO.setUserNickname(userNickname);
        commentDTO.setRId(rId);

        // 서비스 객체를 이용해서 addComment 메서드를 호출한다.
        commentService.addComment(commentDTO);

        // 댓글 리스트를 다시 불러와서 모델에 추가한다.
        List<CommentDTO> comments = commentService.getCommentsByPostId(rId);
        model.addAttribute("comments", comments);

        return "redirect:/experienceBoard/post/" + rId;
    }

    @PostMapping("/comment/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId, @RequestParam("postId") Long postId) {

        // 현재 로그인한 사용자 정보 가져오기
        UserDTO currentUser = loginServiceImpl.getLoginUserInfo();
        String currentUserNickname = currentUser.getUserNickName();

        // 삭제하려는 댓글 정보 가져오기
        CommentDTO comment = commentService.getCommentById(commentId);
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);

        // 댓글 작성자와 로그인한 사용자가 같은지 확인
        if (!comment.getUserNickname().equals(currentUserNickname)) {
            // 만약 다르면, 접근 금지 또는 예외 처리
            return "redirect:/experienceBoard/post/" + postId + "?error=not-authorized";
        }

        // 댓글 삭제 처리
        commentService.deleteComment(commentId);
        return "redirect:/experienceBoard/post/" + postId;
    }




    @PostMapping("/like/{No}")
    public String likePost(@PathVariable("No") Long rId,
                           Model model, RedirectAttributes redirectAttributes) {

        // 로그인한 회원의 계정 정보를 JWT에서 추출
        String userId = loginServiceImpl.getLoginUserId();

        // 로그인한 회원의 모든 정보를 가져온다
        UserDTO user = loginServiceImpl.getLoginUserInfo();
        // 만약에 회원 번호가 필요하다
        long userNo = user.getUserNo();

        if (userNo == 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 토큰입니다. 로그인이 필요합니다.");
            return "redirect:/login"; // 로그인 페이지로 리디렉트
        }

        Long effectiveUserId = userNo;

        // 사용자가 이미 좋아요를 눌렀는지 확인
        boolean isLiked = likeServiceImpl.hasLiked(rId, effectiveUserId);

        if (isLiked) {
            likeServiceImpl.deleteLike(rId, effectiveUserId);
        } else {
            likeServiceImpl.addLike(rId, effectiveUserId);
        }

        likeServiceImpl.updateLikeCount(rId);

        return "redirect:/experienceBoard/post/" + rId;
    }

    // 검색
    @GetMapping("/search")
    public String searchPosts(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        // 검색어 유효성 검사
        if (keyword == null || keyword.trim().isEmpty()) {
            model.addAttribute("error", "검색어를 입력해 주세요.");
            return "experienceAndReviewBoard/experienceAndReviewBoard";
        }

        // SQL 인젝션 방지: MyBatis의 파라미터 바인딩을 통해 처리
        List<ExperienceBoardDTO> searchResults = experienceBoardService.searchPosts(keyword);

        // 검색 결과 없는 경우 처리
        if (searchResults.isEmpty()) {
            model.addAttribute("message", "검색 결과가 없습니다.");
        }

        // 검색 결과와 검색어를 모델에 추가
        model.addAttribute("posts", searchResults);
        model.addAttribute("keyword", keyword);

        return "experienceAndReviewBoard/experienceAndReviewBoard";
    }

    // 추천글
// 기존의 getRecommendedPosts 메서드를 수정
    @GetMapping("/recommended")
    public String getTopLikedPosts(@RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                   Model model) {
        if (page == null) {
            page = 1; // 기본값 설정
        }
        int offset = (page - 1) * size;

        List<ExperienceBoardDTO> recommendedPosts = experienceBoardService.getTopLikedPosts(offset, size);

        int totalPostsCount = experienceBoardService.countTotalRecommendedPosts();
        int totalPages = (int) Math.ceil((double) totalPostsCount / size);

        model.addAttribute("posts", recommendedPosts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "experienceAndReviewBoard/experienceAndReviewBoard";
    }






}
