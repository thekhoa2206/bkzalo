package com.web.Response;

public class GetRequestFriendResponse extends BaseResponse{
    private RequestFriendResponse data;

    public RequestFriendResponse getData() {
        return data;
    }

    public void setData(RequestFriendResponse data) {
        this.data = data;
    }
}
