package com.qaury.soa.parking.backend.externalsoap.webservice;

import com.quary.soa.parking.backend.data.api.external.model.GeneralInfo;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface GeneralInfoService {

    @WebMethod
    @WebResult(name = "info")
    GeneralInfo getGeneralInfo();
}
