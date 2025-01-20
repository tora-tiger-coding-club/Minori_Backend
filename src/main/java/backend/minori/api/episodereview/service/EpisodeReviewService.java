package backend.minori.api.episodereview.service;

import backend.minori.api.episodereview.dto.EpisodeReviewResponseDto;
import backend.minori.api.episodereview.dto.EpisodeReviewUpdateRequestDto;
import backend.minori.api.episodereview.repository.EpisodeReviewRepository;
import backend.minori.domain.EpisodeReview;
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

    // 작품 에피소드 리뷰 목록 조회
    public List<EpisodeReviewResponseDto> getEpisodeReviews(Long animeId) {
        List<EpisodeReview> reviews = episodeReviewRepository.findByAnimeId(animeId);
        return reviews.stream()
                .map(EpisodeReviewResponseDto::of)
                .collect(Collectors.toList());
    }

    // 유저의 에피소드 리뷰 조회
    public List<EpisodeReviewResponseDto> getUserEpisodeReviews(Long userId) {
        List<EpisodeReview> reviews = episodeReviewRepository.findByUserId(userId);
        return reviews.stream()
                .map(EpisodeReviewResponseDto::of)
                .collect(Collectors.toList());
    }

    // 특정 에피소드 리뷰 작성
    @Transactional
    public EpisodeReviewResponseDto createEpisodeReview(Long animeId, EpisodeReview review) {
        review.setAnimeId(animeId);
        EpisodeReview savedReview = episodeReviewRepository.save(review);
        return EpisodeReviewResponseDto.of(savedReview);
    }

    // 특정 에피소드 리뷰 수정
    @Transactional
    public EpisodeReviewResponseDto updateEpisodeReview(Long animeId, Long episodeReviewId,
                                                        EpisodeReviewUpdateRequestDto requestDto) {
        EpisodeReview review = episodeReviewRepository.findByAnimeIdAndEpisodeReviewId(animeId, episodeReviewId)
                .orElseThrow(() -> new EntityNotFoundException("에피소드 리뷰를 찾을 수 없습니다."));

        review.updateReview(
                requestDto.isSpoiler(),
                requestDto.isPublic(),
                requestDto.getStar()
        );

        return EpisodeReviewResponseDto.of(review);
    }

    // 특정 에피소드 리뷰 삭제
    @Transactional
    public void deleteEpisodeReview(Long animeId, Long episodeReviewId) {
        EpisodeReview review = episodeReviewRepository.findByAnimeIdAndEpisodeReviewId(animeId, episodeReviewId)
                .orElseThrow(() -> new EntityNotFoundException("에피소드 리뷰를 찾을 수 없습니다."));
        episodeReviewRepository.delete(review);
    }

    // 특정 에피소드 리뷰 공유
    public EpisodeReviewResponseDto shareEpisodeReview(Long animeId, Long episodeReviewId) {
        EpisodeReview review = episodeReviewRepository.findByAnimeIdAndEpisodeReviewId(animeId, episodeReviewId)
                .orElseThrow(() -> new EntityNotFoundException("에피소드 리뷰를 찾을 수 없습니다."));
        return EpisodeReviewResponseDto.of(review);
    }

    // 에피소드 리뷰 좋아요
    @Transactional
    public void likeEpisodeReview(Long animeId, Long episodeReviewId) {
        EpisodeReview review = episodeReviewRepository.findByAnimeIdAndEpisodeReviewId(animeId, episodeReviewId)
                .orElseThrow(() -> new EntityNotFoundException("에피소드 리뷰를 찾을 수 없습니다."));
        review.increaseLikes();
    }
}


