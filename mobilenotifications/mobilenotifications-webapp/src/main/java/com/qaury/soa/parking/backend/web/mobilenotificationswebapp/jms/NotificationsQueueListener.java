package com.qaury.soa.parking.backend.web.mobilenotificationswebapp.jms;

import com.qaury.soa.parking.backend.web.mobilenotifications.api.messages.TicketExpiredMessage;
import com.qaury.soa.parking.backend.web.mobilenotifications.api.messages.TicketNotPurchasedMessage;
import com.qaury.soa.parking.backend.web.mobilenotificationswebapp.service.NotificationsHandler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;

@MessageDriven(
        name = "NotificationsQueueListener",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType",
                        propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination",
                        propertyValue = "java:global/jms/queue/SOAParkingMobileNotificationsQueue")
        }
)
public class NotificationsQueueListener implements MessageListener {

    @EJB
    private NotificationsHandler notificationsHandler;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Serializable object = objectMessage.getObject();
            if (object instanceof TicketExpiredMessage) {
                System.out.println("NotificationsQueueListener: ticket expired notification");
                notificationsHandler.handleTicketExpiredMessage((TicketExpiredMessage)object);
            } else if (object instanceof TicketNotPurchasedMessage) {
                System.out.println("NotificationsQueueListener: ticket not purchased notification");
                notificationsHandler.handleTicketNotPurchasedMessage((TicketNotPurchasedMessage)object);
            }
        } catch (JMSException e) {
            System.out.println("Error in MobileNotificationQueueListener. JMS Exception: " + e.getMessage());
        }
    }
}
