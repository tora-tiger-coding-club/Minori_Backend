package backend.minori.api.anime;

import backend.minori.api.anime.dto.AnimeResponseDto;
import backend.minori.api.anime.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
