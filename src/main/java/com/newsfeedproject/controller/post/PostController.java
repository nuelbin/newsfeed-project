package com.newsfeedproject.controller.post;

import java.util.List;

import com.newsfeedproject.common.session.SessionConst;
import com.newsfeedproject.dto.post.request.CreatePostRequestDto;
import com.newsfeedproject.dto.post.request.UpdatePostRequestDto;
import com.newsfeedproject.dto.post.response.CreatePostResponseDto;
import com.newsfeedproject.dto.post.response.FindPostResponseDto;
import com.newsfeedproject.dto.post.response.DeletePostResponseDto;
import com.newsfeedproject.dto.post.response.UpdatePostResponseDto;
import com.newsfeedproject.service.post.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	// 게시글 생성 API
	@PostMapping
	public ResponseEntity<CreatePostResponseDto> createPostAPI(HttpServletRequest request,
		@RequestBody CreatePostRequestDto createPostRequestdto) {
		//세션 가져오는 로직
		HttpSession session = request.getSession();
		//세션에서 유저 아이디를 찾는 로직
		Long userId = (Long)session.getAttribute(SessionConst.LOGIN_USER_ID);
		CreatePostResponseDto createPostResponseDto = postService.createPost(userId, createPostRequestdto);
		return ResponseEntity.ok(createPostResponseDto);
	}

	//게시글 조회 API
	@GetMapping("/{post_id}")
	public ResponseEntity<FindPostResponseDto> findPostByIdAPI(
		@PathVariable("post_id") Long id) {
		FindPostResponseDto findPostById = postService.findPostById(id);
		return ResponseEntity.ok(findPostById);
	}

	//게시물 전체 조회 API
	@GetMapping
	public List<FindPostResponseDto> FindALlPostAPI() {
		return postService.findAllPost();
	}

	//게시물 수정 API
	@PatchMapping("/{post_id}")
	public ResponseEntity<UpdatePostResponseDto> updatePostAPI(
		@PathVariable("post_id") Long postId,
		@RequestBody UpdatePostRequestDto updatePostRequestDto,HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long userId = (Long)session.getAttribute(SessionConst.LOGIN_USER_ID);

		//유저 아이디를 받아와서 글을 작성 한 유저만 글을 수정할 수 있게 만들어야 함
		UpdatePostResponseDto updatePost = postService.updatePost(userId, postId, updatePostRequestDto);
		return ResponseEntity.ok(updatePost);
	}

	//게시물 삭제 API
	@DeleteMapping("/{post_id}")
	public ResponseEntity<DeletePostResponseDto> deletePostAPI(
		@PathVariable("post_id") Long postId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long userId = (Long)session.getAttribute(SessionConst.LOGIN_USER_ID);

		DeletePostResponseDto deletePostResponseDto = postService.deletePost(userId, postId);
		return ResponseEntity.ok(deletePostResponseDto);
	}
}
