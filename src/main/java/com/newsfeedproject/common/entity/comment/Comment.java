package com.newsfeedproject.common.entity.comment;

import java.util.ArrayList;
import java.util.List;

import com.newsfeedproject.common.entity.BaseEntity;
import com.newsfeedproject.common.entity.post.Post;
import com.newsfeedproject.common.entity.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 추가!
@Table(name = "Comment")
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY) // LAZY로 설정해야 합니다. 왜? 필요할 때에만 얻어와야 하기 때문이다.
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)//  LAZY로 설정해야 합니다. 왜? 필요할 때에만 얻어와야 하기 때문이다.
	@JoinColumn(name = "post_id")
	private Post post;

	@Column(name = "contnet")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_comment_id")
	private Comment parent; // 부모 댓글 엔티티와의 관계 설정

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private List<Comment> replies = new ArrayList<>();  // 대댓글 목록

	public void addReplie(Comment replie) {
		replies.add(replie); // 대댓글 추가
	}

	public void updateContent(String requestDto) {
		this.content = getContent();
	}

	public Comment(String content, User user, Post post, Comment parent) {
		this.content = content;
		this.user = user;
		this.post = post;
		this.parent = parent;
	}

	public Comment(String content, User user, Post post) {
		this.content = content;
		this.user = user;
		this.post = post;
	}

}
