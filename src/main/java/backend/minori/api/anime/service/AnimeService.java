package backend.minori.api.anime.service;

import backend.minori.api.anime.dto.AnimeResponseDto;
import backend.minori.api.anime.repository.AnimeRepository;
import backend.minori.domain.Anime;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    public List<AnimeResponseDto> getAllAnimes() {
        List<Anime> animes = animeRepository.findAll();
        return animes.stream()
                .map(anime -> new AnimeResponseDto(
                        anime.getAnimeId(),
                        anime.getTitleKr(),
                        anime.getTitleEn(),
                        anime.getTitleJp(),
                        anime.getAiringStatus(),
                        anime.getAiringDay(),
                        anime.getTotalEpisodeNumber(),
                        anime.getProduction(),
                        anime.getPlatform(),
                        anime.getStoryDescription(),
                        anime.getDirector(),
                        anime.getAiringType(),
                        anime.getSeries().getName()
                ))
                .collect(Collectors.toList());
    }

}
