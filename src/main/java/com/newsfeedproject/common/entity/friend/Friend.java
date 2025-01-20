package com.newsfeedproject.common.entity.friend;

import com.newsfeedproject.common.entity.BaseEntity;
import com.newsfeedproject.common.entity.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "friend")
public class Friend extends BaseEntity {
	// 속성
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// JPA에서 엔티티의 기본 키(primary key)를 자동으로 생성하기 위한 설정
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY) // 필요할 때만 가져와야 한다.
	@JoinColumn(name = "from_user_id", nullable = false)
	private User fromUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_user_id", nullable = false)
	private User toUser;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FriendStatus status;

	// 생성자
	public Friend(User fromUser, User toUser, FriendStatus status) {
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.status = status;
	}

	// 친구 상태 변경 메서드
	public void updateStatus(FriendStatus newStatus) {
		this.status = newStatus;
	}

	// 친구 삭제 메서드
	public void deleteFriend() {
		if (!this.isDeleted) {
			this.markAsDeleted(); // BaseEntity의 메서드 호출
		} else {
			throw new IllegalStateException("이미 삭제된 친구 관계입니다.");
		}
	}
}