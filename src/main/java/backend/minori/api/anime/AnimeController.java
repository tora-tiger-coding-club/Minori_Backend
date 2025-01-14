package backend.minori.api.anime;

import backend.minori.api.anime.dto.AnimeResponseDto;
import backend.minori.api.anime.dto.AnimeSearchResponseDto;
import backend.minori.api.anime.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/anime")
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeService animeService;

    @GetMapping("/serach")
    public ResponseEntity<List<AnimeSearchResponseDto>> getAnimes(@RequestParam(required = false, defaultValue = "0", value = "page") int page,
                                                               @RequestParam(required = false, defaultValue = "50", value = "limit") int size,
                                                               @RequestParam(required = false) String season,
                                                               @RequestParam(required = false) String title,
                                                               @RequestParam(required = false) String genre,
                                                               @RequestParam(required = false) String tag) {

        if (season == null && title == null && genre == null && tag == null) {
            List<AnimeSearchResponseDto> animeList = animeService.getAllAnimesWithPageable(page, size);
            return ResponseEntity.ok(animeList);
        }

        List<AnimeSearchResponseDto> filteredAnimeList = animeService.getFilteredAnimes(page, size, season, title, genre, tag);
        return ResponseEntity.ok(filteredAnimeList);
    }

    @GetMapping("/{animeId}")
    public ResponseEntity<AnimeResponseDto> getAnimeFromAnimeId(@PathVariable Long animeId) {
        AnimeResponseDto anime = animeService.getAnimeFromAnimeId(animeId);
        return ResponseEntity.ok(anime);
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<AnimeSearchResponseDto>> findAnimeByKeyword(@PathVariable String keyword) {
        List<AnimeSearchResponseDto> animeList = animeService.findAnimeByKeyword(keyword);
        return ResponseEntity.ok(animeList);
    }

//    @GetMapping("/season")
//    public ResponseEntity<> sans() {
//        return ResponseEntity.ok(new MessageDto("hi"));
//    }
//
//    @GetMapping("/series")
//    public ResponseEntity<> sans() {
//        return ResponseEntity.ok(new MessageDto("hi");
//    }
}
