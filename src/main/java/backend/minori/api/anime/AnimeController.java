package backend.minori.api.anime;

import backend.minori.api.anime.dto.AnimeResponseDto;
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
    public ResponseEntity<List<AnimeResponseDto>> getAllAnimes() {
        List<AnimeResponseDto> animeList = animeService.getAllAnimes();
        return ResponseEntity.ok(animeList);
    }
    @GetMapping("/{animeId}")
    public ResponseEntity<AnimeResponseDto> getAnimeFromAnimeId(@PathVariable Long animeId) {
        AnimeResponseDto anime = animeService.getAnimeFromAnimeId(animeId);
        return ResponseEntity.ok(anime);
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<AnimeResponseDto>> findAnimeByKeyword(@PathVariable String keyword) {
        List<AnimeResponseDto> animeList = animeService.findAnimeByKeyword(keyword);
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
