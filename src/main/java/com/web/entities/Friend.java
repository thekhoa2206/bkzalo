package com.web.entities;

import javax.persistence.*;

@Entity
@Table(name = "tbl_friends")
public class Friend extends BaseEntity {

	@Column(name = "is_accept", nullable = false)
	private boolean isAccept;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_a")
	private User id_user_a;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_b")
	private User id_user_b;

	public boolean getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(boolean isAccept) {
		this.isAccept = isAccept;
	}

	public User getId_user_a() {
		return id_user_a;
	}

	public void setId_user_a(User id_user_a) {
		this.id_user_a = id_user_a;
	}

	public User getId_user_b() {
		return id_user_b;
	}

	public void setId_user_b(User id_user_b) {
		this.id_user_b = id_user_b;
	}

	public void setAccept(boolean isAccept) {
		this.isAccept = isAccept;
	}


}
