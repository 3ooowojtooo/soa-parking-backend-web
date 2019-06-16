package models;

import java.util.List;

public class Notification {
    List<Ticket> tickets;
    Integer placeId;
    Integer zoneId;
    Boolean notPurchasedNotification;
    Boolean ticketExpireNotification;
    Boolean occupied;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Boolean getNotPurchasedNotification() {
        return notPurchasedNotification;
    }

    public void setNotPurchasedNotification(Boolean notPurchasedNotification) {
        this.notPurchasedNotification = notPurchasedNotification;
    }

    public Boolean getTicketExpireNotification() {
        return ticketExpireNotification;
    }

    public void setTicketExpireNotification(Boolean ticketExpireNotification) {
        this.ticketExpireNotification = ticketExpireNotification;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }
}
