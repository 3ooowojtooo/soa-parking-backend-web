package com.qaury.soa.parking.backend.web.events.impl.schedule;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.Controller;
import com.qaury.soa.parking.backend.web.mobilenotifications.api.messages.TicketExpiredMessage;
import com.qaury.soa.parking.backend.web.mobilenotifications.api.messages.TicketNotPurchasedMessage;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

@Singleton
public class ScheduleHandler {

    @EJB(lookup = "java:global/database-impl/ParkingPlaceDAO!com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO")
    private IParkingPlaceRemoteDAO parkingPlaceDAO;

    @Resource(name = "java:global/jms/queue/SOAParkingMobileNotificationsQueue")
    private Queue mobileNotificationsQueue;

    @Inject
    JMSContext jmsContext;

    public void handleTicketPurchaseTimeout(Integer placeId) {
        Controller controller = parkingPlaceDAO.findControllerForParkingPlace(placeId);
        if (controller != null) {
            TicketNotPurchasedMessage ticketNotPurchasedMessage = new TicketNotPurchasedMessage(controller.getId(), placeId);
            ObjectMessage objectMessage = jmsContext.createObjectMessage(ticketNotPurchasedMessage);
            jmsContext.createProducer().send(mobileNotificationsQueue, objectMessage);
        }
    }

    public void handleTicketExpireTimeout(Integer placeId) {
        Controller controller = parkingPlaceDAO.findControllerForParkingPlace(placeId);
        if (controller != null) {
            TicketExpiredMessage ticketExpiredMessage = new TicketExpiredMessage(controller.getId(), placeId);
            ObjectMessage objectMessage = jmsContext.createObjectMessage(ticketExpiredMessage);
            jmsContext.createProducer().send(mobileNotificationsQueue, objectMessage);
        }
    }
}
