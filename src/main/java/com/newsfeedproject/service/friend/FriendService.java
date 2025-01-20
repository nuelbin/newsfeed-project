package com.newsfeedproject.service.friend;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newsfeedproject.common.entity.friend.Friend;
import com.newsfeedproject.common.entity.friend.FriendStatus;
import com.newsfeedproject.common.entity.user.User;
import com.newsfeedproject.common.session.SessionConst;
import com.newsfeedproject.dto.friend.FriendRequestDto;
import com.newsfeedproject.dto.friend.FriendWithDateResponseDto;
import com.newsfeedproject.dto.friend.FriendWithUpdateResponseDto;
import com.newsfeedproject.repository.friend.FriendRepository;
import com.newsfeedproject.repository.user.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {

	private final UserRepository userRepository;
	private final FriendRepository friendRepository;

	// 세션에서 userId 가져오기
	private Long getUserIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(SessionConst.LOGIN_USER_ID) == null) {
			throw new IllegalStateException("로그인 세션이 유효하지 않습니다.");
		}
		return (Long)session.getAttribute(SessionConst.LOGIN_USER_ID);
	}

	// 친구 신청
	@Transactional
	public FriendWithDateResponseDto createFriendService(FriendRequestDto requestDto, HttpServletRequest request) {
		Long fromUserId = getUserIdFromSession(request); // 세션에서 사용자 ID 가져오기

		// fromUserId로 User 조회
		User fromUser = userRepository.findById(fromUserId)
			.orElseThrow(() -> new IllegalArgumentException("신청할 유저를 찾을 수 없습니다."));

		// toUserId로 User 조회
		User toUser = userRepository.findById(requestDto.getToUserId())
			.orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));

		// 같은 유저로 친구 요청하는지 검증
		if (fromUser.getId().equals(toUser.getId())) {
			throw new IllegalArgumentException("자기 자신에게 친구 요청을 할 수 없습니다.");
		}

		// Friend 객체 생성 (fromUser와 toUser를 정확히 설정)
		Friend newFriend = new Friend(fromUser, toUser, FriendStatus.FRIEND_PENDING);

		// Friend 저장
		Friend savedFriend = friendRepository.save(newFriend);

		return new FriendWithDateResponseDto(
			savedFriend.getId(),
			toUser.getId(),
			toUser.getUserName(),
			savedFriend.getStatus(),
			savedFriend.getCreatedAt()
		);
	}

	// 친구 수락 PATCH /api/friends/{tofriendId)/{fromuserId}
	@Transactional
	public void AcceptFriendStatusService(Long fromUserId, Long toUserId) {
		// fromUserId와 toUserId로 친구 관계 찾기
		Friend friend = friendRepository.findByFromUserIdAndToUserId(fromUserId, toUserId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
		// 친구 수락하여 저장
		friend.updateStatus(FriendStatus.FRIEND_ACCEPT);

		// friendRepository.save(friend); // 영속성 컨텍스트로 인해 49번째 줄에서 처리가 완료 된다.
		// 변경감지(DB에서 비교 후 다르면 갱신)
	}

	// 친구 거절 PATCH /api/friends//{fromuserId}/{tofriendId)
	@Transactional
	public void DeclineFriendStatusService(Long fromUserId, Long toUserId) {

		Friend friend = friendRepository.findByFromUserIdAndToUserId(fromUserId, toUserId)
			.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

		friend.updateStatus(FriendStatus.FRIEND_DECLINE);

		// friendRepository.save(friend);
	}

	// 친구 삭제 DELETE /api/delete/friends/{tofriendId)/{fromuserId}
	@Transactional
	public void deleteFriend(Long id) {
		Friend friend = friendRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("친구를 찾을 수 없습니다."));
		// 삭제 처리
		friend.deleteFriend();

		// Friend를 삭제하지 않고, 삭제 상태로 업데이트
		// friendRepository.save(friend);
	}

	// 친구 단건 조회
	public FriendWithUpdateResponseDto getFriend(Long id, Long userId) {
		Friend friend = friendRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("친구를 조회할 수 없습니다."));

		if (friend.isDeleted()) {
			throw new IllegalArgumentException("삭제된 친구는 조회할 수 없습니다.");
		}

		if (friend.getFromUser().getId().equals(userId)) {
			return new FriendWithUpdateResponseDto(
				friend.getId(),
				friend.getToUser().getId(),
				friend.getToUser().getUserName(),
				friend.getStatus(),
				friend.getUpdatedAt()
			);
		} else if (friend.getToUser().getId().equals(userId)) {
			return new FriendWithUpdateResponseDto(
				friend.getId(),
				friend.getFromUser().getId(),
				friend.getFromUser().getUserName(),
				friend.getStatus(),
				friend.getUpdatedAt()
			);
		} else {
			throw new IllegalArgumentException("친구를 찾을 수 없습니다.");
		}
	}

	// 친구 다건 조회
	public List<FriendWithUpdateResponseDto> getAllFriends(Long userId) {
		List<Friend> friends = friendRepository.findAllByFromUserIdOrToUserId(userId, userId);

		return friends.stream()
			.filter(friend -> !friend.isDeleted()) // 삭제된 데이터 필터링
			.map(friend -> {
				if (friend.getFromUser().getId().equals(userId)) {
					return new FriendWithUpdateResponseDto(
						friend.getId(),
						friend.getToUser().getId(),
						friend.getToUser().getUserName(),
						friend.getStatus(),
						friend.getUpdatedAt()
					);
				} else {
					return new FriendWithUpdateResponseDto(
						friend.getId(),
						friend.getFromUser().getId(),
						friend.getFromUser().getUserName(),
						friend.getStatus(),
						friend.getUpdatedAt()
					);
				}
			}).toList();
	}
}