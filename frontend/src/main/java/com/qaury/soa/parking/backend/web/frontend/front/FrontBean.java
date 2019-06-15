package com.qaury.soa.parking.backend.web.frontend.front;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ApplicationScoped
public class FrontBean {

    @EJB
    private FrontDataConnectorEJB testEJB;

    public String test() {
        return String.valueOf(testEJB.getZonesForLoggedUser().size());
    }

    public boolean shouldAdminChannelBeRendered() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Admin");
    }

    public boolean shouldControllerChannelBeRendered() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Controller");
    }

    public String getUserPrincipal() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    }
}