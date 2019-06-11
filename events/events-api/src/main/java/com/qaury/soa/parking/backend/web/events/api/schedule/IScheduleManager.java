package com.qaury.soa.parking.backend.web.events.api.schedule;

import java.util.Date;

public interface IScheduleManager {

    void addTicketPurchaseTimer(int placeId, Date timeToBuyTicket);

    void removeTicketPurchaseTimerIfExists(int placeId);

    void addTicketExpireTimer(int placeId, Date ticketExpirationTime);

    void removeTicketExpireTimerIfExists(int placeId);
}
