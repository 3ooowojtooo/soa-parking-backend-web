package com.qaury.soa.parking.backend.web.database.api.entity;

import javax.persistence.*;

@Entity
@Table(name = "controllers")
public class Controller {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "auth_id", nullable = false, unique = true)
    private String authId;

    @OneToOne(mappedBy = "controller", cascade = CascadeType.ALL)
    private Zone zone;

    public Controller() {

    }

    public Controller(Integer id, String authId) {
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

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}