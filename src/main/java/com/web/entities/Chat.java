package com.web.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_chat")
public class Chat extends BaseEntity {

	@Column(name = "content", length = 500, nullable = false)
	private String content;

	@Column(name = "create_date", nullable = true)
	private LocalDateTime createdDate;

	@Column(name = "unread", nullable = false)
	private boolean unread;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id_sender")
	private User userSender;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id_receiver")
	private User userReceiver;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean getUnread() {
		return unread;
	}

	public void setUnread(boolean unread) {
		this.unread = unread;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public User getUserSender() {
		return userSender;
	}

	public void setUserSender(User userSender) {
		this.userSender = userSender;
	}

	public User getUserReceiver() {
		return userReceiver;
	}

	public void setUserReceiver(User userReceiver) {
		this.userReceiver = userReceiver;
	}

}
