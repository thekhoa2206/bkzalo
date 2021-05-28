package com.web.Response;

import com.web.entities.Friend;
import com.web.entities.User;

import java.util.List;

public class ListFriendsResponse {
    private List<UserResponse> friends;
    private int total;

    public List<UserResponse> getFriends() {
        return friends;
    }

    public void setFriends(List<UserResponse> friends) {
        this.friends = friends;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
