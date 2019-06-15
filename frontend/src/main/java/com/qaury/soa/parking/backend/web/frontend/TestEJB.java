package com.qaury.soa.parking.backend.web.frontend;

import com.qaury.soa.parking.backend.web.database.api.dao.IAuthRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.Zone;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import java.util.List;

@Singleton
public class TestEJB {
    @Resource
    SessionContext sessionContext;

    @EJB(lookup = "java:global/database-impl/AuthDAO!com.qaury.soa.parking.backend.web.database.api.dao.IAuthRemoteDAO")
    private IAuthRemoteDAO authDAO;

    public List<Zone> getZonesForLoggedUser() {
        return authDAO.getAllowedZonesFroPrincipal(sessionContext.getCallerPrincipal().getName());
    }
}
