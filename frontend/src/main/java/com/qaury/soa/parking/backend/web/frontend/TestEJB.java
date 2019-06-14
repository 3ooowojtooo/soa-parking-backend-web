package com.qaury.soa.parking.backend.web.frontend;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import java.util.List;

@Singleton
public class TestEJB {
    @Resource
    SessionContext sessionContext;

    @EJB(lookup = "java:global/database-impl/ParkingPlaceDAO!com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO")
    private IParkingPlaceRemoteDAO parkingPlaceDAO;

    public String test() {
        List<Object[]> objects = parkingPlaceDAO.getFreePlacesInfo();
        String result = objects.get(0)[0].toString() + " " + objects.get(0)[1].toString();
        return result;
    }
}
