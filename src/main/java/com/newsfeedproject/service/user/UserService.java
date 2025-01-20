package com.newsfeedproject.service.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.newsfeedproject.common.config.PasswordEncoder;
import com.newsfeedproject.common.entity.user.User;
import com.newsfeedproject.common.exception.user.EmailAlreadyExistsUserException;
import com.newsfeedproject.common.exception.user.EmailNotFoundUserException;
import com.newsfeedproject.common.exception.user.PasswordMismatchUserException;
import com.newsfeedproject.common.exception.user.UserNotFoundUserException;
import com.newsfeedproject.dto.user.request.CreateUserRequestDto;
import com.newsfeedproject.dto.user.request.LoginUserRequestDto;
import com.newsfeedproject.dto.user.response.CreateUserResponseDto;
import com.newsfeedproject.dto.user.response.DeleteUserResponseDto;
import com.newsfeedproject.dto.user.response.FindUserResponseDto;
import com.newsfeedproject.repository.user.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	// 회원가입
	@Transactional
	public CreateUserResponseDto userSignupService(CreateUserRequestDto dto) {
		// 이메일이 DB에 있는 것과 중복인지 확인
		userRepository.findByEmail(dto.getEmail())
			.ifPresent(user -> {
				// 이미 사용 중인 이메일 예외처리
				throw new EmailAlreadyExistsUserException();
			});

		// 비번과 재입력비번 일치하는지 확인(equals는 주소값이 아닌, 값을 비교)
		if (!dto.getPassword().equals(dto.getReEnterPassword())) {
			// 비밀번호 불일치 예외처리
			throw new PasswordMismatchUserException();
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
	@Transactional
	public DeleteUserResponseDto userDeleteService(Long userId, String password) {
		// 사용자 조회
		User user = userRepository.findByIdAndIsDeletedFalse(userId)
			.orElseThrow(UserNotFoundUserException::new);

		// 비밀번호 확인
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new PasswordMismatchUserException();
		}

		// 탈퇴 처리(소프트 딜리트)
		user.markAsDeleted();
		userRepository.save(user);

		return new DeleteUserResponseDto();
	}

	// 로그인
	@Transactional
	public User userLoginService(LoginUserRequestDto dto) {
		// 이메일 기준으로 DB 유저 찾기
		Optional<User> findUser = userRepository.findByEmail(dto.getEmail());

		// 이메일이 DB에 없을 때 예외처리
		User user = findUser.orElseThrow(EmailNotFoundUserException::new);

		// 비밀번호 불일치 예외처리
		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new PasswordMismatchUserException();
		}

		// 로그인 완료 메시지 컨트롤러로 출력
		return user;
	}

	// 회원 다건 조회
	public List<FindUserResponseDto> userFindAllService() {
		// 탈퇴하지 않은 사용자만 조회
		List<User> userList = userRepository.findByIsDeletedFalse();

		// 조회된 User 엔티티를 FindUserResponseDto로 변환
		// stream으로 데이터를 변환한 뒤, 그 결과를 collect()를 통해 List로 변환하여 수집
		List<FindUserResponseDto> userResponseDtoList = userList.stream()
			.map(user -> new FindUserResponseDto(user.getUserName(), user.getCreatedAt()))
			.collect(Collectors.toList());

		// 변환된 DTO 리스트 반환
		return userResponseDtoList;
	}
}
