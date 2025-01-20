package com.newsfeedproject.common.entity.post;

import com.newsfeedproject.common.entity.BaseEntity;
import com.newsfeedproject.common.entity.category.PostCategory;
import com.newsfeedproject.common.entity.user.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Setter
	@Column(columnDefinition = "TINYINT")
	private boolean isDelete = false;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PostCategory> postCategoryList = new ArrayList<>();

	public Post(String content) {
		this.content = content;
	}

	public void updatePost(String content) {
		this.content = content;
	}
}
