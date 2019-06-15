package com.qaury.soa.parking.backend.web.events.api.schedule;

public interface IScheduleHandler {

    void handleTicketPurchaseTimeout(Integer placeId);

    void handleTicketExpireTimeout(Integer placeId);
}
