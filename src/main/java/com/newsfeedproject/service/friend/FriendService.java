package com.newsfeedproject.service.friend;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newsfeedproject.common.entity.friend.Friend;
import com.newsfeedproject.common.entity.friend.FriendStatus;
import com.newsfeedproject.common.entity.user.User;
import com.newsfeedproject.common.exception.BaseException;
import com.newsfeedproject.common.exception.ResponseCode;
import com.newsfeedproject.common.exception.friend.FriendCannotBeDeletedException;
import com.newsfeedproject.common.exception.friend.FriendCannotBeSelfException;
import com.newsfeedproject.common.exception.friend.FriendNotFoundException;
import com.newsfeedproject.common.exception.friend.FriendRequestNotAuthorizedException;
import com.newsfeedproject.common.session.SessionConst;
import com.newsfeedproject.dto.friend.request.FriendRequestDto;
import com.newsfeedproject.dto.friend.response.FriendWithDateResponseDto;
import com.newsfeedproject.dto.friend.response.FriendWithUpdateResponseDto;
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
		User fromUser = userRepository.findById(fromUserId)
			.orElseThrow(() -> new BaseException(ResponseCode.USER_NOT_FOUND));
		User toUser = userRepository.findById(requestDto.getToUserId())
			.orElseThrow(() -> new BaseException(ResponseCode.USER_NOT_FOUND));
		if (fromUser.getId().equals(toUser.getId())) {
			throw new FriendCannotBeSelfException();
		}
		// Friend 객체 생성 (fromUser와 toUser를 정확히 설정)
		Friend newFriend = new Friend(fromUser, toUser, FriendStatus.FRIEND_PENDING);

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
	public void acceptFriendRequest(Long friendId, Long loggedInUserId) {
		Friend friend = friendRepository.findById(friendId)
			.orElseThrow(() -> new FriendNotFoundException());
		if (!friend.getToUser().getId().equals(loggedInUserId)) {
			throw new FriendRequestNotAuthorizedException();
		}
		friend.updateStatus(FriendStatus.FRIEND_ACCEPT);
	}

	// 친구 거절 PATCH /api/friends//{fromuserId}/{tofriendId)
	@Transactional
	public void declineFriendRequest(Long friendId, Long loggedInUserId) {
		Friend friend = friendRepository.findById(friendId)
			.orElseThrow(() -> new FriendNotFoundException());
		if (!friend.getToUser().getId().equals(loggedInUserId)) {
			throw new FriendRequestNotAuthorizedException();
		}
		friend.updateStatus(FriendStatus.FRIEND_DECLINE);
	}

	// 친구 삭제 DELETE /api/delete/friends/{tofriendId)/{fromuserId}
	@Transactional
	public void deleteFriend(Long id) {
		Friend friend = friendRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("친구를 찾을 수 없습니다."));
		friend.deleteFriend();
	}

	// 친구 단건 조회
	public FriendWithUpdateResponseDto getFriend(Long id, Long userId) {
		// 친구 조회, 없으면 예외 발생
		Friend friend = friendRepository.findById(id)
			.orElseThrow(FriendNotFoundException::new);

		// 삭제된 친구인지 확인
		if (friend.isDeleted()) {
			throw new FriendCannotBeDeletedException();
		}

		// 친구의 요청/수락 여부에 따라 응답 생성
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
			throw new FriendNotFoundException();
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