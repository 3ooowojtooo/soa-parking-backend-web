package com.qaury.soa.parking.backend.web.parkingplacessoap.service;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.ParkingPlace;
import com.qaury.soa.parking.backend.web.events.api.ParkingPlaceStatusUpdateMessage;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import java.util.Date;

@ApplicationScoped
public class ChangeParkingPlaceStatusService {

    @EJB(lookup = "java:global/database-impl/ParkingPlaceDAO!com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO")
    private IParkingPlaceRemoteDAO parkingPlaceRemoteDAO;

    @Resource(name = "java:global/jms/queue/SOAParkingEventsQueue")
    private Queue eventsQueue;

    private JMSContext jmsContext;

    public void changeParkingPlaceStatus(int placeId, long timestamp, boolean occupied) {
        if (parkingPlaceExists(placeId)) {
            updateParkingPlaceStatusInDatabase(placeId, timestamp, occupied);
            handleSchedulers(placeId, timestamp, occupied);
            sendEventNotification(placeId, timestamp, occupied);
        }
    }

    @Inject
    public void setJmsContext(JMSContext jmsContext) {
        this.jmsContext = jmsContext;
    }

    public void setParkingPlaceRemoteDAO(IParkingPlaceRemoteDAO parkingPlaceRemoteDAO) {
        this.parkingPlaceRemoteDAO = parkingPlaceRemoteDAO;
    }

    private boolean parkingPlaceExists(int placeId) {
        return parkingPlaceRemoteDAO.find(placeId) != null;
    }

    private void updateParkingPlaceStatusInDatabase(int placeId, long timestamp, boolean occupied) {
        ParkingPlace parkingPlace = parkingPlaceRemoteDAO.find(placeId);
        parkingPlace.setOccupied(occupied);
        parkingPlaceRemoteDAO.edit(parkingPlace);
    }

    private void handleSchedulers(int placeId, long timestamp, boolean occupied) {

    }

    private void sendEventNotification(int placeId, long timestamp, boolean occupied) {
        ParkingPlaceStatusUpdateMessage message = new ParkingPlaceStatusUpdateMessage(placeId, new Date(timestamp), occupied);
        ObjectMessage objectMessage = jmsContext.createObjectMessage(message);
        jmsContext.createProducer().send(eventsQueue, objectMessage);
    }
}
