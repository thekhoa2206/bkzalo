package com.web.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_report")
public class Report extends BaseEntity {
	
	@Column(name = "subject", length = 1000, nullable = true)
	private String subject;
	
	@Column(name = "details", length = 1000, nullable = true)
	private String details;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_post")
	private Post post;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user")
	private User user;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
