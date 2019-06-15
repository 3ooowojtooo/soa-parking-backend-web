package com.qaury.soa.parking.backend.web.externalrest;

import com.qaury.soa.parking.backend.web.externalrest.controller.ExternalInfoController;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class ExternalRestApplication extends Application {

    private Set<Object> singletons = new HashSet<>();

    @PostConstruct
    public void init() {
        singletons.add(new ExternalInfoController());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
