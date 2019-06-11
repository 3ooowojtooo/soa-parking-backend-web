package com.qaury.soa.parking.backend.web.parkingplacessoap.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface SOAPParkingPlacesService {

    @WebMethod
    void changeParkingPlaceState(@WebParam(name = "placeId") Integer placeId,
                                 @WebParam(name = "timestamp") Long timestamp,
                                 @WebParam(name = "occupied") Boolean occupied);
}
