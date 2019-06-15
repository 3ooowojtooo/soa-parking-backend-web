package com.qaury.soa.parking.backend.web.mobilenotifications.rest;

import com.qaury.soa.parking.backend.web.mobilenotifications.rest.controller.MobileNotificationVerifierController;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationPath("/")
public class MobileNotificationsRestApplication extends Application {

    private Set<Object> singletons = new LinkedHashSet<>();

    @PostConstruct
    public void init() {
        singletons.add(new MobileNotificationVerifierController());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
