package com.newsfeedproject.controller.comment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsfeedproject.dto.comment.request.CreateCommentRequestDto;
import com.newsfeedproject.dto.comment.request.UpdateCommentRequestDto;
import com.newsfeedproject.dto.comment.response.CreateCommentResponseDto;
import com.newsfeedproject.dto.comment.response.DeleteCommentResponseDto;
import com.newsfeedproject.dto.comment.response.FindAllCommentResponseDto;
import com.newsfeedproject.dto.comment.response.FindAllReplyCommentResponseDto;
import com.newsfeedproject.dto.comment.response.UpdateCommentResponseDto;
import com.newsfeedproject.service.comment.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{post_id}/comments")
public class CommentController {

	// 속성
	private final CommentService commentService;

	// 생성자 -> 어노테이션 사용

	// 기능
	@PostMapping // 댓글 생성
	public ResponseEntity<CreateCommentResponseDto> createComment(
		@PathVariable("post_id") Long postId, // (name="...")로 적으면 더 빡세게 적을 수 있다!
		@RequestBody CreateCommentRequestDto createRequestDto
	) { //+ session.getAttribute~~ 인증 시 추가
		CreateCommentResponseDto createdComment = commentService.createComment(postId, createRequestDto);
		return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
	}

	@GetMapping // 댓글 다건 조회
	public ResponseEntity<List<FindAllCommentResponseDto>> findParentComments(
		@PathVariable("post_id") Long postId
	) {
		List<FindAllCommentResponseDto> findAllCommentResponseDtoList = commentService.findParentComments(postId);
		return new ResponseEntity<>(findAllCommentResponseDtoList, HttpStatus.OK);
	}

	@GetMapping("/{comment_id}/replies")  // 대댓글만 다건 조회
	public ResponseEntity<List<FindAllReplyCommentResponseDto>> findChildrenComments(
		@PathVariable("comment_id") Long commentId // 여기서의 commentId = parentId
	) {
		List<FindAllReplyCommentResponseDto> replies = commentService.findChildrenComments(commentId);
		return new ResponseEntity<>(replies, HttpStatus.OK);
	}

	@PutMapping("/{comment_id}") // 댓글 수정
	public ResponseEntity<UpdateCommentResponseDto> updateComment(
		@PathVariable("comment_id") Long commentId,
		@RequestBody UpdateCommentRequestDto updateCommentRequestDto
	) { //+ session.getAttribute~~ 인증 시 추가
		UpdateCommentResponseDto updatedComment = commentService.updateComment(commentId, updateCommentRequestDto);
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}

	@DeleteMapping("/{comment_id}") // 댓글 삭제
	public ResponseEntity<DeleteCommentResponseDto> deleteComment(
		@PathVariable("comment_id") Long commentId
	) { //+ session.getAttribute~~ 인증 시 추가
		DeleteCommentResponseDto deleteComment = commentService.deleteComment(commentId);
		return new ResponseEntity<>(deleteComment, HttpStatus.OK);
	}

}




