package com.web.Response;

public class GetListFriendResponse extends BaseResponse{
    private ListFriendsResponse data;

    public ListFriendsResponse getData() {
        return data;
    }

    public void setData(ListFriendsResponse data) {
        this.data = data;
    }
}
