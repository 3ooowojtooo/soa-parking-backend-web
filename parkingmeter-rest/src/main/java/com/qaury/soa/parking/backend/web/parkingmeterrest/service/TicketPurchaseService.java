package com.qaury.soa.parking.backend.web.parkingmeterrest.service;

import com.qaury.soa.parking.backend.web.events.api.messages.TicketPurchaseMessage;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import java.util.Date;

@ApplicationScoped
public class TicketPurchaseService {

    @Resource(name = "java:global/jms/queue/SOAParkingEventsQueue")
    private Queue eventsQueue;

    private JMSContext jmsContext;

    public void handleTicketPurchase(int placeId, long timestamp, long timestampFrom, long timestampTo) {
       sendEventNotification(placeId, timestamp, timestampFrom, timestampTo);
    }

    @Inject
    public void setJmsContext(JMSContext jmsContext) {
        this.jmsContext = jmsContext;
    }

    private void sendEventNotification(int placeId, long timestamp, long timestampFrom, long timestampTo) {
        TicketPurchaseMessage ticketPurchaseMessage = new TicketPurchaseMessage(placeId, new Date(timestamp), new Date(timestampFrom), new Date(timestampTo));
        ObjectMessage objectMessage = jmsContext.createObjectMessage(ticketPurchaseMessage);
        jmsContext.createProducer().send(eventsQueue, objectMessage);
    }
}
