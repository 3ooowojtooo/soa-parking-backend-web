package com.qaury.soa.parking.backend.web.frontrest;

import com.qaury.soa.parking.backend.web.frontrest.controller.TestController;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationPath("/")
public class FrontRestApplication extends Application {

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
