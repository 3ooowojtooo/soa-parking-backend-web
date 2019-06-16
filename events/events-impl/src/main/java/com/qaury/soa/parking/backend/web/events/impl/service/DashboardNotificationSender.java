package com.qaury.soa.parking.backend.web.events.impl.service;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.ParkingPlace;
import com.qaury.soa.parking.backend.web.database.api.entity.Ticket;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.TextMessage;
import java.util.Set;

@Singleton
public class DashboardNotificationSender {

    @EJB(lookup = "java:global/database-impl/ParkingPlaceDAO!com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO")
    private IParkingPlaceRemoteDAO parkingPlaceRemoteDAO;

    @Inject
    JMSContext jmsContext;

    @Resource(name = "java:global/jms/SOAParkingDashboardQueue")
    Queue dashboardQueue;

    public void sendDashboardParkingPlaceUpdate(int parkingPlaceId) {
        parkingPlaceRemoteDAO.flush();
        ParkingPlace parkingPlace = parkingPlaceRemoteDAO.find(parkingPlaceId);
        if (parkingPlace != null) {
            TextMessage textMessage = jmsContext.createTextMessage(prepareStringParkingPlaceDescription(parkingPlace));
            jmsContext.createProducer().send(dashboardQueue, textMessage);
        }
    }

    private String prepareStringParkingPlaceDescription(ParkingPlace parkingPlace) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("zoneId", parkingPlace.getZone().getId());
        jsonObject.put("placeId", parkingPlace.getId());
        jsonObject.put("occupied", parkingPlace.getOccupied());
        jsonObject.put("notPurchasedNotification", parkingPlace.getNotPurchasedNotification());
        jsonObject.put("ticketExpireNotification", parkingPlace.getTicketExpireNotification());
        jsonObject.put("tickets", prepareTicketsJsonArray(parkingPlace.getTicketList()));
        return jsonObject.toString();
    }

    private JSONArray prepareTicketsJsonArray(Set<Ticket> tickets) {
        JSONArray jsonArray = new JSONArray();
        for (Ticket ticket : tickets) {
            JSONObject ticketObject = new JSONObject();
            ticketObject.put("ticketId", ticket.getId());
            ticketObject.put("dateFrom", ticket.getDateFrom());
            ticketObject.put("dateTo", ticket.getDateTo());
            jsonArray.put(ticketObject);
        }
        return jsonArray;
    }
}