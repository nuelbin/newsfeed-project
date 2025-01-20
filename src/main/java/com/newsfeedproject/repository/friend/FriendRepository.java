package com.newsfeedproject.repository.friend;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsfeedproject.common.entity.friend.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {

	Optional<Friend> findById(Long id);

	Optional<Friend> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

	// 특정 User의 모든 친구 조회
	List<Friend> findAllByFromUserIdOrToUserId(Long fromUserId, Long toUserId);

}
