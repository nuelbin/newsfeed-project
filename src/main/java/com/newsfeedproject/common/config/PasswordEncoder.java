package com.newsfeedproject.common.config;

import at.favre.lib.crypto.bcrypt.BCrypt;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

	// 일반 문자열을 암호화시켜주는 함수
	public String encode(String rawPassword) {
		return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
	}

	// 암호화돼 있는 문자열과 암호화돼 있지 않은 문자열 비교 -> 같은 비밀번호인지 검증
	public boolean matches(String rawPassword, String encodedPassword) {
		BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
		return result.verified;
	}
}