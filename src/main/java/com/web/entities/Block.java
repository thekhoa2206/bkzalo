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
	private User userBlockId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_block_user")
	private User blockUserId;



}
