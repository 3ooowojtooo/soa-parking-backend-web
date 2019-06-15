package com.qaury.soa.parking.backend.web.events.api.messages;

import java.io.Serializable;
import java.util.Date;

public class TicketPurchaseMessage implements Serializable {

    private Integer placeId;
    private Date timestamp;
    private Date timestampFrom;
    private Date timestampTo;

    public TicketPurchaseMessage() {
    }

    public TicketPurchaseMessage(Integer placeId, Date timmestamp, Date timestampFrom, Date timestampTo) {
        this.placeId = placeId;
        this.timestamp = timmestamp;
        this.timestampFrom = timestampFrom;
        this.timestampTo = timestampTo;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public Date getTimmestamp() {
        return timestamp;
    }

    public Date getTimestampFrom() {
        return timestampFrom;
    }

    public Date getTimestampTo() {
        return timestampTo;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestampFrom(Date timestampFrom) {
        this.timestampFrom = timestampFrom;
    }

    public void setTimestampTo(Date timestampTo) {
        this.timestampTo = timestampTo;
    }
}
