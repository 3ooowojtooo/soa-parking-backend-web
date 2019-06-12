package com.qaury.soa.parking.backend.web.events.impl.jms;

import com.qaury.soa.parking.backend.web.events.api.messages.ParkingPlaceStatusUpdateMessage;
import com.qaury.soa.parking.backend.web.events.api.messages.TicketPurchaseMessage;
import com.qaury.soa.parking.backend.web.events.impl.service.ParkingPlaceStatusUpdateMessageHandler;
import com.qaury.soa.parking.backend.web.events.impl.service.TicketPurchaseMessageHandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;

@MessageDriven(
        name = "EventQueueListener",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType",
                        propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination",
                        propertyValue = "java:global/jms/queue/SOAParkingEventsQueue")
        }
)
public class EventsQueueListener implements MessageListener {

    @EJB
    private ParkingPlaceStatusUpdateMessageHandler parkingPlaceStatusUpdateMessageHandler;

    @EJB
    private TicketPurchaseMessageHandler ticketPurchaseMessageHandler;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Serializable sendObject = objectMessage.getObject();
            if (sendObject instanceof TicketPurchaseMessage) {
                System.out.println("ticket purchase");
                ticketPurchaseMessageHandler.handle((TicketPurchaseMessage)sendObject);
            } else if (sendObject instanceof ParkingPlaceStatusUpdateMessage) {
                System.out.println("place status update");
                parkingPlaceStatusUpdateMessageHandler.handle((ParkingPlaceStatusUpdateMessage)sendObject);
            }
        } catch (JMSException e) {
            System.out.println(e.getMessage());
        }

    }

}
