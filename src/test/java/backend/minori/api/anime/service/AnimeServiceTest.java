package backend.minori.api.anime.service;

import backend.minori.api.anime.dto.AnimeResponseDto;
import backend.minori.api.anime.dto.AnimeSearchResponseDto;
import backend.minori.api.anime.repository.AnimeRepository;
import backend.minori.domain.Anime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {
    @Mock
    AnimeRepository animeRepository;

    @InjectMocks
    AnimeService animeService;

    private List<Anime> animeList;

    @BeforeEach
    void setUp() {
        animeList = List.of(
                Anime.builder()
                        .animeId(1L)
                        .titleKr("나루토")
                        .titleEn("Naruto")
                        .titleJp("ナルト")
                        .airingStatus("방영 종료")
                        .airingDay("월요일")
                        .totalEpisodeNumber(220)
                        .production("Studio Pierrot")
                        .platform("Netflix")
                        .storyDescription("나루토는 닌자가 되기 위한 여정을 담은 이야기입니다.")
                        .director("Hayato Date")
                        .airingType("TV")
                        .build(),

                Anime.builder()
                        .animeId(2L)
                        .titleKr("진격의 거인")
                        .titleEn("Attack on Titan")
                        .titleJp("進撃の巨人")
                        .airingStatus("완결")
                        .airingDay("토요일")
                        .totalEpisodeNumber(87)
                        .production("Wit Studio")
                        .platform("Amazon Prime")
                        .storyDescription("거인에 맞서 싸우는 인간들의 이야기.2")
                        .director("Tetsuro Araki")
                        .airingType("TV")
                        .build(),

                Anime.builder()
                        .animeId(3L)
                        .titleKr("진격의 거인 시즌2")
                        .titleEn("Attack on Titan Season2")
                        .titleJp("進撃の巨人 Season2")
                        .airingStatus("완결")
                        .airingDay("토요일")
                        .totalEpisodeNumber(87)
                        .production("Wit Studio")
                        .platform("Amazon Prime")
                        .storyDescription("거인에 맞서 싸우는 인간들의 이야기.2")
                        .director("Tetsuro Araki")
                        .airingType("TV")
                        .build()
        );
    }


    @DisplayName("전체 애니메이션 조회")
    @Test
    void getAllAnimesTest() {
        Mockito.when(animeRepository.findAll()).thenReturn(animeList);

        List<AnimeResponseDto> animeList = animeService.getAllAnimes();

        assertEquals(3, animeList.size());
        assertEquals("나루토", animeList.get(0).getTitleKr());
        assertEquals("진격의 거인", animeList.get(1).getTitleKr());
        assertEquals("진격의 거인 시즌2", animeList.get(2).getTitleKr());
    }

    @DisplayName("페이지네이션 적용 애니메이션 조회")
    @Test
    void getAllAnimesWithPageableTest() {
        Pageable pageable = PageRequest.of(0, 2);
        PageImpl<Anime> pageResult = new PageImpl<>(animeList.subList(0, 2), pageable, animeList.size());
        Mockito.when(animeRepository.findAll(pageable)).thenReturn(pageResult);

        List<AnimeResponseDto> animeList = animeService.getAllAnimesWithPageable(0, 2);

        assertEquals(2, animeList.size());
        assertEquals("나루토", animeList.get(0).getTitleKr());
        assertEquals("진격의 거인", animeList.get(1).getTitleKr());
    }

    @DisplayName("특정 애니메이션 정보 조회")
    @Test
    void getAnimeFromAnimeIdTest() {
        Mockito.when(animeRepository.findById(3L)).thenReturn(Optional.of(animeList.get(2)));
        AnimeResponseDto animeResponseDto = animeService.getAnimeFromAnimeId(3L);

        assertEquals("진격의 거인 시즌2", animeResponseDto.getTitleKr());
    }

    @DisplayName("한글 제목으로 애니메이션 검색")
    @Test
    void findAnimeByKeywordTest() {
        Mockito.when(animeRepository.findByTitleKrContaining("진격"))
                .thenReturn(animeList.subList(1, 3));

        List<AnimeSearchResponseDto> animeList = animeService.findAnimeByKeyword("진격");

        assertEquals(2, animeList.size());
        assertEquals("진격의 거인", animeList.get(0).getTitleKr());
        assertEquals("진격의 거인 시즌2", animeList.get(1).getTitleKr());
    }
}
