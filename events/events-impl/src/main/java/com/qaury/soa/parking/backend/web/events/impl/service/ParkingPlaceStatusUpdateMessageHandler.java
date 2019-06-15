package com.qaury.soa.parking.backend.web.events.impl.service;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.ITicketRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.ParkingPlace;
import com.qaury.soa.parking.backend.web.events.api.messages.ParkingPlaceStatusUpdateMessage;
import com.qaury.soa.parking.backend.web.events.impl.schedule.ScheduleManager;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.Date;

@Singleton
public class ParkingPlaceStatusUpdateMessageHandler {

    private static final int SECONDS_TO_BUY_TICKET_AFTER_OCCUPY = 4;

    @EJB(lookup = "java:global/database-impl/ParkingPlaceDAO!com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO")
    private IParkingPlaceRemoteDAO parkingPlaceRemoteDAO;

    @EJB(lookup = "java:global/database-impl/TicketDAO!com.qaury.soa.parking.backend.web.database.api.dao.ITicketRemoteDAO")
    private ITicketRemoteDAO ticketDAO;

    @EJB
    private ScheduleManager scheduleManager;

    public void handle(ParkingPlaceStatusUpdateMessage message) {
        if (parkingPlaceExists(message.getPlaceId())) {
            updateParkingPlaceStatusInDatabase(message);
            handleSchedulers(message);
        }
    }

    private boolean parkingPlaceExists(int placeId) {
        return parkingPlaceRemoteDAO.find(placeId) != null;
    }

    private void updateParkingPlaceStatusInDatabase(ParkingPlaceStatusUpdateMessage message) {
        ParkingPlace parkingPlace = parkingPlaceRemoteDAO.find(message.getPlaceId());
        parkingPlace.setOccupied(message.getOccupied());
        parkingPlaceRemoteDAO.edit(parkingPlace);
        if (!message.getOccupied()) {
            ticketDAO.deleteAllTicketsForPlaceId(message.getPlaceId());
            parkingPlaceRemoteDAO.setPlaceTicketExpired(message.getPlaceId(), false);
            parkingPlaceRemoteDAO.setPlaceTicketNotPurchased(message.getPlaceId(), false);
        }
    }

    private void handleSchedulers(ParkingPlaceStatusUpdateMessage message) {
        if (message.getOccupied()) {
            Date timeToBuyTicket = new Date(message.getTimestamp().getTime() + 1000 * SECONDS_TO_BUY_TICKET_AFTER_OCCUPY);
            scheduleManager.addTicketPurchaseTimer(message.getPlaceId(), timeToBuyTicket);
        } else {
            scheduleManager.removeTicketPurchaseTimerIfExists(message.getPlaceId());
            scheduleManager.removeTicketExpireTimerIfExists(message.getPlaceId());
        }
    }

}
