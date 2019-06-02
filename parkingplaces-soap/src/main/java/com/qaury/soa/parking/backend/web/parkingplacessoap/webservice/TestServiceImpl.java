package com.qaury.soa.parking.backend.web.parkingplacessoap.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "com.qaury.soa.parking.backend.web.parkingplacessoap.webservice.TestService")
public class TestServiceImpl implements TestService {

    @Override
    @WebMethod
    public String hello() {
        return "Hello World";
    }
}
