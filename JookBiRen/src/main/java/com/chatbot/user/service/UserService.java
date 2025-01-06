package com.chatbot.user.service;

import com.chatbot.user.dto.UserDto.ResponseDto;
import com.chatbot.user.entity.UserEp00;
import com.chatbot.user.repository.UserQuerydslRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    //    private final UserRepository userRepository;
    private final UserQuerydslRepository userRepository;
//    private final LoginBlockService loginBlockService;

    @Value("${login.code.prefix}")
    private String codePrefix;

    public ResponseDto login(UserEp00 userInfo) {
        String code = userInfo.getCode();
        // 코드 접두사가 있다면 접두사를 지우고 없다면 잘못된 코드임을 반환
//        if (!code.contains(codePrefix)) {
//            return unsuccessfulLogin(userInfo);
//        }
//        code = code.replaceAll(codePrefix, "");

//        if (code.contains(codePrefix)) {
//            code = code.replaceAll(codePrefix, "");
//        } else {
////            List<String> blockIds = getBlockIds(false, true);
//            return invalidCodeWithNoPrefix(userInfo);
//        }

        // 코드를 통해 존재하는 유저인지 확인
        UserEp00 user = validateCode(code);
        // 존재하지 않는 유저라면 잘못된 코드임을 반환
        if (user == null) {
//            List<String> blockIds = getBlockIds(false, true);
            return unsuccessfulLogin(userInfo);
        }

        // 밴 당한 유저라면 밴 당한 사실을 반환
        if (user.isBanned()) {
            return UserEp00.userToResponseDto(user, 2, null);
        }

        // 아직 한 번도 로그인되지 않은 유저라면 카카오아이디를 저장하고 성공적인 로그인이 되었음을 반환
        if (user.getFirstId() == null) {
            user.setFirstId(userInfo.getFirstId());
            userRepository.save(user);
//            List<String> blockIds = getBlockIds(true, true);
            return successfulLogin(user);
        }

        // 로그인을 했었는데 같은 아이디로 로그인했다면 성공적인 로그인이 되있음을 반환
        if (user.getFirstId().equals(userInfo.getFirstId())) {
//            List<String> blockIds = getBlockIds(true, true);
            return successfulLogin(user);
        }

        // 로그인을 했었는데 다른 아이디로 로그인했다면 밴 시키고 중복 로그인을 반환
        if (!user.getFirstId().equals(userInfo.getFirstId())) {
            user.setSecondId(userInfo.getFirstId());
            user.setBanned(true);
            userRepository.save(user);
            return UserEp00.userToResponseDto(UserEp00.of(userInfo.getCode(), userInfo.getFirstId()), 3, null);
        }

        // 위 경우 모두 아니라면 잘못된 코드임을 반환
//        List<String> blockIds = getBlockIds(false, true);
        return unsuccessfulLogin(userInfo);
    }

    private ResponseDto successfulLogin(UserEp00 user) {
        List<String> blockIds = new ArrayList<>();
        blockIds.add("6762dd757c842c4d39423b2c");
        return UserEp00.userToResponseDto(user, 1, blockIds.get(0));
    }

    private static ResponseDto unsuccessfulLogin(UserEp00 userInfo) {
        List<String> blockIds = new ArrayList<>();
        blockIds.add("64fee1bc6a34bd19e09017f1");
        return UserEp00.userToResponseDto(UserEp00.of(userInfo.getCode(), null), 4, blockIds.get(0));
    }

    private UserEp00 validateCode(String code) {
        return userRepository.findByCode(code).orElse(null);
    }

    public UserEp00 findUserByIdOrderByCustom(String kakaoId) {
        List<UserEp00> users = userRepository.findAllByFirstIdOrderByCustomDesc(kakaoId);
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public void updateUser(UserEp00 user) {
        userRepository.save(user);
    }

//    public ResponseDto login(User userInfo, boolean isStory) {
//        String code = userInfo.getCode();
//        // 코드 접두사가 있다면 접두사를 지우고 없다면 잘못된 코드임을 반환
//        if (code.contains(codePrefix)) {
//            code = code.replaceAll(codePrefix, "");
//        } else {
//            User user = User.of(userInfo.getCode(), null);
//            List<String> blockIds = getBlockIds(false, isStory);
//            return User.userToResponseDto(user, 4, blockIds.get(0), null, isStory);
//        }
//
//        // 코드를 통해 존재하는 유저인지 확인
//        User user = validateCode(code);
//        // 존재하지 않는 유저라면 잘못된 코드임을 반환
//        if (user == null) {
//            List<String> blockIds = getBlockIds(false, isStory);
//            return User.userToResponseDto(User.of(userInfo.getCode(), null), 4, blockIds.get(0), null, isStory);
//        }
//        // 밴 당한 유저라면 밴 당한 사실을 반환
//        if (user.isBanned()) {
//            return User.userToResponseDto(user, 2, null, null, isStory);
//        }
//        // 아직 한 번도 로그인되지 않은 유저라면 카카오아이디를 저장하고 성공적인 로그인이 되었음을 반환
//        if (user.getFirstId() == null) {
//            user.setFirstId(userInfo.getFirstId());
//            userRepository.save(user);
//            List<String> blockIds = getBlockIds(true, isStory);
//            return User.userToResponseDto(user, 1, blockIds.get(0), validateTutorialId(isStory, blockIds), isStory);
//        }
//        // 로그인을 했었는데 같은 아이디로 로그인했다면 성공적인 로그인이 되있음을 반환
//        if (user.getFirstId().equals(userInfo.getFirstId())) {
//            List<String> blockIds = getBlockIds(true, isStory);
//            return User.userToResponseDto(user, 1, blockIds.get(0), validateTutorialId(isStory, blockIds), isStory);
//        }
//        // 로그인을 했었는데 다른 아이디로 로그인했다면 밴 시키고 중복 로그인을 반환
//        if (!user.getFirstId().equals(userInfo.getFirstId())) {
//            user.setSecondId(userInfo.getFirstId());
//            user.setBanned(true);
//            userRepository.save(user);
//            return User.userToResponseDto(User.of(userInfo.getCode(), userInfo.getFirstId()), 3, null, null, isStory);
//        }
//        // 위 경우 모두 아니라면 잘못된 코드임을 반환
//        List<String> blockIds = getBlockIds(false, isStory);
//        return User.userToResponseDto(User.of(userInfo.getCode(), null), 4, blockIds.get(0), null, isStory);
//    }

//    private List<String> getBlockIds(boolean isSuccessfulLogin, boolean isStory) {
//        return loginBlockService.findLoginBlockByLogin(isSuccessfulLogin, isStory);
//    }

//    private String validateTutorialId(boolean isStory, List<String> blocksId) {
//        if (isStory) {
//            return blocksId.get(1);
//        }
//        return null;
//    }

//    public User findUserById(String kakaoId) {
//        List<User> users = userRepository.findAllByFirstId(kakaoId);
//        if (users == null || users.isEmpty()) {
//            return null;
//        }
//        return users.get(0);
//    }
}
