package com.qaury.soa.parking.backend.web.frontend;

import com.qaury.soa.parking.backend.web.database.api.dao.IAuthRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.Zone;
import com.qaury.soa.parking.backend.web.database.api.exception.PasswordChangeException;
import org.jboss.security.auth.spi.Util;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import java.util.List;

@Singleton
public class FrontDataConnectorEJB {
    @Resource
    SessionContext sessionContext;

    @EJB(lookup = "java:global/database-impl/AuthDAO!com.qaury.soa.parking.backend.web.database.api.dao.IAuthRemoteDAO")
    private IAuthRemoteDAO authDAO;

    public List<Zone> getZonesForLoggedUser() {
        return authDAO.getAllowedZonesFroPrincipal(sessionContext.getCallerPrincipal().getName());
    }

    public List<String> getLoginsForWhichLoggedUserCanChangePassword() {
        return authDAO.getLoginsForWhichPrincipalCanChangePassword(sessionContext.getCallerPrincipal().getName());
    }

    public void changePassword(String login, String plainTextPassword) throws PasswordChangeException {
        String passwordHash = hashPassword(plainTextPassword);
        authDAO.changePassword(sessionContext.getCallerPrincipal().getName(), login, passwordHash);
    }

    private String hashPassword(String plainTextPassword) {
        return Util.createPasswordHash("MD5", Util.BASE64_ENCODING, null, null, plainTextPassword);
    }

}
