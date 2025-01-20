package com.newsfeedproject.repository.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.newsfeedproject.common.entity.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	// JPQL을 써야될 것 같다. ()@Query + 부모 댓글 Id를 통해 대댓글 목록 조회
	@Query("SELECT c FROM Comment c where c.id = :commentId ")
	List<Comment> findByParent(@Param("commentId") Long parentCommentId);
}

