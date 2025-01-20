package com.newsfeedproject.service.post;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newsfeedproject.common.entity.post.Post;
import com.newsfeedproject.common.entity.user.User;
import com.newsfeedproject.common.exception.post.DeletePostException;
import com.newsfeedproject.common.exception.post.MissMatchIdException;
import com.newsfeedproject.common.exception.post.UserNotFoundException;
import com.newsfeedproject.dto.post.request.CreatePostRequestDto;
import com.newsfeedproject.dto.post.request.UpdatePostRequestDto;
import com.newsfeedproject.dto.post.response.CreatePostResponseDto;
import com.newsfeedproject.dto.post.response.DeletePostResponseDto;
import com.newsfeedproject.dto.post.response.FindPostResponseDto;
import com.newsfeedproject.dto.post.response.UpdatePostResponseDto;
import com.newsfeedproject.repository.post.PostRepository;
import com.newsfeedproject.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;

	//게시물 생성 기능
	@Transactional
	public CreatePostResponseDto createPost(Long userId, CreatePostRequestDto createPostRequestdto) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저 아이디를 찾을수 없습니다."));
		Post post = new Post(user, createPostRequestdto.getContent());
		Post savePost = postRepository.save(post);
		return new CreatePostResponseDto(user.getUserName(), "게시글이 생성되었습니다.",savePost);
	}

	//게시물 조회 기능
	public FindPostResponseDto findPostById(Long id) {
		Post findPost = postRepository.findPostById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

		//삭제된 게시물을 조회하면 던지는 예외
		if (findPost.getIsDelete()) {
			throw new DeletePostException();
		}

		return new FindPostResponseDto(findPost); //게시글 생성과 통일하게 코드를 변경할것.
	}

	//게시물 전체 조회 기능
	public List<FindPostResponseDto> findAllPost() {
		List<Post> findAllPost = postRepository.findAll();
		List<FindPostResponseDto> findPostList = new ArrayList<>();

		for (Post post : findAllPost) {
			FindPostResponseDto findpostResponseDto = new FindPostResponseDto(post);
			findPostList.add(findpostResponseDto);
		}

		return findPostList;
	}

	//게시물 수정 기능
	@Transactional
	public UpdatePostResponseDto updatePost(Long userId, Long postId, UpdatePostRequestDto updatePostRequestDto) {

		User sessionUser = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());
		Post savePost = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("수정할 게시글을 찾을 수 없습니다."));

		// 글 작성자가 아닌 다른 유저가 글을 수정하려 할 시
		if (sessionUser != savePost.getUser()) {
			throw new MissMatchIdException();
		}

		// 이미 삭제된 게시글에 수정 요청이 들어올 시
		if (savePost.getIsDelete()) {
			throw new DeletePostException();
		}

		savePost.updatePost(updatePostRequestDto.getContents());
		return new UpdatePostResponseDto("게시글이 수정되었습니다.", savePost);
	}

	//게시글 삭제 기능
	@Transactional
	public DeletePostResponseDto deletePost(Long userId, Long postId) {

		User sessionUser = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());
		Post savePost = postRepository.findPostById(postId)
			.orElseThrow(() -> new IllegalArgumentException("삭제 할 게시글을 찾을 수 없습니다."));

		// 글 작성자가 아닌 다른 유저가 글을 수정하려 할 시
		if (sessionUser != savePost.getUser()) {
			throw new MissMatchIdException();
		}

		//이미 삭제된 게시글에 삭제 요청이 들어올시
		if (savePost.getIsDelete()) {
			throw new DeletePostException();
		}

		//게시글 삭제 요청 시 포스트의 삭제상태를 true 로 변경
		savePost.updateIsDelete(true);
		postRepository.save(savePost);
		return new DeletePostResponseDto("게시글이 삭제되었습니다.", savePost);
	}
}
