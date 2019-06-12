package com.qaury.soa.parking.backend.web.mobilenotifications.rest.service;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;
import com.qaury.soa.parking.backend.web.mobilenotifications.rest.response.StateResponse;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MobileNotificationVerifierService {

    @EJB(lookup = "java:global/database-impl/ParkingPlaceDAO!com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO")
    private IParkingPlaceRemoteDAO parkingPlaceDAO;

    public StateResponse verifyNotPurchasedStatus(Integer placeId) {
        StateResponse stateResponse = new StateResponse();
        stateResponse.setState(parkingPlaceDAO.isPlaceTicketNotPurchasedNotificationValid(placeId));
        return stateResponse;
    }

    public StateResponse verifyTicketExpiredStatus(Integer placeId) {
        StateResponse stateResponse = new StateResponse();
        stateResponse.setState(parkingPlaceDAO.isPlaceTicketExpiredNotificationValid(placeId));
        return stateResponse;
    }

}
