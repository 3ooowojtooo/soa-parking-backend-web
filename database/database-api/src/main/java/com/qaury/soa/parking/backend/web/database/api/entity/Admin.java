package com.qaury.soa.parking.backend.web.database.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "auth_id", nullable = false, unique = true)
    private String authId;

    public Admin() {
    }

    public Admin(Integer id, String authId) {
        this.id = id;
        this.authId = authId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }
}
