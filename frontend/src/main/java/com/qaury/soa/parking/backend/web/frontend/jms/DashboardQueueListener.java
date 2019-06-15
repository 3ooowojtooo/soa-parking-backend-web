package com.qaury.soa.parking.backend.web.frontend.jms;

import com.qaury.soa.parking.backend.web.frontend.service.DashboardMessageHandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(
        name = "EventQueueListener",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType",
                        propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination",
                        propertyValue = "java:global/jms/SOAParkingDashboardQueue")
        }
)
public class DashboardQueueListener implements MessageListener {

    @EJB
    private DashboardMessageHandler messageHandler;

    @Override
    public void onMessage(Message message) {
        try {
            messageHandler.handleDashboardMessage(((TextMessage) message).getText());
        } catch (JMSException e) {
            System.out.println("DashboardMessageHandler: " + e.getMessage());
        }
    }
}
