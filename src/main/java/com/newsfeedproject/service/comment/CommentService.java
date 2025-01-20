package com.newsfeedproject.service.comment;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newsfeedproject.common.entity.comment.Comment;
import com.newsfeedproject.common.entity.post.Post;
import com.newsfeedproject.common.entity.user.User;
import com.newsfeedproject.common.exception.comment.CommentNotFoundException;
import com.newsfeedproject.common.exception.comment.PostNotFoundException;
import com.newsfeedproject.dto.comment.request.CreateCommentRequestDto;
import com.newsfeedproject.dto.comment.request.UpdateCommentRequestDto;
import com.newsfeedproject.dto.comment.response.CommentDto;
import com.newsfeedproject.dto.comment.response.CreateCommentResponseDto;
import com.newsfeedproject.dto.comment.response.DeleteCommentResponseDto;
import com.newsfeedproject.dto.comment.response.FindAllCommentResponseDto;
import com.newsfeedproject.dto.comment.response.FindAllReplyCommentResponseDto;
import com.newsfeedproject.dto.comment.response.UpdateCommentResponseDto;
import com.newsfeedproject.repository.comment.CommentRepository;
import com.newsfeedproject.repository.post.PostRepository;
import com.newsfeedproject.repository.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	// 댓글 생성
	@Transactional
	public CreateCommentResponseDto createComment(Long postId, CreateCommentRequestDto createRequestDto) {

		log.info("서비스 들어옴");

		// 피드(게시물)와 작성자 정보 조회
		Post post = postRepository.findPostById(postId)
			.orElseThrow(() -> new PostNotFoundException()); // 예외처리 추가
		log.info(post.toString());

		User user = userRepository.findById(createRequestDto.getUserId())
			.orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
		log.info(user.toString());

		Long parentId = createRequestDto.getParentCommentId();

		// 부모 아이디의 존재여부 검증 로직 (대댓글인 경우)
		if (parentId != null) {
			log.info("부모아이디 존재함");
			boolean existsById = commentRepository.existsById(parentId);
			if (!existsById) { // 부모댓글
				throw new CommentNotFoundException(); // 같은 코드 if(existById == false) -> 예외처리 추가
			} else {
				// 해당하는 부모 아이디를 조회
				Comment parentComment = commentRepository.findById(createRequestDto.getParentCommentId())
					.orElseThrow(() -> new CommentNotFoundException());
				log.info(parentComment.toString());

				// 댓글 엔티티 생성
				Comment comment = new Comment(
					createRequestDto.getContent(),
					user,
					post,
					parentComment
				);

				// 댓글 목록 추가
				parentComment.addReplie(comment);

				// 데이터베이스에 댓글 저장
				Comment savedComment = commentRepository.save(comment);

				// 저장된 댓글 정보를 CreateCommentResponseDto로 변환하여 반환
				return new CreateCommentResponseDto(
					savedComment.getId(),
					savedComment.getParent().getId(),
					savedComment.getUser().getUserName(),
					savedComment.getContent(),
					savedComment.getCreatedAt());
			}
		}

		// 댓글인 경우
		Comment comment = new Comment(createRequestDto.getContent(),
			user,
			post);

		// 데이터베이스에 댓글 저장
		Comment savedComment = commentRepository.save(comment);

		// 저장된 댓글 정보를 CreateCommentResponseDto로 변환하여 반환
		return new CreateCommentResponseDto(
			savedComment.getId(),
			0L,
			savedComment.getUser().getUserName(),
			savedComment.getContent(),
			savedComment.getCreatedAt());
	}

	// 댓글 업데이트
	@Transactional
	public UpdateCommentResponseDto updateComment(Long commentId, UpdateCommentRequestDto updateCommentRequestDto) {

		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentNotFoundException());

		comment.updateContent(
			updateCommentRequestDto
		);

		Comment updatedComment = commentRepository.save(comment);  // 변경된 댓글을 저장

		return new UpdateCommentResponseDto(
			updatedComment.getId(),
			updatedComment.getContent());

	}

	// 댓글 삭제
	@Transactional
	public DeleteCommentResponseDto deleteComment(Long commentId) {

		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentNotFoundException()); // 예외처리 추가

		commentRepository.delete(comment);

		return new DeleteCommentResponseDto(
			"댓글이 삭제 되었습니다."
		);

	}

	// 대댓글 다건 조회
	@Transactional
	public List<FindAllReplyCommentResponseDto> findChildrenComments(Long parentCommentId) {
		// 부모 댓글을 먼저 조회
		Comment parentComment = commentRepository.findById(parentCommentId)
			.orElseThrow(() -> new CommentNotFoundException());

		// 부모 댓글에 달린 대댓글(답글) 조회
		List<Comment> replies = commentRepository.findByParent(parentComment.getId());

		// 대댓글 엔티티를 FindAllReplyCommentResponseDto로 변환하여 반환
		return replies.stream()
			.map(reply -> new FindAllReplyCommentResponseDto(
				reply.getId(),
				parentComment.getId(),
				reply.getContent(),
				CommentDto.convertDto(reply.getReplies()), // 댓글에 달린 대댓글 구현
				reply.getCreatedAt()
			))
			.collect(Collectors.toList());
	}

	// 댓글 다건 조회
	@Transactional
	public List<FindAllCommentResponseDto> findParentComments(Long postId) {

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());

		List<Comment> parentComments = commentRepository.findByParentId(0L, post);// 부모 댓긅x 그냥 댓글 조회
		// post 1개에 대한 댓글 볼 수 있음

		return parentComments.stream()
			.map(comment -> new FindAllCommentResponseDto(
				comment.getId(),
				comment.getUser().getUserName(),
				comment.getContent(),
				CommentDto.convertDto(comment.getReplies()) // 댓글에 달린 대댓글 구현
			))
			.collect(Collectors.toList());
	}
}

