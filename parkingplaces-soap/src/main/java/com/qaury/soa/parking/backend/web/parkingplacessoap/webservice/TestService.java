package com.qaury.soa.parking.backend.web.parkingplacessoap.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface TestService {

    @WebMethod
    String hello();
}
