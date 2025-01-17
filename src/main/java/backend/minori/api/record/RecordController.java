package backend.minori.api.record;

import backend.minori.api.user.dto.MessageDto;
import backend.minori.api.record.dto.AnimationRecord;
import backend.minori.api.record.service.AnimationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordController {

    private final AnimationRecordService animationRecordService;

    @GetMapping("/{user_id}")
    public ResponseEntity<List<AnimationRecord>> getRecordsByUserId(@PathVariable("user_id") Long userId) {
        List<AnimationRecord> records = animationRecordService.getRecordsByUserId(userId);
        return ResponseEntity.ok(records);
    }

    @PostMapping("/{user_id}")
    public ResponseEntity<AnimationRecord> addRecord(@PathVariable("user_id") Long userId, @RequestBody AnimationRecord record) {
        AnimationRecord savedRecord = animationRecordService.saveRecord(userId, record);
        return ResponseEntity.ok(savedRecord);
    }

    @GetMapping("/{user_id}/{record_id}")
    public ResponseEntity<AnimationRecord> getRecordById(@PathVariable("user_id") Long userId, @PathVariable("record_id") Long recordId) {
        AnimationRecord record = animationRecordService.getRecordById(userId, recordId);
        return ResponseEntity.ok(record);
    }

    @PatchMapping("/{user_id}/{record_id}")
    public ResponseEntity<AnimationRecord> updateRecord(@PathVariable("user_id") Long userId, @PathVariable("record_id") Long recordId, @RequestBody AnimationRecord record) {
        AnimationRecord updatedRecord = animationRecordService.updateRecord(userId, recordId, record);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/{user_id}/{record_id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable("user_id") Long userId, @PathVariable("record_id") Long recordId) {
        animationRecordService.deleteRecord(userId, recordId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{user_id}/{record_id}/share")
    public ResponseEntity<MessageDto> shareRecord(@PathVariable("user_id") Long userId, @PathVariable("record_id") Long recordId) {
        String shareMessage = animationRecordService.shareRecord(userId, recordId);
        return ResponseEntity.ok(new MessageDto(shareMessage));
    }
}
