package backend.minori.api.record;

import backend.minori.api.record.dto.AnimeRecordRequestDto;
import backend.minori.api.record.dto.AnimeRecordResponseDto;
import backend.minori.api.record.service.AnimeRecordService;
import backend.minori.common.auth.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class AnimeRecordController {
    private final AnimeRecordService animeRecordService;

    @GetMapping("/{user_id}")
    public ResponseEntity<List<AnimeRecordResponseDto>> getAnimeRecords(@AuthenticationPrincipal CustomOAuth2User user) {
        List<AnimeRecordResponseDto> responses = animeRecordService.getAllAnimeRecordsByUserId(user);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{user_id}")
    public ResponseEntity<AnimeRecordResponseDto> addAnimeRecord(@AuthenticationPrincipal CustomOAuth2User user,
                                                                 @RequestBody AnimeRecordRequestDto request) {
        AnimeRecordResponseDto response = animeRecordService.saveAnimeRecord(user, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{user_id}/{record_id}")
    public ResponseEntity<AnimeRecordResponseDto> getAnimeRecordById(@AuthenticationPrincipal CustomOAuth2User user,
                                                                     @PathVariable("record_id") Long recordId) {
        AnimeRecordResponseDto response = animeRecordService.getAnimeRecordById(user, recordId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{user_id}/{record_id}")
    public ResponseEntity<AnimeRecordResponseDto> updateAnimeRecord(@AuthenticationPrincipal CustomOAuth2User user,
                                                                    @PathVariable("record_id") Long recordId,
                                                                    @RequestBody AnimeRecordRequestDto updatedRequest) {
        AnimeRecordResponseDto response = animeRecordService.updateAnimeRecord(user, recordId, updatedRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{user_id}/{record_id}")
    public ResponseEntity<Void> deleteAnimeRecord(@AuthenticationPrincipal CustomOAuth2User user,
                                                                    @PathVariable("record_id") Long recordId) {
        animeRecordService.deleteAnimeRecord(user, recordId);
        return ResponseEntity.ok().build();
    }


}
