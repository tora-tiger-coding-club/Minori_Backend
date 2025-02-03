package backend.minori.api.review.dto;

import backend.minori.common.auth.CustomOAuth2User;
import backend.minori.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private Long userId;
    private Long animeId;
    private String content;
    private int star;
    private int likes;
    private boolean isSpoiler;
    private boolean isPublic;

    public static ReviewResponseDto of(Review review) {
        return ReviewResponseDto.builder()
                .reviewId(review.getId())
                .userId(review.getUser().getId())
                .animeId(review.getAnime().getId())
                .content(review.getContent())
                .star(review.getStar())
                .likes(review.getLikes())
                .isSpoiler(review.isSpoiler())
                .isPublic(review.isPublic())
                .build();
    }

    // 로그인과 인증을 위한 새로운 메소드 추가
    public static ReviewResponseDto of(Review review, CustomOAuth2User user) {
        return ReviewResponseDto.builder()
                .reviewId(review.getId())
                .userId(review.getUser().getId())
                .animeId(review.getAnime().getId())
                .content(review.getContent())
                .star(review.getStar())
                .likes(review.getLikes())
                .isSpoiler(review.isSpoiler())
                .isPublic(review.isPublic())
                .build();
    }
}


