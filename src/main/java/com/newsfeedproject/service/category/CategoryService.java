package com.newsfeedproject.service.category;

import com.newsfeedproject.common.entity.category.Category;
import com.newsfeedproject.common.entity.category.PostCategory;
import com.newsfeedproject.common.entity.post.Post;
import com.newsfeedproject.dto.category.request.CategoryRequestDto;
import com.newsfeedproject.dto.category.request.PostCategoryRequestDto;
import com.newsfeedproject.dto.category.response.CreateCategoryResponseDto;
import com.newsfeedproject.dto.category.response.DeleteCategoryResponseDto;
import com.newsfeedproject.dto.category.response.FindCategoryResponseDto;
import com.newsfeedproject.dto.category.response.PostCategoryResponseDto;
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
    public CreateCategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {

        // 카테고리 엔티티 생성 후 데이터베이스에 저장
        Category category = new Category(categoryRequestDto.getCategoryName());
        Category saveCategory = categoryRepository.save(category);

        return new CreateCategoryResponseDto("카테고리가 생성되었습니다.", category.getCategoryName());
    }


    // 포스트를 카테고리에 연결
    @Transactional
    public PostCategoryResponseDto postToCategory(Long postId, PostCategoryRequestDto postCategoryRequestDto) {
        // 포스트를 조회 -> 없으면 예외 발생
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("포스트가 없습니다."));

        // 카테고리를 조회 -> 없으면 예외 발생
        Category category = categoryRepository.findByCategoryName(postCategoryRequestDto.getCategoryName())
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 없습니다."));

        // 엔티티 생성 및 저장
        PostCategory postCategory = new PostCategory(post, category);
        postCategoryRepository.save(postCategory);

        // 연결된 PostCategory 가져오고
        List<PostCategory> postCategoryList = postCategoryRepository.findByCategory(category);
        // 반환할 dto 생성하기
        List<FindPostResponseDto> posts = new ArrayList<>();

        // 연결된 모든 PostCategory 가져오기
        for (PostCategory postCategory1 : postCategoryList) {
            // 연결된 Post 가져와서
            Post connectedPost = postCategory1.getPost();
            // dto로 변환 후 추가
            posts.add(new FindPostResponseDto(connectedPost));
        }

        return new PostCategoryResponseDto(category.getCategoryName(), posts);
    }


    // 카테고리 다건 조회(전체)
    public List<PostCategoryResponseDto> findAllCategory() {
        // 모든 카테고리를 조회
        List<Category> findAllCategory = categoryRepository.findAll();
        List<PostCategoryResponseDto> postCategoryResponseList = new ArrayList<>();

        // 카테고리 가져오기 -> 포스트
        for (Category category : findAllCategory) {
            List<PostCategory> postCategoryList = postCategoryRepository.findByCategory(category);

            // 연결된 포스트 -> findPostResponseList 생성
            List<FindPostResponseDto> findPostResponseList = new ArrayList<>();

            // 카테고리에 연결된 포스트 리스트 가져오기
            for (PostCategory postCategory : postCategoryList) {
                // 연결된 Post를 dto로 변환하고 리스트에 추가
                Post connectedPost = postCategory.getPost();
                findPostResponseList.add(new FindPostResponseDto(connectedPost));
            }

            // 카테고리와 포스트를 dto 변환하고 전체 리스트에 추가
            PostCategoryResponseDto postCategoryResponseDto = new PostCategoryResponseDto(category.getCategoryName(), findPostResponseList);
            postCategoryResponseList.add(postCategoryResponseDto);
        }

        return postCategoryResponseList;
    }


    // 카테고리 단건 조회
    public FindCategoryResponseDto findCategoryById(Long categoryId) {
        // 카테고리를 조회 -> 없으면 예외 발생
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 없습니다."));

        return new FindCategoryResponseDto(category.getCategoryName());
    }

    // 카테고리 삭제
    @Transactional
    public DeleteCategoryResponseDto deleteCategory(Long categoryId) {
        // 카테고리 조회 -> 없으면 예외 발생
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 없습니다."));

        categoryRepository.delete(category);

        return new DeleteCategoryResponseDto("카테고리가 삭제되었습니다.");
    }


//    // 포스트에 연결된 카테고리 변경
//    @Transactional
//    public CategoryResponseDto updatePostCategory(Long postId, PostCategoryRequestDto requestDto) {
//        // 포스트 조회 -> 없으면 예외 발생
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new IllegalArgumentException("포스트가 없습니다."));
//
//        // 새로운 카테고리 조회 -> 없으면 예외 발생
//        Category newCategory = categoryRepository.findByCategoryName(requestDto.getCategoryName())
//                .orElseThrow(() -> new IllegalArgumentException("카테고리가 없습니다."));
//
//
//    }



}


