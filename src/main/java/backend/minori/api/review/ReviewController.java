package backend.minori.api.review;

import backend.minori.api.review.dto.ReviewResponseDto;
import backend.minori.api.review.dto.ReviewUpdateRequestDto;
import backend.minori.api.review.service.ReviewService;
import backend.minori.common.auth.CustomOAuth2User;
import backend.minori.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review/anime")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{anime_id}/public")
    public ResponseEntity<List<ReviewResponseDto>> getPublicAnimeReviews(
            @PathVariable("anime_id") Long animeId) {
        return ResponseEntity.ok(reviewService.getPublicAnimeReviews(animeId));
    }

    @GetMapping("/{anime_id}")
    public ResponseEntity<List<ReviewResponseDto>> getAnimeReviews(
            @AuthenticationPrincipal CustomOAuth2User user,
            @PathVariable("anime_id") Long animeId) {
        return ResponseEntity.ok(reviewService.getAnimeReviews(animeId, user));
    }

    @PostMapping("/{anime_id}")
    public ResponseEntity<ReviewResponseDto> createReview(
            @AuthenticationPrincipal CustomOAuth2User customUser,
            @PathVariable("anime_id") Long animeId,
            @RequestBody Review reviewRequest) {
        return ResponseEntity.ok(reviewService.createReview(animeId, reviewRequest, customUser));
    }

    @PutMapping("/{anime_id}/{review_id}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @AuthenticationPrincipal CustomOAuth2User customUser,
            @PathVariable("anime_id") Long animeId,
            @PathVariable("review_id") Long reviewId,
            @RequestBody ReviewUpdateRequestDto requestDto) {
        return ResponseEntity.ok(reviewService.updateReview(customUser.getUserId(), animeId, reviewId, requestDto, customUser));
    }

    @DeleteMapping("/{anime_id}/{review_id}")
    public ResponseEntity<Void> deleteReview(
            @AuthenticationPrincipal CustomOAuth2User customUser,
            @PathVariable("anime_id") Long animeId,
            @PathVariable("review_id") Long reviewId) {
        reviewService.deleteReview(customUser.getUserId(), animeId, reviewId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<ReviewResponseDto>> getUserReviews(
            @AuthenticationPrincipal CustomOAuth2User customUser,
            @PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(reviewService.getUserReviews(userId, customUser));
    }

    @GetMapping("/{anime_id}/{review_id}/share")
    public ResponseEntity<ReviewResponseDto> shareReview(
            @PathVariable("anime_id") Long animeId,
            @PathVariable("review_id") Long reviewId) {
        return ResponseEntity.ok(reviewService.shareReview(animeId, reviewId));
    }

    @PostMapping("/{anime_id}/{review_id}/like")
    public ResponseEntity<Void> likeReview(
            @PathVariable("anime_id") Long animeId,
            @PathVariable("review_id") Long reviewId) {
        reviewService.likeReview(animeId, reviewId);
        return ResponseEntity.ok().build();
    }
}
