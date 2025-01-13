package backend.minori.api.user.service;

import backend.minori.api.user.repository.UserRepository;
import backend.minori.domain.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));
    }

    public void registerUser() {

    }
}
