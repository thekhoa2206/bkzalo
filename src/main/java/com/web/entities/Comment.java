package com.web.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_comment")
public class Comment extends BaseEntity {

	@Column(name = "content", length = 500, nullable = false)
	private String content;

	@Column(name = "create_date", nullable = true)
	private LocalDateTime createdDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id") // tên field khoá ngoại
	private User user;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "tbl_comment_post", joinColumns = @JoinColumn(name = "id_comment"), inverseJoinColumns = @JoinColumn(name = "id_post"))
	private List<Post> post = new ArrayList<Post>();

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Post> getPost() {
		return post;
	}

	public void setPost(List<Post> post) {
		this.post = post;
	}

}
