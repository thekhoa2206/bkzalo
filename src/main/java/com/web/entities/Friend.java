package com.web.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_friends")
public class Friend extends BaseEntity{

    @Column(name = "id_user_a", length = 100, nullable = false)
    private Integer id_user_a;

    @Column(name = "id_user_b", length = 100, nullable = false)
    private Integer id_user_b;

    @Column(name = "is_accept", length = 1, nullable = false)
    private Integer is_accept;


    public Integer getId_user_a() {
        return id_user_a;
    }

    public void setId_user_a(Integer id_user_a) {
        this.id_user_a = id_user_a;
    }

    public Integer getId_user_b() {
        return id_user_b;
    }

    public void setId_user_b(Integer id_user_b) {
        this.id_user_b = id_user_b;
    }

    public Integer getIs_accept() {
        return is_accept;
    }

    public void setIs_accept(Integer is_accept) {
        this.is_accept = is_accept;
    }

}
