package backend.minori.api.episodereview;

import backend.minori.api.episodereview.dto.EpisodeReviewResponseDto;
import backend.minori.api.episodereview.dto.EpisodeReviewUpdateRequestDto;
import backend.minori.common.auth.CustomOAuth2User;
import backend.minori.domain.EpisodeReview;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface EpisodeReviewControllerDocs {

    ResponseEntity<List<EpisodeReviewResponseDto>> getPublicEpisodeReviews(Long animeId);

    ResponseEntity<List<EpisodeReviewResponseDto>> getEpisodeReviews(
            @AuthenticationPrincipal CustomOAuth2User user, Long animeId);

    ResponseEntity<EpisodeReviewResponseDto> createEpisodeReview(
            @AuthenticationPrincipal CustomOAuth2User customUser, Long animeId, EpisodeReview reviewRequest);

    ResponseEntity<EpisodeReviewResponseDto> updateEpisodeReview(
            @AuthenticationPrincipal CustomOAuth2User customUser, Long animeId, Long episodeReviewId,
            EpisodeReviewUpdateRequestDto requestDto);

    ResponseEntity<Void> deleteEpisodeReview(
            @AuthenticationPrincipal CustomOAuth2User customUser, Long animeId, Long episodeReviewId);

    ResponseEntity<List<EpisodeReviewResponseDto>> getUserEpisodeReviews(
            @AuthenticationPrincipal CustomOAuth2User customUser, Long userId);

    ResponseEntity<EpisodeReviewResponseDto> shareEpisodeReview(Long animeId, Long episodeReviewId);

    ResponseEntity<Void> likeEpisodeReview(Long animeId, Long episodeReviewId);
}
