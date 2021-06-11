package com.web.Response;

public class UserResponse{

    private int id;

    private String username;

    private String avatar;

    private String created;


    public UserResponse(int id, String username, String avatar,String created){
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.created = created;
    }

    public UserResponse() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}
