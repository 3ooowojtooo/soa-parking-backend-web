package com.qaury.soa.parking.backend.web.externalrest.service;

import com.quary.soa.parking.backend.data.api.external.IExternalDataServiceRemote;
import com.quary.soa.parking.backend.data.api.external.model.GeneralInfo;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExternalInfoHelperService {

    @EJB(lookup = "java:global/dataimpl/ExternalDataService!com.quary.soa.parking.backend.data.api.external.IExternalDataServiceRemote")
    private IExternalDataServiceRemote externalDataService;

    public GeneralInfo getPakringZonesGeneralInfo() {
        return externalDataService.createZoneGeneralInfo();
    }
}
