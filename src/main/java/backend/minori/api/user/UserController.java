package backend.minori.api.user;

import backend.minori.api.user.dto.UserResistRequestDto;
import backend.minori.api.user.dto.UserResponseDto;
import backend.minori.api.user.service.UserService;
import backend.minori.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info/{userId}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable Long userId) {
        User user = userService.findUserById(userId);

        return new ResponseEntity<>(UserResponseDto.of(user), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity signupUser(UserResistRequestDto userResistRequestDto,
                                     @AuthenticationPrincipal UserDetails user) {

        return ResponseEntity.ok(user.getUsername());
    }
}
