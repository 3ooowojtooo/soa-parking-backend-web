package com.qaury.soa.parking.backend.web.database.api.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "controllers")
public class Controller implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "controller", cascade = CascadeType.ALL)
    private Zone zone;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_id")
    private Auth auth;

    public Controller() {

    }

    public Controller(Integer id, String authId) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}