package com.web.entities;

import javax.persistence.*;

@Entity
@Table(name = "tbl_friends")
public class Friend extends BaseEntity {

	@Column(name = "is_accept", nullable = false)
	private boolean isAccept;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_a")
	private User userAId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_b")
	private User userBId;

	public boolean getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(boolean isAccept) {
		this.isAccept = isAccept;
	}

	public User getUserAId() {
		return userAId;
	}

	public void setUserAId(User userAId) {
		this.userAId = userAId;
	}

	public User getUserBId() {
		return userBId;
	}

	public void setUserBId(User userBId) {
		this.userBId = userBId;
	}


}
