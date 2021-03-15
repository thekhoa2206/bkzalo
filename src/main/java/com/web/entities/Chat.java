package com.web.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;

@Entity
@Table(name = "tbl_chat")
public class Chat extends BaseEntity {
	@Column(name = "content", length = 500, nullable = false)
	private String content;

	@Column(name = "create_date", nullable = true)
	private LocalDateTime createdDate;

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

}
