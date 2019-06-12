package com.qaury.soa.parking.backend.web.mobilenotifications.rest.controller;

import com.qaury.soa.parking.backend.web.mobilenotifications.rest.service.MobileNotificationVerifierService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@RequestScoped
public class MobileNotificationVerifierController {

    private MobileNotificationVerifierService mobileNotificationVerifierService;

    @GET
    @Path("/parkingplaces/{placeId}/isinticketnotpurchasedstate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isPlaceIdInTicketPurchasedNotPurchasedState(@PathParam("placeId") Integer placeId) {
        return Response.ok(mobileNotificationVerifierService.verifyNotPurchasedStatus(placeId)).build();
    }

    @GET
    @Path("/parkingplaces/{placeId}/isinticketexpiredstate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isPlaceIdInTicketExpireState(@PathParam("placeId") Integer placeId) {
        return Response.ok(mobileNotificationVerifierService.verifyTicketExpiredStatus(placeId)).build();
    }

    @Inject
    public void setMobileNotificationVerifierService(MobileNotificationVerifierService mobileNotificationVerifierService) {
        this.mobileNotificationVerifierService = mobileNotificationVerifierService;
    }
}
