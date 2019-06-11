package com.qaury.soa.parking.backend.web.parkingplacessoap.service;

import com.qaury.soa.parking.backend.web.events.api.messages.ParkingPlaceStatusUpdateMessage;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import java.util.Date;

@ApplicationScoped
public class ChangeParkingPlaceStatusService {

    @Resource(name = "java:global/jms/queue/SOAParkingEventsQueue")
    private Queue eventsQueue;

    private JMSContext jmsContext;

    public void changeParkingPlaceStatus(int placeId, long timestamp, boolean occupied) {
        sendEventNotification(placeId, timestamp, occupied);
    }

    @Inject
    public void setJmsContext(JMSContext jmsContext) {
        this.jmsContext = jmsContext;
    }


    private void sendEventNotification(int placeId, long timestamp, boolean occupied) {
        ParkingPlaceStatusUpdateMessage message = new ParkingPlaceStatusUpdateMessage(placeId, new Date(timestamp), occupied);
        ObjectMessage objectMessage = jmsContext.createObjectMessage(message);
        jmsContext.createProducer().send(eventsQueue, objectMessage);
    }
}
