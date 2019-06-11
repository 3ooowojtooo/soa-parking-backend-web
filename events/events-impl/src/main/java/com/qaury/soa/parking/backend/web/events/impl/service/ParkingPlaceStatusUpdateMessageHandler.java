package com.qaury.soa.parking.backend.web.events.impl.service;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.ParkingPlace;
import com.qaury.soa.parking.backend.web.events.api.messages.ParkingPlaceStatusUpdateMessage;
import com.qaury.soa.parking.backend.web.events.impl.schedule.ScheduleManager;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.Date;

@Singleton
public class ParkingPlaceStatusUpdateMessageHandler {

    private static final int MINUTES_TO_BUY_TICKET_AFTER_PLACE_OCCUPY = 4;

    @EJB(lookup = "java:global/database-impl/ParkingPlaceDAO!com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO")
    private IParkingPlaceRemoteDAO parkingPlaceRemoteDAO;

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
    }

    private void handleSchedulers(ParkingPlaceStatusUpdateMessage message) {
        if (message.getOccupied()) {
            Date timeToBuyTicket = new Date(message.getTimestamp().getTime() + 60 * 1000 * MINUTES_TO_BUY_TICKET_AFTER_PLACE_OCCUPY);
            scheduleManager.addTicketPurchaseTimer(message.getPlaceId(), timeToBuyTicket);
        }
    }

}
