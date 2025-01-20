package com.newsfeedproject.repository.category;

import com.newsfeedproject.common.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
