package backend.minori.api.review.service;

import backend.minori.api.review.dto.ReviewResponseDto;
import backend.minori.api.review.dto.ReviewUpdateRequestDto;
import backend.minori.api.review.repository.ReviewRepository;
import backend.minori.domain.Review;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;

    // 애니메이션 전체 리뷰 목록 조회
    public List<ReviewResponseDto> getAnimeReviews(Long animeId) {
        List<Review> reviews = reviewRepository.findByAnimeId(animeId);
        return reviews.stream()
                .map(ReviewResponseDto::of)
                .collect(Collectors.toList());
    }

    // 특정 유저 애니메이션 리뷰 조회
    public List<ReviewResponseDto> getUserReviews(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream()
                .map(ReviewResponseDto::of)
                .collect(Collectors.toList());
    }

    // 애니메이션 리뷰 작성
    @Transactional
    public ReviewResponseDto createReview(Long animeId, Review review) {
        review.setAnimeId(animeId);
        Review savedReview = reviewRepository.save(review);
        return ReviewResponseDto.of(savedReview);
    }

    // 애니메이션 특정 리뷰 수정
    @Transactional
    public ReviewResponseDto updateReview(Long animeId, Long reviewId, ReviewUpdateRequestDto requestDto) {
        Review review = reviewRepository.findByAnimeIdAndReviewId(animeId, reviewId)
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));

        review.updateReview(
                requestDto.isSpoiler(),
                requestDto.isPublic(),
                requestDto.getStar()
        );

        return ReviewResponseDto.of(review);
    }

    // 애니메이션 특정 리뷰 삭제
    @Transactional
    public void deleteReview(Long animeId, Long reviewId) {
        Review review = reviewRepository.findByAnimeIdAndReviewId(animeId, reviewId)
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));
        reviewRepository.delete(review);
    }

    // 애니메이션 특정 리뷰 공유
    public ReviewResponseDto shareReview(Long animeId, Long reviewId) {
        Review review = reviewRepository.findByAnimeIdAndReviewId(animeId, reviewId)
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));
        return ReviewResponseDto.of(review);
    }

    // 애니메이션 리뷰 좋아요
    @Transactional
    public void likeReview(Long animeId, Long reviewId) {
        Review review = reviewRepository.findByAnimeIdAndReviewId(animeId, reviewId)
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));
        review.increaseLikes();
    }
}


