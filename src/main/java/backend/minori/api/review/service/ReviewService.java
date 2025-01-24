package backend.minori.api.review.service;

import backend.minori.api.anime.repository.AnimeRepository;
import backend.minori.api.review.dto.ReviewResponseDto;
import backend.minori.api.review.dto.ReviewUpdateRequestDto;
import backend.minori.api.review.repository.ReviewRepository;
import backend.minori.api.user.repository.UserRepository;
import backend.minori.common.auth.CustomOAuth2User;
import backend.minori.common.jwt.service.JwtService;
import backend.minori.domain.Anime;
import backend.minori.domain.Review;
import backend.minori.domain.Role;
import backend.minori.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AnimeRepository animeRepository;

    public List<ReviewResponseDto> getPublicAnimeReviews(Long animeId) {
        return reviewRepository.findByAnimeIdAndIsPublicTrue(animeId)
                .stream()
                .map(ReviewResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDto> getAnimeReviews(Long animeId, CustomOAuth2User customUser) {
        if (customUser.getRole() == Role.ADMIN) {
            return reviewRepository.findByAnimeId(animeId)
                    .stream()
                    .map(review -> ReviewResponseDto.of(review, customUser))
                    .collect(Collectors.toList());
        }
        return reviewRepository.findByAnimeIdAndIsPublicTrue(animeId)
                .stream()
                .map(review -> ReviewResponseDto.of(review, customUser))
                .collect(Collectors.toList());
    }

    public ReviewResponseDto createReview(Long animeId, Review reviewRequest, CustomOAuth2User customUser) {
        if (customUser.getRole() == Role.GUEST) {
            throw new IllegalStateException("회원 가입이 필요한 서비스입니다.");
        }

        User user = userRepository.findById(customUser.getUserId())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new IllegalStateException("Anime not found"));

        Review review = Review.builder()
                .user(user)
                .anime(anime)
                .content(reviewRequest.getContent())
                .star(reviewRequest.getStar())
                .isPublic(reviewRequest.isPublic())
                .isSpoiler(reviewRequest.isSpoiler())
                .likes(0)
                .build();

        validateReviewCreation(review);
        Review savedReview = reviewRepository.save(review);
        return ReviewResponseDto.of(savedReview, customUser);
    }

    public ReviewResponseDto updateReview(Long userId, Long animeId, Long reviewId,
                                          ReviewUpdateRequestDto requestDto,
                                          CustomOAuth2User customUser) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        validateReviewOwnership(review, userId);
        validateAnimeReview(review, animeId);


        review.updateReview(
                requestDto.isSpoiler(),
                requestDto.isPublic(),
                requestDto.getStar(),
                requestDto.getContent()
        );
        return ReviewResponseDto.of(review, customUser);
    }


    public void deleteReview(Long userId, Long animeId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        validateReviewOwnership(review, userId);
        validateAnimeReview(review, animeId);

        reviewRepository.delete(review);
    }

    public List<ReviewResponseDto> getUserReviews(Long userId, CustomOAuth2User customUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return reviewRepository.findByUserAndIsPublicTrue(user)
                .stream()
                .map(review -> ReviewResponseDto.of(review, customUser))
                .collect(Collectors.toList());
    }

    public ReviewResponseDto shareReview(Long animeId, Long reviewId) {
        Review review = reviewRepository.findByReviewIdAndAnimeIdAndIsPublicTrue(reviewId, animeId)
                .orElseThrow(() -> new IllegalArgumentException("공개된 리뷰를 찾을 수 없습니다."));
        return ReviewResponseDto.of(review);
    }

    public void likeReview(Long animeId, Long reviewId) {
        Review review = reviewRepository.findByReviewIdAndAnimeId(reviewId, animeId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        review.increaseLikes();
    }

    private void validateReviewCreation(Review review) {
        if (reviewRepository.existsByAnimeIdAndUser(review.getAnime().getAnimeId(), review.getUser())) {
            throw new IllegalArgumentException("이미 작성한 리뷰가 존재합니다.");
        }
    }

    private void validateReviewOwnership(Review review, Long userId) {
        if (!review.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("리뷰에 대한 권한이 없습니다.");
        }
    }

    private void validateAnimeReview(Review review, Long animeId) {
        if (!review.getAnime().getAnimeId().equals(animeId)) {
            throw new IllegalArgumentException("해당 애니메이션의 리뷰가 아닙니다.");
        }
    }
}


