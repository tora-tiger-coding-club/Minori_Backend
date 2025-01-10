
package backend.minori.api.review.dto;

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
}


