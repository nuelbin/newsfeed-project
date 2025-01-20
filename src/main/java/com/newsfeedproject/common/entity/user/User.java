package com.newsfeedproject.common.entity.user;

import org.hibernate.annotations.Comment;

import com.newsfeedproject.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	/**
	 *        < 이하 BaseEntity에서 상속받을 요소들 >
	 *        datetime created_at "가입일" ==  "생성일"
	 *        datetime upated_at "수정일"
	 *        datetime deleted_at "탈퇴일" == "삭제일"
	 *        tinyint is_deleted "삭제 여부"
	 */

	@Comment("유저 식별자")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT")
	private Long id;

	@Comment("유저명")
	@Column(
		name = "user_name",
		nullable = false
	)
	private String userName;

	@Comment("이메일")
	@Column(
		name = "email",
		nullable = false
	)
	private String email;

	@Comment("비밀번호")
	@Column(
		name = "password",
		nullable = false
	)
	private String password;

	// 회원 상태(false: 탈퇴한 회원 의미)
	// 소프트 딜리트(기본값 true)
	private Boolean status = true;

	public User(String userName, String email, String password) {
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

}
