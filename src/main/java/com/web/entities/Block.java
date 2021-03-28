package com.web.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_blocks")
public class Block extends BaseEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_block")
	private User id_user_block;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_block_user")
	private User id_block_user;

	public User getId_user_block() {
		return id_user_block;
	}

	public void setId_user_block(User id_user_block) {
		this.id_user_block = id_user_block;
	}

	public User getId_block_user() {
		return id_block_user;
	}

	public void setId_block_user(User id_block_user) {
		this.id_block_user = id_block_user;
	}

}
