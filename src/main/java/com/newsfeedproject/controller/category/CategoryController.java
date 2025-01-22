package com.newsfeedproject.controller.category;

import com.newsfeedproject.dto.category.request.CategoryRequestDto;
import com.newsfeedproject.dto.category.request.PostCategoryRequestDto;
import com.newsfeedproject.dto.category.response.CategoryResponseDto;
import com.newsfeedproject.dto.category.response.PostCategoryResponseDto;
import com.newsfeedproject.dto.post.response.FindPostResponseDto;
import com.newsfeedproject.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 생성
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.ok(categoryResponseDto);
    }

    // 포스트를 카테고리에 연결
    @PostMapping("/posts/{postId}/link")
    public ResponseEntity<PostCategoryResponseDto> PostToCategory(
            @PathVariable Long postId, @RequestBody PostCategoryRequestDto postCategoryRequestDto) {
        PostCategoryResponseDto response = categoryService.PostToCategory(postId, postCategoryRequestDto);
        return ResponseEntity.ok(response);
    }

    // 카테고리 다건 조회
    @GetMapping
    public ResponseEntity<List<PostCategoryResponseDto>> findAllCategory() {
        List<PostCategoryResponseDto> response = categoryService.findAllCategory();
        return ResponseEntity.ok(response);
    }

    // 카테고리 단건 조회

}
