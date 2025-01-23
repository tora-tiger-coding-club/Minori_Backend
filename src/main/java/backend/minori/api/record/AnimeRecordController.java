package backend.minori.api.record;

import backend.minori.api.record.dto.AnimeRecordRequestDto;
import backend.minori.api.record.dto.AnimeRecordResponseDto;
import backend.minori.api.record.service.AnimeRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class AnimeRecordController {
    private final AnimeRecordService animeRecordService;

    @GetMapping("/{user_id}")
    public ResponseEntity<List<AnimeRecordResponseDto>> getAnimeRecords(@PathVariable Long userId) {
        List<AnimeRecordResponseDto> responses = animeRecordService.getAllAnimeRecordsByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{user_id}")
    public ResponseEntity<AnimeRecordResponseDto> addAnimeRecord(@PathVariable Long userId,
                                                                 @RequestBody AnimeRecordRequestDto request) {
        AnimeRecordResponseDto response = animeRecordService.saveAnimeRecord(userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{user_id}/{record_id}")
    public ResponseEntity<AnimeRecordResponseDto> getAnimeRecordById(@PathVariable("user_id") Long userId,
                                                                     @PathVariable("record_id") Long recordId) {
        AnimeRecordResponseDto response = animeRecordService.getAnimeRecordById(userId, recordId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{user_id}/{record_id}")
    public ResponseEntity<AnimeRecordResponseDto> updateAnimeRecord(@PathVariable("user_id") Long userId,
                                                                    @PathVariable("record_id") Long recordId,
                                                                    @RequestBody AnimeRecordRequestDto updatedRequest) {
        AnimeRecordResponseDto response = animeRecordService.updateAnimeRecord(userId, recordId, updatedRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{user_id}/{record_id}")
    public ResponseEntity<Void> deleteAnimeRecord(@PathVariable("user_id") Long userId,
                                                                    @PathVariable("record_id") Long recordId) {
        animeRecordService.deleteAnimeRecord(userId, recordId);
        return ResponseEntity.ok().build();
    }


}
