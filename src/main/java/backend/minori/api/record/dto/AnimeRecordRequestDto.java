package backend.minori.api.record.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class AnimeRecordRequestDto {
    private Long animeId;
    private String status;
    private Integer currentEpisode;
    private LocalDate startedAt;
}
