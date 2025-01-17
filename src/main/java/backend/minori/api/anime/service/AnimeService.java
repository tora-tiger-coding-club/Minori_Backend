package backend.minori.api.anime.service;

import backend.minori.api.anime.AnimeSpecification;
import backend.minori.api.anime.dto.AnimeResponseDto;
import backend.minori.api.anime.dto.AnimeSearchResponseDto;
import backend.minori.api.anime.repository.AnimeRepository;
import backend.minori.domain.Anime;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    public List<AnimeSearchResponseDto> getAllAnimes() {
        List<Anime> animes = animeRepository.findAll();
        return animes.stream()
                .map(AnimeSearchResponseDto::fromEntity)
                .toList();
    }

    public List<AnimeSearchResponseDto> getAllAnimesWithPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return animeRepository.findAll(pageable)
                .map(AnimeSearchResponseDto::fromEntity)
                .toList();
    }

    public List<AnimeSearchResponseDto> getFilteredAnimes(int page, int size, String season, String title, String genre, String tag) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Anime> specification = AnimeSpecification.filterBy(season, title, genre, tag);
        return animeRepository.findAll(specification, pageable)
                .map(AnimeSearchResponseDto::fromEntity)
                .toList();
    }

    public AnimeResponseDto getAnimeFromAnimeId(Long animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new IllegalArgumentException("애니메이션 정보를 찾을 수 없습니다."));
        return AnimeResponseDto.fromEntity(anime);
    }

    public List<AnimeSearchResponseDto> findAnimeByKeyword(String keyword) {
        List<Anime> animes = animeRepository.findByTitleKrContaining(keyword);
        return animes.stream()
                .map(AnimeSearchResponseDto::fromEntity)
                .toList();
    }
}
