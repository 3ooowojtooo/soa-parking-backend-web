package com.qaury.soa.parking.backend.web.frontend.front;

import com.qaury.soa.parking.backend.web.database.api.entity.Zone;
import com.qaury.soa.parking.backend.web.database.api.exception.PasswordChangeException;
import org.primefaces.context.RequestContext;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.List;
import java.util.Map;


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

    public List<Zone> getZones(){
        return testEJB.getZonesForLoggedUser();
    }

    public void notification(){
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String message = params.get("msg");
        if(message!=null) {
            FacesMessage msg = new FacesMessage(message);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("msgs");
        }
    }

    public boolean shouldControllerChannelBeRendered() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Controller");
    }

    public String getUserPrincipal() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    }

    public void changePassword(String login, String password) {
        FacesMessage msg = new FacesMessage();
        System.out.println(login + ":" + password);
        try {
            testEJB.changePassword(login, password);
            msg = new FacesMessage("Changed password for ", login);
        } catch (PasswordChangeException e) {
            msg = new FacesMessage("Could not change password: ", e.getMessage());
        } finally {
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public List<String> getLoginsForWhichLoggedUserCanChangePassword() {
        return testEJB.getLoginsForWhichLoggedUserCanChangePassword();
    }

}