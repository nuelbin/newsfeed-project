package com.newsfeedproject.service.user;

import java.util.Optional;

import com.newsfeedproject.common.config.PasswordEncoder;
import com.newsfeedproject.common.entity.user.User;
import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;
import com.newsfeedproject.dto.user.request.CreateUserRequestDto;
import com.newsfeedproject.dto.user.request.LoginUserRequestDto;
import com.newsfeedproject.dto.user.response.CreateUserResponseDto;
import com.newsfeedproject.dto.user.response.LoginUserResponseDto;
import com.newsfeedproject.dto.user.response.LogoutUserResponseDto;
import com.newsfeedproject.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public CreateUserResponseDto userSignupService(CreateUserRequestDto dto) {
        // 이메일이 DB에 있는 것과 중복인지 확인
        userRepository.findByEmail(dto.getEmail())
            .ifPresent(user -> {
                // 이미 사용 중인 이메일 예외처리
                throw new BaseException(ResponseCode.EMAIL_ALREADY_EXISTS);
            });

        // 비번과 재입력비번 일치하는지 확인(equals는 주소값이 아닌, 값을 비교)
        if (!dto.getPassword().equals(dto.getReEnterPassword())) {
            // 비밀번호 불일치 예외처리
            throw new BaseException(ResponseCode.PASSWORD_MISMATCH);
        }

        // PasswordEncoder로 암호화
        String bcryptPassword = passwordEncoder.encode(dto.getPassword());

        // Db에 저장
        User user = new User(dto.getUserName(), dto.getEmail(), bcryptPassword);
        userRepository.save(user);

        // 회원 가입 완료 메시지 컨트롤러로 출력
        return new CreateUserResponseDto();
    }

    // 회원 탈퇴

    // 로그인
    public User userLoginService(LoginUserRequestDto dto) {
        // 이메일 기준으로 DB 유저 찾기
        Optional<User> findUser = userRepository.findByEmail(dto.getEmail());

        // 이메일이 DB에 없을 때 예외처리
        User user = findUser.orElseThrow(() -> new BaseException(ResponseCode.EMAIL_NOT_FOUND));

        // 비밀번호 불일치 예외처리
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BaseException(ResponseCode.PASSWORD_MISMATCH);
        }

        // 로그인 완료 메시지 컨트롤러로 출력
        return user;
    }

    // 회원 다건 조회
}
