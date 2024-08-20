package kr.co.vibevillage.experienceAndReviewBoard.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.vibevillage.experienceAndReviewBoard.dto.CommentDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.ExperienceBoardDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.LikeDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.UploadDTO;
import kr.co.vibevillage.experienceAndReviewBoard.service.impl.CommentServiceImpl;
import kr.co.vibevillage.experienceAndReviewBoard.service.impl.ExperienceBoardServiceImpl;
import kr.co.vibevillage.experienceAndReviewBoard.service.impl.LikeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/experienceBoard")
public class ExperienceBoardController {
    @Autowired
    private final ExperienceBoardServiceImpl experienceBoardService;
    private final CommentServiceImpl commentService;
    private final LikeServiceImpl likeServiceImpl;

    public ExperienceBoardController(ExperienceBoardServiceImpl experienceBoardService, CommentServiceImpl commentService, LikeServiceImpl likeServiceImpl) {
        this.experienceBoardService = experienceBoardService;
        this.commentService = commentService;
        this.likeServiceImpl = likeServiceImpl;
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


    // 게시글 작성
    @PostMapping("/write")
    public String createPost(@ModelAttribute ExperienceBoardDTO experienceBoardDTO,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        try {
            // 파일 업로드 처리
            if (file != null && !file.isEmpty()) {
                handleFileUpload(file, experienceBoardDTO);
            }

            // 게시글 작성
            experienceBoardService.createPost(experienceBoardDTO);
            redirectAttributes.addFlashAttribute("message", "글이 성공적으로 작성되었습니다.");
            return "redirect:/experienceBoard";

        } catch (IOException e) {
            // 파일 업로드 실패 시 처리
            redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
            return "redirect:/experienceBoard/new";

        } catch (Exception e) {
            // 게시글 작성 실패 시 처리
            redirectAttributes.addFlashAttribute("errorMessage", "게시글 작성 중 오류가 발생했습니다.");
            return "redirect:/experienceBoard/new";
        }
    }


    // 파일 업로드 처리 로직을 분리하여 가독성을 높임
    private void handleFileUpload(MultipartFile file, ExperienceBoardDTO experienceBoardDTO) throws IOException {
        if (!file.isEmpty()) {
            // 파일 업로드 경로 설정
            String uploadDirectory = "static/uploadReviewFile";
            String fileName = file.getOriginalFilename();
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
    @PostMapping("/post/{id}/comment")
    public String addComment(@PathVariable Long id, @ModelAttribute CommentDTO commentDTO) {
        commentDTO.setRId(id);
        commentService.addComment(commentDTO);
        return "redirect:/experienceBoard/post/" + id;
    }

    @PostMapping("/comment/delete/{commentId}")
    public String deleteComment(@PathVariable Long commentId, @RequestParam Long postId) {
        commentService.deleteComment(commentId);
        return "redirect:/experienceBoard/post/" + postId;
    }



    @PostMapping("/like/{id}")
    public String likePost(@PathVariable Long id, @SessionAttribute("loggedInUser") Long uNo, Model model) {
        // 사용자가 이미 좋아요를 눌렀는지 확인
        boolean isLiked = likeServiceImpl.hasLiked(id, uNo);

        if (isLiked) {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            likeServiceImpl.deleteLike(id, uNo);
        } else {
            // 좋아요를 누르지 않았다면 좋아요 추가
            likeServiceImpl.addLike(id, uNo);
        }

        // 좋아요 카운트 업데이트
        likeServiceImpl.updateLikeCount(id);

        // 게시글 상세 페이지로 리다이렉트
        return "redirect:/experienceBoard/post/" + id;
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
