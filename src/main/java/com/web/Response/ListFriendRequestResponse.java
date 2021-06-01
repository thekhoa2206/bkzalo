package com.web.Response;

import java.util.List;

public class ListFriendRequestResponse {
    private List<UserResponse> friends;
    private int total;

    public List<UserResponse> getFriends() {
        return friends;
    }

    public void setFriends(List<UserResponse> friends) {
        this.friends = friends;
    }
}
