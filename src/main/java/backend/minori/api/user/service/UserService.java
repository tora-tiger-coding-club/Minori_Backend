package backend.minori.api.user.service;

import backend.minori.api.user.dto.UserSignupRequestDto;
import backend.minori.api.user.repository.UserRepository;
import backend.minori.common.auth.CustomOAuth2User;
import backend.minori.domain.Role;
import backend.minori.domain.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));
    }

    public void signupUser(CustomOAuth2User user, UserSignupRequestDto signupRequest) {
        if (!Objects.equals(user.getUserId(), signupRequest.getUserId())) {
            throw new IllegalArgumentException("사용자 정보가 올바르지 않습니다.");
        }
        if (user.getRole() != Role.GUEST) {
            throw new IllegalArgumentException("이미 회원 가입이 된 회원입니다.");
        }

        userRepository.save(User.builder()
                .userId(user.getUserId())
                .role(Role.USER)
                .username(signupRequest.getUsername())
                .introduce(signupRequest.getIntroduce())
                .isPublic(true)
                .build());
    }
}
