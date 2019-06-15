package com.qaury.soa.parking.backend.web.frontend;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class FrontBean {

    @EJB
    private FrontDataConnectorEJB testEJB;

    public String test() {
        return String.valueOf(testEJB.getZonesForLoggedUser().size());
    }
}