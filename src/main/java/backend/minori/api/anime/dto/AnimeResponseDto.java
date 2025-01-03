package backend.minori.api.anime.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
}
