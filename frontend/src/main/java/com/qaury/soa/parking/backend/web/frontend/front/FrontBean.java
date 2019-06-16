package com.qaury.soa.parking.backend.web.frontend.front;

import com.google.gson.Gson;
import com.qaury.soa.parking.backend.web.database.api.entity.Zone;
import com.qaury.soa.parking.backend.web.database.api.exception.PasswordChangeException;
import models.Notification;
import org.primefaces.context.RequestContext;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Named
@SessionScoped
public class FrontBean implements Serializable {

    @EJB private FrontDataConnectorEJB testEJB;

    public boolean shouldAdminChannelBeRendered() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Admin");
    }

    public List<Zone> getZones(){
        return testEJB.getZonesForLoggedUser();
    }

    public void notification(){
        Gson gson = new Gson();
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Notification notification = gson.fromJson(params.get("msg"),Notification.class);
        if(notification!=null &&
                getZones().stream().map(Zone::getId).collect(Collectors.toList()).contains(notification.getZoneId())) {
            FacesMessage msg = new FacesMessage("Parking place: " + notification.getPlaceId().toString());
            if(notification.getOccupied())
                msg.setDetail("has been occupied");
            else
                msg.setDetail("has been freed");
            if(notification.getOccupied() && notification.getNotPurchasedNotification())
                msg.setDetail("is occupied but not paid");
            if(notification.getOccupied() && notification.getTicketExpireNotification())
                msg.setDetail("is occupied but the ticket has just expired");
            if(notification.getOccupied() && notification.getTickets()!=null && !notification.getTickets().isEmpty())
                msg.setDetail("is occupied and paid, ticket info: " + notification.getTickets().stream().map(t-> "Date: " + t.getDateFrom() + " - " + t.getDateTo()).collect(Collectors.joining()));
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