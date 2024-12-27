package backend.minori.api.record;

import backend.minori.api.user.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecordController {

    @GetMapping("/hi")
    public ResponseEntity<MessageDto> hi() {
        return ResponseEntity.ok(new MessageDto("hi"));
    }

    @GetMapping("/hello")
    public ResponseEntity<MessageDto> hello() {
        return ResponseEntity.ok(new MessageDto("hello"));
    }
}
