package backend.minori.api.episodereview.dto;

import backend.minori.domain.EpisodeReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EpisodeReviewResponseDto {
    private Long episodeReviewId;
    private Long userId;
    private Long animeId;
    private String content;
    private int star;
    private int likes;
    private boolean isSpoiler;
    private int episode;
    private boolean isPublic;

    public static EpisodeReviewResponseDto of(EpisodeReview review) {
        return EpisodeReviewResponseDto.builder()
                .episodeReviewId(review.getEpisodeReviewId())
                .userId(review.getUserId())
                .animeId(review.getAnimeId())
                .content(review.getContent())
                .star(review.getStar())
                .likes(review.getLikes())
                .isSpoiler(review.isSpoiler())
                .episode(review.getEpisode())
                .isPublic(review.isPublic())
                .build();
    }
}
