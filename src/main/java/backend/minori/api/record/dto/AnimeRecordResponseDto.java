package backend.minori.api.record.dto;

import backend.minori.domain.AnimeRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class AnimeRecordResponseDto {
    private Long recordId;
    private Long userId;
    private Long animeId;
    private String status;
    private Integer currentEpisode;
    private LocalDate startedAt;

    public static AnimeRecordResponseDto of(AnimeRecord animeRecord) {
        return AnimeRecordResponseDto.builder()
                .recordId(animeRecord.getId())
                .userId(animeRecord.getUser().getId())
                .animeId(animeRecord.getAnime().getId())
                .status(animeRecord.getStatus())
                .currentEpisode(animeRecord.getCurrentEpisode())
                .startedAt(animeRecord.getStartedAt())
                .build();
    }
}
