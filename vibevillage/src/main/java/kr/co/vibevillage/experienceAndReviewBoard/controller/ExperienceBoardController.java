package kr.co.vibevillage.experienceAndReviewBoard.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.vibevillage.experienceAndReviewBoard.domain.Comment;
import kr.co.vibevillage.experienceAndReviewBoard.domain.ExperienceBoard;
import kr.co.vibevillage.experienceAndReviewBoard.dto.CommentDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.ExperienceBoardDTO;
import kr.co.vibevillage.experienceAndReviewBoard.dto.LikeDTO;
import kr.co.vibevillage.experienceAndReviewBoard.service.CommentService;
import kr.co.vibevillage.experienceAndReviewBoard.service.ExperienceBoardService;
import kr.co.vibevillage.experienceAndReviewBoard.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/experienceBoard")
public class ExperienceBoardController {
    @Autowired
    private final ExperienceBoardService experienceBoardService;
    private final CommentService commentService;
    private final LikeService likeService;

    public ExperienceBoardController(ExperienceBoardService experienceBoardService, LikeService likeService, CommentService commentService) {
        this.experienceBoardService = experienceBoardService;
        this.commentService = commentService;
        this.likeService = likeService;
    }

    @GetMapping
    public String getAllPosts(@RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size,
                              Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            // 검색어가 있을 경우 검색 결과를 가져옴
            List<ExperienceBoardDTO> searchResults = experienceBoardService.searchPosts(keyword);

            // 각 게시글에 대해 댓글 수와 좋아요 수를 가져옴
            for (ExperienceBoardDTO post : searchResults) {
                int commentCount = commentService.getCommentCountByPostId(post.getRId());
                int likeCount = likeService.countLikes(post.getRId());
                post.setCommentCount(commentCount);
                post.setRLikeCount(likeCount);
            }

            model.addAttribute("posts", searchResults);
        } else if (experienceBoardService.getAllPosts(page, size) != null) {
            // 전체 게시글을 페이지네이션을 적용하여 가져옴
            List<ExperienceBoard> posts = experienceBoardService.getAllPosts(page, size);

            // 각 게시글에 대해 댓글 수와 좋아요 수를 가져옴
            for (ExperienceBoard post : posts) {
                int commentCount = commentService.getCommentCountByPostId(post.getRId());
                int likeCount = likeService.countLikes(post.getRId());
                post.setCommentCount(commentCount);
                post.setRLikeCount(likeCount);
            }

            model.addAttribute("posts", posts);
        }

        return "experienceAndReviewBoard/experienceAndReviewBoard";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new ExperienceBoardDTO());
        return "experienceAndReviewBoard/writeReview";
    }

    @PostMapping("/write")
    public String createPost(@ModelAttribute ExperienceBoardDTO experienceBoardDTO, RedirectAttributes redirectAttributes) {
        experienceBoardService.createPost(experienceBoardDTO);
        redirectAttributes.addFlashAttribute("message", "글이 성공적으로 작성되었습니다.");
        return "redirect:/experienceBoard";
    }

//    @PostMapping
//    public String createPost(@ModelAttribute ExperienceBoardDTO post) {
//        experienceBoardService.createPost(post);
//        return "redirect:/experienceBoard";
//    }
    // 카테고리

    @GetMapping("/filter")
    public String filterByCategory(@RequestParam("categoryId") Long categoryId, Model model, HttpSession session) {
        // 카테고리에 따른 게시글을 조회
        List<ExperienceBoardDTO> filteredPosts = experienceBoardService.getPostsByCategory(categoryId);

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
                    dto.setCategoryId(p.getCategoryId());
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
            hasLiked = likeService.hasLiked(likeDto);
        }

        //List<ExperienceBoardDTO> otherPosts = experienceBoardService.getOtherPosts(id);
        List<ExperienceBoard> otherPosts = experienceBoardService.getAllPosts(1, 10);
        List<Comment> comments = commentService.getCommentsByPostId(id);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("otherPosts", otherPosts);
        model.addAttribute("hasLiked", hasLiked);
        model.addAttribute("likeCount", likeService.countLikes(id));
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

//    @GetMapping("/like/{id}")
//    public String likePost(@PathVariable Long id, Long uNo) {
//        likeService.likePost(new LikeDTO(id, uNo));
//        return "redirect:/experienceBoard";
//    }

    // 좋아요 토글
//    @PostMapping("/like/{id}")
//    public String toggleLike(@PathVariable Long id, HttpSession session) {
//        Long uNo = (Long) session.getAttribute("uNo");
//        if (uNo == null) {
//            return "redirect:/login"; // 로그인하지 않은 사용자는 로그인 페이지로 리다이렉트
//        }
//
//        LikeDTO likeDto = new LikeDTO();
//        likeDto.setRId(id);
//        likeDto.setUNo(uNo);
//        likeService.toggleLike(likeDto);
//
//        return "redirect:/experienceBoard/post/" + id;
//    }

    @PostMapping("/like/{id}")
    public String likePost(@PathVariable Long id, @SessionAttribute("loggedInUser") Long uNo, Model model) {
        // 사용자가 이미 좋아요를 눌렀는지 확인
        boolean isLiked = likeService.hasLiked(id, uNo);

        if (isLiked) {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            likeService.deleteLike(id, uNo);
        } else {
            // 좋아요를 누르지 않았다면 좋아요 추가
            likeService.addLike(id, uNo);
        }

        // 좋아요 카운트 업데이트
        likeService.updateLikeCount(id);

        // 게시글 상세 페이지로 리다이렉트
        return "redirect:/experienceBoard/post/" + id;
    }

    // 검색
    @GetMapping("/search")
    public String searchPosts(@RequestParam("keyword") String keyword, Model model) {
        List<ExperienceBoardDTO> searchResults = experienceBoardService.searchPosts(keyword);
        model.addAttribute("posts", searchResults);
        return "experienceAndReviewBoard/experienceAndReviewBoard";
    }

    // 추천글
    @GetMapping("/recommended")
    public String getRecommendedPosts(Model model) {
        List<ExperienceBoardDTO> recommendedPosts = experienceBoardService.getTopLikedPosts();
        model.addAttribute("posts", recommendedPosts);
        return "experienceAndReviewBoard/experienceAndReviewBoard";
    }

}
