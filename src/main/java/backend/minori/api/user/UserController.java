package backend.minori.api.user;

import backend.minori.api.user.dto.UserSignupRequestDto;
import backend.minori.api.user.dto.UserResponseDto;
import backend.minori.api.user.service.UserService;
import backend.minori.common.auth.CustomOAuth2User;
import backend.minori.common.jwt.service.JwtService;
import backend.minori.domain.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/info/{userId}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable Long userId) {
        User user = userService.findUserById(userId);

        return new ResponseEntity<>(UserResponseDto.of(user), HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity<Void> signupUser(@AuthenticationPrincipal CustomOAuth2User user,
        UserSignupRequestDto userSignupRequestDto,
        HttpServletResponse response
    ) {
        userService.signupUser(user, userSignupRequestDto);
        
        // TODO : OAuth2LoginSuccessHandler에 있는걸 그대로 따왔는데 OAuth2LoginSuccessHandler로 보낼방법 생각하기
        String accessToken = jwtService.createAccessToken(user.getEmail(), user.getUserId(), user.getRole().getKey());
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(user.getEmail(), refreshToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
