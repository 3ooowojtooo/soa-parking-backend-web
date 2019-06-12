package com.qaury.soa.parking.backend.web.events.impl.service;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.ITicketRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.ParkingPlace;
import com.qaury.soa.parking.backend.web.database.api.entity.Ticket;
import com.qaury.soa.parking.backend.web.events.api.messages.TicketPurchaseMessage;
import com.qaury.soa.parking.backend.web.events.impl.schedule.ScheduleManager;

import javax.ejb.EJB;
import javax.ejb.Singleton;

@Singleton
public class TicketPurchaseMessageHandler {

    @EJB(lookup = "java:global/database-impl/TicketDAO!com.qaury.soa.parking.backend.web.database.api.dao.ITicketRemoteDAO")
    private ITicketRemoteDAO ticketRemoteDAO;

    @EJB(lookup = "java:global/database-impl/ParkingPlaceDAO!com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO")
    private IParkingPlaceRemoteDAO parkingPlaceRemoteDAO;

    @EJB
    private ScheduleManager scheduleManager;

    public void handle(TicketPurchaseMessage message) {
        if (placeExists(message.getPlaceId())) {
            addTicketToDatabase(message);
            handleSchedulers(message);
        }
    }

    private boolean placeExists(int placeId) {
        return parkingPlaceRemoteDAO.find(placeId) != null;
    }

    private void addTicketToDatabase(TicketPurchaseMessage message) {
        ParkingPlace parkingPlace = parkingPlaceRemoteDAO.find(message.getPlaceId());
        Ticket ticket = new Ticket(message.getTimestampFrom(), message.getTimestampTo());
        parkingPlace.addTicket(ticket);
        ticketRemoteDAO.persist(ticket);
        parkingPlaceRemoteDAO.setPlaceTicketNotPurchased(message.getPlaceId(), false);
        parkingPlaceRemoteDAO.setPlaceTicketExpired(message.getPlaceId(), false);
    }

    private void handleSchedulers(TicketPurchaseMessage message) {
        scheduleManager.removeTicketPurchaseTimerIfExists(message.getPlaceId());
        scheduleManager.addTicketExpireTimer(message.getPlaceId(), message.getTimestampTo());
    }

}
