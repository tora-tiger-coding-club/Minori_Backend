package backend.minori.api.user.service;

import backend.minori.api.user.repository.UserRepository;
import backend.minori.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
}
