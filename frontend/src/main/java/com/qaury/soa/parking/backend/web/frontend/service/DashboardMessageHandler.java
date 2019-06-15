package com.qaury.soa.parking.backend.web.frontend.service;

import javax.ejb.Singleton;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;

@Singleton
public class DashboardMessageHandler {

    @Inject
    @Push
    PushContext dashboardChannel;

    public void handleDashboardMessage(String message) {
        System.out.println(message);
        pushParkingPlaceDescriptionAccordingToLoggedUserRole(message);
    }

    private void pushParkingPlaceDescriptionAccordingToLoggedUserRole(String message) {
        System.out.println("Pushing notification to dashbaord websocket.");
        dashboardChannel.send(message);

    }
}
