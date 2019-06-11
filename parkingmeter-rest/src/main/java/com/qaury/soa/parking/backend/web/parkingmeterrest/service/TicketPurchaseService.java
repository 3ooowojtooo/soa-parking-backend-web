package com.qaury.soa.parking.backend.web.parkingmeterrest.service;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.ITicketRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.ParkingPlace;
import com.qaury.soa.parking.backend.web.database.api.entity.Ticket;
import com.qaury.soa.parking.backend.web.events.api.TicketPurchaseMessage;
import com.qaury.soa.parking.backend.web.events.api.schedule.IScheduleManagerRemote;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import java.util.Date;

@ApplicationScoped
public class TicketPurchaseService {

    @EJB(lookup = "java:global/database-impl/TicketDAO!com.qaury.soa.parking.backend.web.database.api.dao.ITicketRemoteDAO")
    private ITicketRemoteDAO ticketRemoteDAO;

    @EJB(lookup = "java:global/database-impl/ParkingPlaceDAO!com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO")
    private IParkingPlaceRemoteDAO parkingPlaceRemoteDAO;

    @EJB(lookup = "java:global/events-impl/ScheduleManager!com.qaury.soa.parking.backend.web.events.api.schedule.IScheduleManagerRemote")
    private IScheduleManagerRemote scheduleManager;

    @Resource(name = "java:global/jms/queue/SOAParkingEventsQueue")
    private Queue eventsQueue;

    private JMSContext jmsContext;

    public void handleTicketPurchase(int placeId, long timestamp, long timestampFrom, long timestampTo) {
        if (placeExists(placeId)) {
            addTicketToDatabase(placeId, timestamp, timestampFrom, timestampTo);
            handleSchedulers(placeId, timestamp, timestampFrom, timestampTo);
            sendEventNotification(placeId, timestamp, timestampFrom, timestampTo);
        }
    }

    @Inject
    public void setJmsContext(JMSContext jmsContext) {
        this.jmsContext = jmsContext;
    }

    private boolean placeExists(int placeId) {
        return parkingPlaceRemoteDAO.find(placeId) != null;
    }

    private void addTicketToDatabase(int placeId, long timestamp, long timestampFrom, long timestampTo) {
        ParkingPlace parkingPlace = parkingPlaceRemoteDAO.find(placeId);
        Date timestampDate = new Date(timestamp);
        Date timestampFromDate = new Date(timestampFrom);
        Date timestampToDate = new Date(timestampTo);
        Ticket ticket = new Ticket(timestampFromDate, timestampToDate);
        parkingPlace.addTicket(ticket);
        ticketRemoteDAO.persist(ticket);
    }

    private void handleSchedulers(int placeId, long timestamp, long timestampFrom, long timestampTo) {
        scheduleManager.addTicketExpireTimer(placeId, new Date(timestampTo));
    }

    private void sendEventNotification(int placeId, long timestamp, long timestampFrom, long timestampTo) {
        TicketPurchaseMessage ticketPurchaseMessage = new TicketPurchaseMessage(placeId, new Date(timestamp), new Date(timestampFrom), new Date(timestampTo));
        ObjectMessage objectMessage = jmsContext.createObjectMessage(ticketPurchaseMessage);
        jmsContext.createProducer().send(eventsQueue, objectMessage);
    }
}
