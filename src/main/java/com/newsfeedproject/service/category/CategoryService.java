package com.newsfeedproject.service.category;

import com.newsfeedproject.common.entity.category.Category;
import com.newsfeedproject.common.entity.category.PostCategory;
import com.newsfeedproject.common.entity.post.Post;
import com.newsfeedproject.common.entity.user.User;
import com.newsfeedproject.common.exception.post.DeletePostException;
import com.newsfeedproject.dto.category.request.CategoryRequestDto;
import com.newsfeedproject.dto.category.request.PostCategoryRequestDto;
import com.newsfeedproject.dto.category.response.CategoryResponseDto;
import com.newsfeedproject.dto.category.response.PostCategoryResponseDto;
import com.newsfeedproject.dto.post.request.CreatePostRequestDto;
import com.newsfeedproject.dto.post.response.CreatePostResponseDto;
import com.newsfeedproject.dto.post.response.FindPostResponseDto;
import com.newsfeedproject.repository.category.CategoryRepository;
import com.newsfeedproject.repository.category.PostCategoryRepository;
import com.newsfeedproject.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;

    // 카테고리 생성
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {

        // 카테고리 엔티티 생성 후 데이터베이스에 저장
        Category category = new Category(categoryRequestDto.getCategoryName());
        Category saveCategory = categoryRepository.save(category);

        return new CategoryResponseDto("카테고리가 생성되었습니다.");
    }

    // 포스트를 카테고리에 연결
    @Transactional
    public PostCategoryResponseDto PostToCategory(Long postId, PostCategoryRequestDto requestDto) {
        // 포스트를 조회 -> 없으면 예외 발생
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("포스트가 없습니다."));

        // 카테고리를 조회 -> 없으면 예외 발생
        Category category = categoryRepository.findByCategoryName(requestDto.getCategoryName())
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 없습니다."));

        // 엔티티 생성 및 저장
        PostCategory postCategory = new PostCategory(post, category);
        postCategoryRepository.save(postCategory);

        // 연결된 포스트를 DTO로 변환
        List<FindPostResponseDto> posts = new ArrayList<>();
         //연결된 모든 PostCategory 가져오기
        for (PostCategory postCategory1 : category.getPostCategoryList()) {
            //연결된 Post 가져와서
            Post connectedPost = postCategory1.getPost();
            //dto로 변환 후 추가
            posts.add(new FindPostResponseDto(connectedPost));
        }

        return new PostCategoryResponseDto(category.getCategoryName(), posts);
    }


    // 다건 조회

    // 단건 조회
}