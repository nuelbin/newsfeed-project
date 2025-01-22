package com.newsfeedproject.controller.category;

import com.newsfeedproject.dto.category.request.CategoryRequestDto;
import com.newsfeedproject.dto.category.request.PostCategoryRequestDto;
import com.newsfeedproject.dto.category.response.CategoryResponseDto;
import com.newsfeedproject.dto.category.response.PostCategoryResponseDto;
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
    public ResponseEntity<CategoryResponseDto> createCategoryAPI(@RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.ok(categoryResponseDto);
    }

    // 포스트를 카테고리에 연결
    @PostMapping("/posts/{postId}/link")
    public ResponseEntity<PostCategoryResponseDto> PostToCategoryAPI(
            @PathVariable Long postId, @RequestBody PostCategoryRequestDto postCategoryRequestDto) {
        PostCategoryResponseDto postCategoryResponseDto = categoryService.PostToCategory(postId, postCategoryRequestDto);
        return ResponseEntity.ok(postCategoryResponseDto);
    }

    // 카테고리 다건 조회
    @GetMapping
    public ResponseEntity<List<PostCategoryResponseDto>> findAllCategoryAPI() {
        List<PostCategoryResponseDto> postCategoryResponseDtoList = categoryService.findAllCategory();
        return ResponseEntity.ok(postCategoryResponseDtoList);
    }

    // 카테고리 단건 조회
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> findCategoryByIdApI(@PathVariable Long categoryId) {
        CategoryResponseDto categoryResponseDto = categoryService.findCategoryById(categoryId);
        return ResponseEntity.ok(categoryResponseDto);
    }

}
