package com.qaury.soa.parking.backend.web.frontend.service;

import com.qaury.soa.parking.backend.web.frontend.front.FrontDataConnectorEJB;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;

@Singleton
public class DashboardMessageHandler {

    @Resource
    SessionContext sessionContext;

    @Inject
    @Push
    PushContext adminDashboardChannel;

    @Inject
    @Push
    PushContext controllerDashboardChannel;

    @EJB
    private FrontDataConnectorEJB frontDataConnectorEJB;

    public void handleDashboardMessage(String message) {
        System.out.println(message);
        pushParkingPlaceDescriptionAccordingToLoggedUserRole(message);
    }

    private void pushParkingPlaceDescriptionAccordingToLoggedUserRole(String message) {
        if (sessionContext.isCallerInRole("Admin")) {
            adminDashboardChannel.send(message);
        } else if (sessionContext.isCallerInRole("Controller")) {
            controllerDashboardChannel.send(message, sessionContext.getCallerPrincipal().getName());
        } else {
            System.out.println("Pushing notification to websocket: Incorrect role.");
        }
    }
}
