package com.chatbot.user.service;

import com.chatbot.block.service.LoginBlockService;
import com.chatbot.user.dto.UserDto.ResponseDto;
import com.chatbot.user.entity.User;
import com.chatbot.user.repository.UserRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final LoginBlockService loginBlockService;

    @Value("${login.code.prefix}")
    private String codePrefix;

    public ResponseDto login(User userInfo, boolean isStory) {
        String code = userInfo.getCode();
        // 코드 접두사가 있다면 접두사를 지우고 없다면 잘못된 코드임을 반환
        if (code.contains(codePrefix)) {
            code = code.replaceAll(codePrefix, "");
        } else {
            User user = User.of(userInfo.getCode(), null);
            List<String> blockIds = getBlockIds(false, isStory);
            return User.userToResponseDto(user, 4, blockIds.get(0), null, isStory);
        }

        // 코드를 통해 존재하는 유저인지 확인
        User user = validateCode(code);
        // 존재하지 않는 유저라면 잘못된 코드임을 반환
        if (user == null) {
            List<String> blockIds = getBlockIds(false, isStory);
            return User.userToResponseDto(User.of(userInfo.getCode(), null), 4, blockIds.get(0), null, isStory);
        }
        // 밴 당한 유저라면 밴 당한 사실을 반환
        if (user.isBanned()) {
            return User.userToResponseDto(user, 2, null, null, isStory);
        }
        // 아직 한 번도 로그인되지 않은 유저라면 카카오아이디를 저장하고 성공적인 로그인이 되었음을 반환
        if (user.getFirstId() == null) {
            user.setFirstId(userInfo.getFirstId());
            userRepository.save(user);
            List<String> blockIds = getBlockIds(true, isStory);
            return User.userToResponseDto(user, 1, blockIds.get(0), validateTutorialId(isStory, blockIds), isStory);
        }
        // 로그인을 했었는데 같은 아이디로 로그인했다면 성공적인 로그인이 되있음을 반환
        if (user.getFirstId().equals(userInfo.getFirstId())) {
            List<String> blockIds = getBlockIds(true, isStory);
            return User.userToResponseDto(user, 1, blockIds.get(0), validateTutorialId(isStory, blockIds), isStory);
        }
        // 로그인을 했었는데 다른 아이디로 로그인했다면 밴 시키고 중복 로그인을 반환
        if (!user.getFirstId().equals(userInfo.getFirstId())) {
            user.setSecondId(userInfo.getFirstId());
            user.setBanned(true);
            userRepository.save(user);
            return User.userToResponseDto(User.of(userInfo.getCode(), userInfo.getFirstId()), 3, null, null, isStory);
        }
        // 위 경우 모두 아니라면 잘못된 코드임을 반환
        List<String> blockIds = getBlockIds(false, isStory);
        return User.userToResponseDto(User.of(userInfo.getCode(), null), 4, blockIds.get(0), null, isStory);
    }

    private List<String> getBlockIds(boolean isSuccessfulLogin, boolean isStory) {
        return loginBlockService.findLoginBlockByLogin(isSuccessfulLogin, isStory);
    }

    private String validateTutorialId(boolean isStory, List<String> blocksId) {
        if (isStory) {
            return blocksId.get(1);
        }
        return null;
    }

    private User validateCode(String code) {
        return userRepository.findByCode(code).orElse(null);
    }

    public User findUserById(String kakaoId) {
        return userRepository.findByFirstId(kakaoId).orElse(null);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
