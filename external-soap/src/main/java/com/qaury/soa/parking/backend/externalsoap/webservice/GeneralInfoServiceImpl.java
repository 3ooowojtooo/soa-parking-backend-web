package com.qaury.soa.parking.backend.externalsoap.webservice;

import com.quary.soa.parking.backend.data.api.external.IExternalDataServiceRemote;
import com.quary.soa.parking.backend.data.api.external.model.GeneralInfo;

import javax.ejb.EJB;
import javax.jws.WebService;

@WebService(endpointInterface = "com.qaury.soa.parking.backend.externalsoap.webservice.GeneralInfoService")
public class GeneralInfoServiceImpl implements GeneralInfoService {

    @EJB(lookup = "java:global/dataimpl/ExternalDataService!com.quary.soa.parking.backend.data.api.external.IExternalDataServiceRemote")
    private IExternalDataServiceRemote externalDataServiceRemote;

    @Override
    public GeneralInfo getGeneralInfo() {
        return externalDataServiceRemote.createZoneGeneralInfo();
    }
}
