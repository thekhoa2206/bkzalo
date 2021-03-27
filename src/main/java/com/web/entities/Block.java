package com.web.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_blocks")
public class Block extends BaseEntity {
//	@Column(name = "id_user_block", length = 100, nullable = false)
//	private Integer id_user_block;
//
//	@Column(name = "id_block_user", length = 100, nullable = false)
//	private Integer id_block_user;
//
//	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_block")
	private User userBlock;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_block_user")
	private User blockUser;

	public User getUserBlock() {
		return userBlock;
	}

	public void setUserBlock(User userBlock) {
		this.userBlock = userBlock;
	}

	public User getBlockUser() {
		return blockUser;
	}

	public void setBlockUser(User blockUser) {
		this.blockUser = blockUser;
	}
	

}
