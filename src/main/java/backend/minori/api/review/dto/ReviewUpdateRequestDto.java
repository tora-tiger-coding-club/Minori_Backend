package backend.minori.api.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewUpdateRequestDto {
    private boolean isSpoiler;
    private boolean isPublic;
    private int star;
}
