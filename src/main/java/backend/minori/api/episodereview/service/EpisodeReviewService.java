package backend.minori.api.episodereview.service;

import backend.minori.api.anime.repository.AnimeRepository;
import backend.minori.api.episodereview.dto.EpisodeReviewResponseDto;
import backend.minori.api.episodereview.dto.EpisodeReviewUpdateRequestDto;
import backend.minori.api.episodereview.repository.EpisodeReviewRepository;
import backend.minori.api.user.repository.UserRepository;
import backend.minori.common.auth.CustomOAuth2User;
import backend.minori.domain.Anime;
import backend.minori.domain.EpisodeReview;
import backend.minori.domain.Role;
import backend.minori.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EpisodeReviewService {
    private final EpisodeReviewRepository episodeReviewRepository;
    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;

    public List<EpisodeReviewResponseDto> getPublicEpisodeReviews(Long animeId) {
        return episodeReviewRepository.findByAnimeIdAndIsPublicTrue(animeId)
                .stream()
                .map(EpisodeReviewResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<EpisodeReviewResponseDto> getEpisodeReviews(Long animeId, CustomOAuth2User customUser) {
        if (customUser.getRole() == Role.ADMIN) {
            return episodeReviewRepository.findByAnimeId(animeId)
                    .stream()
                    .map(review -> EpisodeReviewResponseDto.of(review))
                    .collect(Collectors.toList());
        }
        return episodeReviewRepository.findByAnimeIdAndIsPublicTrue(animeId)
                .stream()
                .map(EpisodeReviewResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public EpisodeReviewResponseDto createEpisodeReview(Long animeId, EpisodeReview reviewRequest, CustomOAuth2User customUser) {
        if (customUser.getRole() == Role.GUEST) {
            throw new IllegalStateException("회원 가입이 필요한 서비스입니다.");
        }

        User user = userRepository.findById(customUser.getUserId())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new IllegalStateException("Anime not found"));

        EpisodeReview review = EpisodeReview.builder()
                .user(user)
                .anime(anime)
                .content(reviewRequest.getContent())
                .star(reviewRequest.getStar())
                .isPublic(reviewRequest.isPublic())
                .isSpoiler(reviewRequest.isSpoiler())
                .episode(reviewRequest.getEpisode())
                .likes(0)
                .build();

        validateEpisodeReviewCreation(review);
        EpisodeReview savedReview = episodeReviewRepository.save(review);
        return EpisodeReviewResponseDto.of(savedReview);
    }

    @Transactional
    public EpisodeReviewResponseDto updateEpisodeReview(Long userId, Long animeId, Long episodeReviewId,
                                                        EpisodeReviewUpdateRequestDto requestDto,
                                                        CustomOAuth2User customUser) {
        EpisodeReview review = episodeReviewRepository.findByAnimeIdAndEpisodeReviewId(animeId, episodeReviewId)
                .orElseThrow(() -> new EntityNotFoundException("에피소드 리뷰를 찾을 수 없습니다."));

        validateEpisodeReviewOwnership(review, userId);
        validateAnimeEpisodeReview(review, animeId);

        review.updateReview(
                requestDto.isSpoiler(),
                requestDto.isPublic(),
                requestDto.getStar(),
                requestDto.getContent()
        );
        return EpisodeReviewResponseDto.of(review);
    }


    @Transactional
    public void deleteEpisodeReview(Long userId, Long animeId, Long episodeReviewId) {
        EpisodeReview review = episodeReviewRepository.findByAnimeIdAndEpisodeReviewId(animeId, episodeReviewId)
                .orElseThrow(() -> new EntityNotFoundException("에피소드 리뷰를 찾을 수 없습니다."));

        validateEpisodeReviewOwnership(review, userId);
        validateAnimeEpisodeReview(review, animeId);

        episodeReviewRepository.delete(review);
    }

    public List<EpisodeReviewResponseDto> getUserEpisodeReviews(Long userId, CustomOAuth2User customUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return episodeReviewRepository.findByUserAndIsPublicTrue(user)
                .stream()
                .map(EpisodeReviewResponseDto::of)
                .collect(Collectors.toList());
    }

    public EpisodeReviewResponseDto shareEpisodeReview(Long animeId, Long episodeReviewId) {
        EpisodeReview review = episodeReviewRepository.findByEpisodeReviewIdAndAnimeIdAndIsPublicTrue(episodeReviewId, animeId)
                .orElseThrow(() -> new EntityNotFoundException("공개된 에피소드 리뷰를 찾을 수 없습니다."));
        return EpisodeReviewResponseDto.of(review);
    }

    @Transactional
    public void likeEpisodeReview(Long animeId, Long episodeReviewId) {
        EpisodeReview review = episodeReviewRepository.findByAnimeIdAndEpisodeReviewId(animeId, episodeReviewId)
                .orElseThrow(() -> new EntityNotFoundException("에피소드 리뷰를 찾을 수 없습니다."));
        review.increaseLikes();
    }

    private void validateEpisodeReviewCreation(EpisodeReview review) {
        if (episodeReviewRepository.existsByAnimeIdAndUserAndEpisode(review.getAnime().getAnimeId(), review.getUser(), review.getEpisode())) {
            throw new IllegalArgumentException("이미 작성한 에피소드 리뷰가 존재합니다.");
        }
    }

    private void validateEpisodeReviewOwnership(EpisodeReview review, Long userId) {
        if (!review.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("에피소드 리뷰에 대한 권한이 없습니다.");
        }
    }

    private void validateAnimeEpisodeReview(EpisodeReview review, Long animeId) {
        if (!review.getAnime().getAnimeId().equals(animeId)) {
            throw new IllegalArgumentException("해당 애니메이션의 에피소드 리뷰가 아닙니다.");
        }
    }
}
