package backend.minori.api.anime.dto;

import backend.minori.domain.Anime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AnimeSearchResponseDto {
    private Long animeId;
    private String titleKr;
    private String airingStatus;
    private String airingType;

    public static AnimeSearchResponseDto fromEntity(Anime anime) {
        return AnimeSearchResponseDto.builder()
                .animeId(anime.getAnimeId())
                .titleKr(anime.getTitleKr())
                .airingStatus(anime.getAiringStatus())
                .airingType(anime.getAiringType())
                .build();
    }
}
