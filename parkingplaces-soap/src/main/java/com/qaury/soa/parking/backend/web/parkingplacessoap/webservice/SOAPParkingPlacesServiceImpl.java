package com.qaury.soa.parking.backend.web.parkingplacessoap.webservice;

import com.qaury.soa.parking.backend.web.parkingplacessoap.service.ChangeParkingPlaceStatusService;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Date;

@WebService(endpointInterface = "com.qaury.soa.parking.backend.web.parkingplacessoap.webservice.SOAPParkingPlacesService")
public class SOAPParkingPlacesServiceImpl implements SOAPParkingPlacesService {

    private ChangeParkingPlaceStatusService changeParkingPlaceStatusService;

    @Override
    @WebMethod
    public void changeParkingPlaceState(Integer placeId, Boolean occupied) {
        changeParkingPlaceStatusService.changeParkingPlaceStatus(placeId, new Date().getTime(), occupied);
    }

    @Inject
    public void setChangeParkingPlaceStatusService(ChangeParkingPlaceStatusService changeParkingPlaceStatusService) {
        this.changeParkingPlaceStatusService = changeParkingPlaceStatusService;
    }
}
