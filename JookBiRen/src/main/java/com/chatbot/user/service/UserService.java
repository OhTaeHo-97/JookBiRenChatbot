package com.chatbot.user.service;

import com.chatbot.block.service.LoginBlockService;
import com.chatbot.user.dto.UserDto.ResponseDto;
import com.chatbot.user.entity.User;
import com.chatbot.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
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

    public ResponseDto login(User userInfo) {
        String code = userInfo.getCode();
        if (code.contains(codePrefix)) {
            code = code.replaceAll(codePrefix, "");
        } else {
            User user = User.of(userInfo.getCode(), null);
            List<String> blockIds = getBlockIds(false);
            return User.userToResponseDto(user, 4, blockIds.get(0), null);
        }

        User user = validateCode(code);
        if (user == null) {
            List<String> blockIds = getBlockIds(false);
            return User.userToResponseDto(User.of(userInfo.getCode(), null), 4, blockIds.get(0), null);
        }
        if (user.isBanned()) {
            return User.userToResponseDto(user, 2, null, null);
        }
        if (user.getFirstId() == null) {
            user.setFirstId(userInfo.getFirstId());
            userRepository.save(user);
            List<String> blockIds = getBlockIds(true);
            return User.userToResponseDto(user, 1, blockIds.get(0), blockIds.get(1));
        }
        if (user.getFirstId().equals(userInfo.getFirstId())) {
            List<String> blockIds = getBlockIds(true);
            return User.userToResponseDto(user, 1, blockIds.get(0), blockIds.get(1));
        }
        if (!user.getFirstId().equals(userInfo.getFirstId())) {
            user.setSecondId(userInfo.getFirstId());
            user.setBanned(true);
            userRepository.save(user);
            return User.userToResponseDto(User.of(userInfo.getCode(), userInfo.getFirstId()), 3, null, null);
        }
        List<String> blockIds = getBlockIds(false);
        return User.userToResponseDto(User.of(userInfo.getCode(), null), 4, blockIds.get(0), null);
    }

    private List<String> getBlockIds(boolean isSuccessfulLogin) {
        return loginBlockService.findLoginBlockByLogin(isSuccessfulLogin);
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
