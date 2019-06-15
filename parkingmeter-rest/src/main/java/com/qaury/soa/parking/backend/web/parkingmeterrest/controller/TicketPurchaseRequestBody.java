package com.qaury.soa.parking.backend.web.parkingmeterrest.controller;

public class TicketPurchaseRequestBody {

    private Integer placeId;
    private long timestamp;
    private long timestampFrom;
    private long timestampTo;

    public TicketPurchaseRequestBody() {
    }

    public TicketPurchaseRequestBody(Integer placeId, long timestamp, long timestampFrom, long timestampTo) {
        this.placeId = placeId;
        this.timestamp = timestamp;
        this.timestampFrom = timestampFrom;
        this.timestampTo = timestampTo;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestampFrom() {
        return timestampFrom;
    }

    public void setTimestampFrom(long timestampFrom) {
        this.timestampFrom = timestampFrom;
    }

    public long getTimestampTo() {
        return timestampTo;
    }

    public void setTimestampTo(long timestampTo) {
        this.timestampTo = timestampTo;
    }
}
