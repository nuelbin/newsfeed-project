package com.newsfeedproject.controller.friend;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsfeedproject.dto.friend.request.FriendRequestDto;
import com.newsfeedproject.dto.friend.response.FriendWithDateResponseDto;
import com.newsfeedproject.dto.friend.response.FriendWithUpdateResponseDto;
import com.newsfeedproject.service.friend.FriendService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

	private final FriendService friendService;

	//친구 신청
	@PostMapping
	public FriendWithDateResponseDto createFriend(
		@RequestBody FriendRequestDto requestDto,
		HttpServletRequest request
	) {
		return friendService.createFriendService(requestDto, request);
	}

	// 친구 수락
	@PatchMapping("/accept/{toUserId}/{fromUserId}")
	public ResponseEntity<String> acceptFriendStatus(@PathVariable Long fromUserId, @PathVariable Long toUserId) {
		friendService.acceptFriendRequest(fromUserId, toUserId); // 서비스 메서드 호출
		return ResponseEntity.ok("친구 요청을 승낙하였습니다.");
	}

	// 친구 거절
	@PatchMapping("/decline/{toUserId}/{fromUserId}")
	public ResponseEntity<String> declineFriendStatus(@PathVariable Long fromUserId, @PathVariable Long toUserId) {
		friendService.declineFriendRequest(fromUserId, toUserId); // 서비스 메서드 호출
		return ResponseEntity.ok("친구 요청을 거절하였습니다.");
	}

	// 친구 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		friendService.deleteFriend(id);
		return ResponseEntity.ok("친구를 삭제 하였습니다.");
	}

	// 친구 단건 조회
	@GetMapping("/user/{fromUserId}/{toUserId}")
	public ResponseEntity<FriendWithUpdateResponseDto> getFriend(@PathVariable Long fromUserId,
		@PathVariable Long toUserId) {
		FriendWithUpdateResponseDto friendDto = friendService.getFriend(fromUserId, toUserId);
		return ResponseEntity.ok(friendDto);
	}

	// 친구 다건 조회
	@GetMapping("/user/{fromUserId}")
	public ResponseEntity<List<FriendWithUpdateResponseDto>> getAllFriends(@PathVariable Long fromUserId) {
		List<FriendWithUpdateResponseDto> friends = friendService.getAllFriends(fromUserId);
		return ResponseEntity.ok(friends);
	}
}