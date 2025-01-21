package com.newsfeedproject.service.category;

import com.newsfeedproject.common.entity.category.Category;
import com.newsfeedproject.dto.category.request.CategoryRequestDto;
import com.newsfeedproject.dto.category.response.CategoryResponseDto;
import com.newsfeedproject.repository.category.CategoryRepository;
import com.newsfeedproject.repository.category.PostCategoryRepository;
import com.newsfeedproject.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;

    // 카테고리 생성
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = new Category(categoryRequestDto.getCategoryName());
        Category saveCategory = categoryRepository.save(category);
        return new CategoryResponseDto("카테고리가 생성되었습니다.");
    }
}

