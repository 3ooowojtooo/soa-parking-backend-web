package com.qaury.soa.parking.backend.web.events.api;

import java.io.Serializable;
import java.util.Date;

public class ParkingPlaceStatusUpdateMessage implements Serializable {

    private Integer placeId;
    private Date timestamp;
    private Boolean occupied;

    public ParkingPlaceStatusUpdateMessage() {
    }

    public ParkingPlaceStatusUpdateMessage(Integer placeId, Date timestamp, Boolean occupied) {
        this.placeId = placeId;
        this.timestamp = timestamp;
        this.occupied = occupied;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }
}
