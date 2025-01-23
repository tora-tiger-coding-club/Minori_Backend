package backend.minori.api.review;

import backend.minori.api.review.dto.ReviewResponseDto;
import backend.minori.api.review.dto.ReviewUpdateRequestDto;
import backend.minori.api.review.service.ReviewService;
import backend.minori.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review/anime")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 애니메이션 전체 리뷰 목록 조회
    @GetMapping("/{anime_id}")
    public ResponseEntity<List<ReviewResponseDto>> getAnimeReviews(
            @PathVariable("anime_id") Long animeId) {
        return ResponseEntity.ok(reviewService.getAnimeReviews(animeId));
    }

    // 애니메이션 리뷰 작성
    @PostMapping("/{anime_id}")
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable("anime_id") Long animeId,
            @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.createReview(animeId, review));
    }

    // 애니메이션 리뷰 수정
    @PatchMapping("/{anime_id}/{review_id}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @PathVariable("anime_id") Long animeId,
            @PathVariable("review_id") Long reviewId,
            @RequestBody ReviewUpdateRequestDto requestDto) {
        return ResponseEntity.ok(reviewService.updateReview(animeId, reviewId, requestDto));
    }

    // 애니메이션 리뷰 삭제
    @DeleteMapping("/{anime_id}/{review_id}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable("anime_id") Long animeId,
            @PathVariable("review_id") Long reviewId) {
        reviewService.deleteReview(animeId, reviewId);
        return ResponseEntity.ok().build();
    }

    // 특정 유저의 애니메이션 리뷰 조회
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<ReviewResponseDto>> getUserReviews(
            @PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(reviewService.getUserReviews(userId));
    }

    // 애니메이션 리뷰 공유
    @GetMapping("/{anime_id}/{review_id}/share")
    public ResponseEntity<ReviewResponseDto> shareReview(
            @PathVariable("anime_id") Long animeId,
            @PathVariable("review_id") Long reviewId) {
        return ResponseEntity.ok(reviewService.shareReview(animeId, reviewId));
    }

    // 애니메이션 리뷰 좋아요
    @PostMapping("/{anime_id}/{review_id}/like")
    public ResponseEntity<Void> likeReview(
            @PathVariable("anime_id") Long animeId,
            @PathVariable("review_id") Long reviewId) {
        reviewService.likeReview(animeId, reviewId);
        return ResponseEntity.ok().build();
    }
}
