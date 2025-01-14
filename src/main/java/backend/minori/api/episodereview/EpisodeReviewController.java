package backend.minori.api.episodereview;

import backend.minori.api.episodereview.dto.EpisodeReviewResponseDto;
import backend.minori.api.episodereview.dto.EpisodeReviewUpdateRequestDto;
import backend.minori.api.episodereview.service.EpisodeReviewService;
import backend.minori.domain.EpisodeReview;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/review/episode")
@RequiredArgsConstructor
public class EpisodeReviewController {

    private final EpisodeReviewService episodeReviewService;

    // 전체 에피소드 리뷰 목록 조회
    @GetMapping("/{anime_id}")
    public ResponseEntity<List<EpisodeReviewResponseDto>> getEpisodeReviews(
            @PathVariable("anime_id") Long animeId) {
        return ResponseEntity.ok(episodeReviewService.getEpisodeReviews(animeId));
    }

    // 특정 에피소드 리뷰 작성
    @PostMapping("/{anime_id}")
    public ResponseEntity<EpisodeReviewResponseDto> createEpisodeReview(
            @PathVariable("anime_id") Long animeId,
            @RequestBody EpisodeReview review) {
        return ResponseEntity.ok(episodeReviewService.createEpisodeReview(animeId, review));
    }

    // 특정 에피소드 리뷰 수정
    @PatchMapping("/{anime_id}/{episode_review_id}")
    public ResponseEntity<EpisodeReviewResponseDto> updateEpisodeReview(
            @PathVariable("anime_id") Long animeId,
            @PathVariable("episode_review_id") Long episodeReviewId,
            @RequestBody EpisodeReviewUpdateRequestDto requestDto) {
        return ResponseEntity.ok(episodeReviewService.updateEpisodeReview(animeId, episodeReviewId, requestDto));
    }

    // 특정 에피소드 리뷰 삭제
    @DeleteMapping("/{anime_id}/{episode_review_id}")
    public ResponseEntity<Void> deleteEpisodeReview(
            @PathVariable("anime_id") Long animeId,
            @PathVariable("episode_review_id") Long episodeReviewId) {
        episodeReviewService.deleteEpisodeReview(animeId, episodeReviewId);
        return ResponseEntity.ok().build();
    }

    // 특정 유저 에피소드 리뷰 조회
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<EpisodeReviewResponseDto>> getUserEpisodeReviews(
            @PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(episodeReviewService.getUserEpisodeReviews(userId));
    }

    // 에피소드 리뷰 공유
    @GetMapping("/{anime_id}/{episode_review_id}/share")
    public ResponseEntity<EpisodeReviewResponseDto> shareEpisodeReview(
            @PathVariable("anime_id") Long animeId,
            @PathVariable("episode_review_id") Long episodeReviewId) {
        return ResponseEntity.ok(episodeReviewService.shareEpisodeReview(animeId, episodeReviewId));
    }

    // 에피소드 리뷰 좋아요
    @PostMapping("/{anime_id}/{episode_review_id}/like")
    public ResponseEntity<Void> likeEpisodeReview(
            @PathVariable("anime_id") Long animeId,
            @PathVariable("episode_review_id") Long episodeReviewId) {
        episodeReviewService.likeEpisodeReview(animeId, episodeReviewId);
        return ResponseEntity.ok().build();
    }
}
