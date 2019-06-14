package com.qaury.soa.parking.backend.web.frontend;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;

@Singleton
public class TestEJB {
    @Resource
    SessionContext sessionContext;

    public String test() {
        return String.valueOf(sessionContext.isCallerInRole("Controller"));
    }
}
