package com.qaury.soa.parking.backend.web.mobilenotifications.impl.jms;

import com.qaury.soa.parking.backend.web.mobilenotifications.api.messages.TicketExpiredMessage;
import com.qaury.soa.parking.backend.web.mobilenotifications.api.messages.TicketNotPurchasedMessage;
import com.qaury.soa.parking.backend.web.mobilenotifications.impl.service.NotificationHandlerService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
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
                        propertyValue = "java:global/jms/queue/SOAParkingMobileNotificationsQueue")
        }
)
public class MobileNotificationsQueueListener implements MessageListener {

    @EJB
    private NotificationHandlerService notificationHandlerService;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Serializable object = objectMessage.getObject();
            if (object instanceof TicketExpiredMessage) {
                System.out.println("MobileNotificationsQueueListener: ticket expired notification");
                notificationHandlerService.handleTicketExpiredMessage((TicketExpiredMessage)object);
            } else if (object instanceof TicketNotPurchasedMessage) {
                System.out.println("MobileNotificationsQueueListener: ticket not purchased notification");
                notificationHandlerService.handleTicketNotPurchasedMessage((TicketNotPurchasedMessage)object);
            }
        } catch (JMSException e) {
            System.out.println("Error in MobileNotificationQueueListener. JMS Exception: " + e.getMessage());
        }
    }
}
