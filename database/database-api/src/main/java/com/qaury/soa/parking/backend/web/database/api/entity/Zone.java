package com.qaury.soa.parking.backend.web.database.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "zones")
public class Zone implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "controller_id")
    private Controller controller;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "zone", cascade = CascadeType.ALL)
    private Set<ParkingPlace> parkingPlaceList;

    public Zone() {
    }

    public Zone(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        if (controller == null) {
            if (this.controller != null) {
                this.controller.setZone(null);
            }
        } else {
            controller.setZone(this);
        }
        this.controller = controller;
    }

    public void addParkingPlace(ParkingPlace parkingPlace) {
        if (parkingPlace != null && !parkingPlaceList.contains(parkingPlace)) {
            this.parkingPlaceList.add(parkingPlace);
            parkingPlace.setZone(this);
        }
    }

    public void removeParkingPlace(ParkingPlace parkingPlace) {
        if (parkingPlace != null && parkingPlaceList.contains(parkingPlace)) {
            this.parkingPlaceList.remove(parkingPlace);
            parkingPlace.setZone(null);
        }
    }

    public Set<ParkingPlace> getParkingPlaceList() {
        return parkingPlaceList;
    }

    public void setParkingPlaceList(Set<ParkingPlace> parkingPlaceList) {
        this.parkingPlaceList = parkingPlaceList;
    }
}
