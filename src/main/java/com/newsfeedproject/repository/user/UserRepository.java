package com.newsfeedproject.repository.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsfeedproject.common.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	/*
	 * 회원가입
	 * 유저가 입력한 이메일이 기가입한 이메일인지 확인
	 */
	Optional<User> findByEmail(String email);

	/**
	 * 회원 다건 조회
	 * 탈퇴하지 않은 사용자만 조회
	 */
	List<User> findByIsDeletedFalse();

	/**
	 * 회원 탈퇴
	 * ID와 삭제 여부(탈퇴하지 않은 사용자)로 사용자 조회
	 */
	Optional<User> findByIdAndIsDeletedFalse(Long userId);
}
