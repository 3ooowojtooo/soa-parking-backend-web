package com.qaury.soa.parking.backend.web.parkingmeterrest;

import com.qaury.soa.parking.backend.web.parkingmeterrest.controller.TestController;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationPath("/")
public class ParkingMeterRestApplication extends Application {

    private Set<Object> singletons = new LinkedHashSet<>();

    @PostConstruct
    public void init() {
        singletons.add(new TestController());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
