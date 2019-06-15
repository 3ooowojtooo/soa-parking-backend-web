package com.qaury.soa.parking.backend.web.frontend;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class TestBean {

    @EJB
    private TestEJB testEJB;

    public String test() {
        return testEJB.getZonesForLoggedUser().size() + " xd";
    }
}