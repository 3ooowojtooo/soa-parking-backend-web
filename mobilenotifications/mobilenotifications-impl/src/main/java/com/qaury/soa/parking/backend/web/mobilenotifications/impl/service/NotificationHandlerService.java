package com.qaury.soa.parking.backend.web.mobilenotifications.impl.service;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;
import com.qaury.soa.parking.backend.web.mobilenotifications.api.messages.TicketExpiredMessage;
import com.qaury.soa.parking.backend.web.mobilenotifications.api.messages.TicketNotPurchasedMessage;

import javax.ejb.EJB;
import javax.ejb.Singleton;

@Singleton
public class NotificationHandlerService {

    @EJB(lookup = "java:global/database-impl/ParkingPlaceDAO!com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO")
    private IParkingPlaceRemoteDAO parkingPlaceDAO;

    public void handleTicketExpiredMessage(TicketExpiredMessage message) {
        System.out.println("handleTicketExpiredMessage: " + parkingPlaceDAO.isPlaceTicketExpiredNotificationValid(message.getPlaceId()));
    }

    public void handleTicketNotPurchasedMessage(TicketNotPurchasedMessage message) {
        System.out.println("handleTicketNotPurchasedMessage: " + parkingPlaceDAO.isPlaceTicketNotPurchasedNotificationValid(message.getPlaceId()));
    }
}
