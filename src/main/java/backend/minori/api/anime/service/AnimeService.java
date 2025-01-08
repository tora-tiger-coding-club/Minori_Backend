package backend.minori.api.anime.service;

import backend.minori.api.anime.dto.AnimeResponseDto;
import backend.minori.api.anime.repository.AnimeRepository;
import backend.minori.domain.Anime;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    public List<AnimeResponseDto> getAllAnimes() {
        List<Anime> animes = animeRepository.findAll();
        return animes.stream()
                .map(AnimeResponseDto::fromEntity)
                .toList();
    }
}
