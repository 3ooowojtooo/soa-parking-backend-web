package com.qaury.soa.parking.backend.web.externalrest.controller;

import com.qaury.soa.parking.backend.web.externalrest.service.ExternalInfoHelperService;
import com.quary.soa.parking.backend.data.api.external.model.GeneralInfo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@RequestScoped
public class ExternalInfoController {

    private ExternalInfoHelperService externalInfoHelperService;

    @GET
    @Path("/generalinfo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getZonesGeneralInfo() {
        GeneralInfo generalInfo = externalInfoHelperService.getPakringZonesGeneralInfo();
        return Response.ok(generalInfo).build();
    }

    @Inject
    public void setExternalInfoHelperService(ExternalInfoHelperService externalInfoHelperService) {
        this.externalInfoHelperService = externalInfoHelperService;
    }
}