package com.web.Response;

import com.web.entities.User;

public class UserResponse{

    private int id;

    private String name;

    private String avatar;

    private String created;


    public UserResponse(int id, String name, String avatar,String created){
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
