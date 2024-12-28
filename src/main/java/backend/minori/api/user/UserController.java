package backend.minori.api.user;

import backend.minori.api.user.dto.MessageDto;
import backend.minori.api.user.dto.UserDto;
import backend.minori.api.user.service.UserService;
import backend.minori.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/hi")
    public ResponseEntity<MessageDto> hi() {
        return ResponseEntity.ok(new MessageDto("hi"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAllUsers () {
        List<User> allUsers = userService.findAllUsers();

        return ResponseEntity.ok(
                allUsers.stream().map(user ->
                        UserDto.builder()
                            .userId(user.getUserId())
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .isPublic(user.isPublic())
                            .bio(user.getBio())
                            .activated(user.getActivated())
                            .createdAt(user.getCreatedAt())
                            .updatedAt(user.getUpdatedAt())
                            .build()).toList());
    }
}
