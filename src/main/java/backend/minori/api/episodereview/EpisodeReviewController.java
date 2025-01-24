package backend.minori.api.episodereview;

import backend.minori.api.episodereview.dto.EpisodeReviewResponseDto;
import backend.minori.api.episodereview.dto.EpisodeReviewUpdateRequestDto;
import backend.minori.api.episodereview.service.EpisodeReviewService;
import backend.minori.common.auth.CustomOAuth2User;
import backend.minori.domain.EpisodeReview;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/review/episode")
@RequiredArgsConstructor
public class EpisodeReviewController implements EpisodeReviewControllerDocs {

    private final EpisodeReviewService episodeReviewService;

    @Override
    @GetMapping("/{anime_id}/public")
    public ResponseEntity<List<EpisodeReviewResponseDto>> getPublicEpisodeReviews(
            @PathVariable("anime_id") Long animeId) {
        return ResponseEntity.ok(episodeReviewService.getPublicEpisodeReviews(animeId));
    }

    @Override
    @GetMapping("/{anime_id}")
    public ResponseEntity<List<EpisodeReviewResponseDto>> getEpisodeReviews(
            @AuthenticationPrincipal CustomOAuth2User user,
            @PathVariable("anime_id") Long animeId) {
        return ResponseEntity.ok(episodeReviewService.getEpisodeReviews(animeId, user));
    }

    @Override
    @PostMapping("/{anime_id}")
    public ResponseEntity<EpisodeReviewResponseDto> createEpisodeReview(
            @AuthenticationPrincipal CustomOAuth2User customUser,
            @PathVariable("anime_id") Long animeId,
            @RequestBody EpisodeReview reviewRequest) {
        return ResponseEntity.ok(episodeReviewService.createEpisodeReview(animeId, reviewRequest, customUser));
    }

    @Override
    @PutMapping("/{anime_id}/{episode_review_id}")
    public ResponseEntity<EpisodeReviewResponseDto> updateEpisodeReview(
            @AuthenticationPrincipal CustomOAuth2User customUser,
            @PathVariable("anime_id") Long animeId,
            @PathVariable("episode_review_id") Long episodeReviewId,
            @RequestBody EpisodeReviewUpdateRequestDto requestDto) {
        return ResponseEntity.ok(episodeReviewService.updateEpisodeReview(customUser.getUserId(), animeId, episodeReviewId, requestDto, customUser));
    }

    @Override
    @DeleteMapping("/{anime_id}/{episode_review_id}")
    public ResponseEntity<Void> deleteEpisodeReview(
            @AuthenticationPrincipal CustomOAuth2User customUser,
            @PathVariable("anime_id") Long animeId,
            @PathVariable("episode_review_id") Long episodeReviewId) {
        episodeReviewService.deleteEpisodeReview(customUser.getUserId(), animeId, episodeReviewId);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<EpisodeReviewResponseDto>> getUserEpisodeReviews(
            @AuthenticationPrincipal CustomOAuth2User customUser,
            @PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(episodeReviewService.getUserEpisodeReviews(userId, customUser));
    }

    @Override
    @GetMapping("/{anime_id}/{episode_review_id}/share")
    public ResponseEntity<EpisodeReviewResponseDto> shareEpisodeReview(
            @PathVariable("anime_id") Long animeId,
            @PathVariable("episode_review_id") Long episodeReviewId) {
        return ResponseEntity.ok(episodeReviewService.shareEpisodeReview(animeId, episodeReviewId));
    }

    @Override
    @PostMapping("/{anime_id}/{episode_review_id}/like")
    public ResponseEntity<Void> likeEpisodeReview(
            @PathVariable("anime_id") Long animeId,
            @PathVariable("episode_review_id") Long episodeReviewId) {
        episodeReviewService.likeEpisodeReview(animeId, episodeReviewId);
        return ResponseEntity.ok().build();
    }
}
