package com.newsfeedproject.controller.category;

import com.newsfeedproject.dto.category.request.CategoryRequestDto;
import com.newsfeedproject.dto.category.request.PostCategoryRequestDto;
import com.newsfeedproject.dto.category.request.UpdateCategoryRequestDto;
import com.newsfeedproject.dto.category.response.*;
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
    public ResponseEntity<CreateCategoryResponseDto> createCategoryAPI(@RequestBody CategoryRequestDto categoryRequestDto) {
        CreateCategoryResponseDto createCategoryResponseDto = categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.ok(createCategoryResponseDto);
    }

    // 포스트를 카테고리에 연결
    @PostMapping("/posts/{postId}/link")
    public ResponseEntity<PostCategoryResponseDto> PostToCategoryAPI(
            @PathVariable Long postId, @RequestBody PostCategoryRequestDto postCategoryRequestDto) {
        PostCategoryResponseDto postCategoryResponseDto = categoryService.postToCategory(postId, postCategoryRequestDto.getCategoryId());
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
    public ResponseEntity<FindCategoryResponseDto> findCategoryByIdApI(@PathVariable Long categoryId) {
        FindCategoryResponseDto findCategoryResponseDto = categoryService.findCategoryById(categoryId);
        return ResponseEntity.ok(findCategoryResponseDto);
    }

    // 카테고리 삭제
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<DeleteCategoryResponseDto> deleteCategoryAPI(@PathVariable Long categoryId) {
        DeleteCategoryResponseDto deleteCategoryResponseDto = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(deleteCategoryResponseDto);
    }

    // 포스트에 연결된 카테고리 변경
    @PatchMapping("/posts/{postId}/link")
    public ResponseEntity<UpdateCategoryResponseDto> updateCategoryAPI(
            @PathVariable Long postId,
            @RequestBody UpdateCategoryRequestDto updateCategoryRequestDto) {
        UpdateCategoryResponseDto udateCategoryresponseDto = categoryService.updateCategory(postId, updateCategoryRequestDto);
        return ResponseEntity.ok(udateCategoryresponseDto);
    }


}
