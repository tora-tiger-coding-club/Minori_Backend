package backend.minori.api.episodereview.dto;


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
}
