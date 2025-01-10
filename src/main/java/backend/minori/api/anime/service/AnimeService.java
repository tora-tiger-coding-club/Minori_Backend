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

    public AnimeResponseDto getAnimeFromAnimeId(Long animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new IllegalArgumentException("애니메이션 정보를 찾을 수 없습니다."));
        return AnimeResponseDto.fromEntity(anime);
    }

    public List<AnimeResponseDto> findAnimeByKeyword(String keyword) {
        List<Anime> animes = animeRepository.findByTitleKrContaining(keyword);
        return animes.stream()
                .map(AnimeResponseDto::fromEntity)
                .toList();
    }
}
