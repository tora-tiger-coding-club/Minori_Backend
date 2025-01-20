package backend.minori.api.anime.dto;

import backend.minori.domain.Anime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AnimeResponseDto {
    private Long animeId;
    private String titleKr;
    private String titleEn;
    private String titleJp;
    private String airingStatus;
    private String airingDay;
    private Integer totalEpisodeNumber;
    private String production;
    private String platform;
    private String storyDescription;
    private String director;
    private String airingType;
    private String seriesName;

    public static AnimeResponseDto fromEntity(Anime anime) {
        return AnimeResponseDto.builder()
                .animeId(anime.getAnimeId())
                .titleKr(anime.getTitleKr())
                .titleEn(anime.getTitleEn())
                .titleJp(anime.getTitleJp())
                .airingStatus(anime.getAiringStatus())
                .airingDay(anime.getAiringDay())
                .totalEpisodeNumber(anime.getTotalEpisodeNumber())
                .production(anime.getProduction())
                .platform(anime.getPlatform())
                .storyDescription(anime.getStoryDescription())
                .director(anime.getDirector())
                .airingType(anime.getAiringType())
                .seriesName(anime.getSeries() != null ? anime.getSeries().getName() : null)
                .build();
    }
}
