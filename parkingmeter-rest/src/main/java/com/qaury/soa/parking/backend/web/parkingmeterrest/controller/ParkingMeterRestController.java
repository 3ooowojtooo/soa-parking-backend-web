package com.qaury.soa.parking.backend.web.parkingmeterrest.controller;

import com.qaury.soa.parking.backend.web.parkingmeterrest.service.TicketPurchaseService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@RequestScoped
public class ParkingMeterRestController {

    private TicketPurchaseService ticketPurchaseService;

    @POST
    @Path("/ticketpurchase")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ticketPurchase(TicketPurchaseRequestBody requestBody) {
        ticketPurchaseService.handleTicketPurchase(requestBody.getPlaceId(), requestBody.getTimestamp(), requestBody.getTimestampFrom(), requestBody.getTimestampTo());
        return Response.ok().build();
    }

    @Inject
    public void setTicketPurchaseService(TicketPurchaseService ticketPurchaseService) {
        this.ticketPurchaseService = ticketPurchaseService;
    }
}