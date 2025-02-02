package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.core.error.ex.Exception400;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        User oldUSer = userRepository.findByUsername(joinDTO.getUsername());

        if (oldUSer != null) {
            throw new Exception400("이미 존재하는 유저네임입니다.");
        }
        userRepository.save(joinDTO.toEntity());
    }

    public User 로그인(UserRequest.LoginDTO loginDTO) {
        User user = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        return user;
    }
}
