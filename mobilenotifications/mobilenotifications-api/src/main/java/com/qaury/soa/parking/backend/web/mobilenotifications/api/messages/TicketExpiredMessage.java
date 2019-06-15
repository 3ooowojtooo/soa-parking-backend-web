package com.qaury.soa.parking.backend.web.mobilenotifications.api.messages;

import java.io.Serializable;

public class TicketExpiredMessage implements Serializable {

    private int controllerId;
    private int placeId;

    public TicketExpiredMessage() {
    }

    public TicketExpiredMessage(int controllerId, int placeId) {
        this.controllerId = controllerId;
        this.placeId = placeId;
    }

    public int getControllerId() {
        return controllerId;
    }

    public void setControllerId(int controllerId) {
        this.controllerId = controllerId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }
}
