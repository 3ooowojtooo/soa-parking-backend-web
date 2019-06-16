package com.qaury.soa.parking.backend.web.events.impl.schedule;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.ITicketRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.Controller;
import com.qaury.soa.parking.backend.web.events.impl.service.DashboardNotificationSender;
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

    @EJB(lookup = "java:global/database-impl/TicketDAO!com.qaury.soa.parking.backend.web.database.api.dao.ITicketRemoteDAO")
    private ITicketRemoteDAO ticketDAO;

    @Resource(name = "java:global/jms/queue/SOAParkingMobileNotificationsQueue")
    private Queue mobileNotificationsQueue;

    @Inject
    JMSContext jmsContext;

    @EJB
    private DashboardNotificationSender dashboardNotificationSender;

    public void handleTicketPurchaseTimeout(Integer placeId) {
        parkingPlaceDAO.setPlaceTicketNotPurchased(placeId, true);
        Integer controllerId = parkingPlaceDAO.findControllerIdForParkingPlace(placeId);
        TicketNotPurchasedMessage ticketNotPurchasedMessage = new TicketNotPurchasedMessage(controllerId, placeId);
        ObjectMessage objectMessage = jmsContext.createObjectMessage(ticketNotPurchasedMessage);
        jmsContext.createProducer().send(mobileNotificationsQueue, objectMessage);
        dashboardNotificationSender.sendDashboardParkingPlaceUpdate(placeId);
    }

    public void handleTicketExpireTimeout(Integer placeId) {
        parkingPlaceDAO.setPlaceTicketExpired(placeId, true);
        ticketDAO.deleteExpiredTickets(placeId);
        parkingPlaceDAO.flush();
        Integer controllerId = parkingPlaceDAO.findControllerIdForParkingPlace(placeId);
        TicketExpiredMessage ticketExpiredMessage = new TicketExpiredMessage(controllerId, placeId);
        ObjectMessage objectMessage = jmsContext.createObjectMessage(ticketExpiredMessage);
        jmsContext.createProducer().send(mobileNotificationsQueue, objectMessage);
        dashboardNotificationSender.sendDashboardParkingPlaceUpdate(placeId);
    }
}
