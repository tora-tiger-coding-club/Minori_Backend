package backend.minori.api.review.dto;

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
                .reviewId(review.getReviewId())
                .userId(review.getUserId())
                .animeId(review.getAnimeId())
                .content(review.getContent())
                .star(review.getStar())
                .likes(review.getLikes())
                .isSpoiler(review.isSpoiler())
                .isPublic(review.isPublic())
                .build();
    }
}


