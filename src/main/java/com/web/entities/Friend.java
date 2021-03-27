package com.web.entities;

import javax.persistence.*;

@Entity
@Table(name = "tbl_friends")
public class Friend extends BaseEntity {

	@Column(name = "is_accept", nullable = false)
	private boolean isAccept;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_a")
	private User userA;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_b")
	private User userB;

	public boolean getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(boolean isAccept) {
		this.isAccept = isAccept;
	}

	public User getUserA() {
		return userA;
	}

	public void setUserA(User userA) {
		this.userA = userA;
	}

	public User getUserB() {
		return userB;
	}

	public void setUserB(User userB) {
		this.userB = userB;
	}
}
