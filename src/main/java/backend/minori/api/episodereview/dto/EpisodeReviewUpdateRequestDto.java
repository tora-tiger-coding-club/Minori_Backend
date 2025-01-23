package backend.minori.api.episodereview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EpisodeReviewUpdateRequestDto {
    private boolean isSpoiler;
    private boolean isPublic;
    private int star;
}
