package com.qaury.soa.parking.backend.web.events.impl.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

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

    @Override
    public void onMessage(Message message) {

    }
}
