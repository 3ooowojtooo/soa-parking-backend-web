package com.qaury.soa.parking.backend.web.database.api.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "parking_places")
public class ParkingPlace implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @Column(name = "occupied", nullable = false)
    private Boolean occupied;

    @Column(name = "time_for_ticket_purchase")
    private Date timeForTicketPurchase;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parkingPlace", cascade = CascadeType.ALL)
    private Set<Ticket> ticketList;

    public ParkingPlace() {
    }

    public ParkingPlace(Integer id, Boolean occupied, Date timeForTicketPurchase) {
        this.id = id;
        this.occupied = occupied;
        this.timeForTicketPurchase = timeForTicketPurchase;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public Date getTimeForTicketPurchase() {
        return timeForTicketPurchase;
    }

    public void setTimeForTicketPurchase(Date timeForTicketPurchase) {
        this.timeForTicketPurchase = timeForTicketPurchase;
    }

    public Set<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(Set<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public void addTicket(Ticket ticket) {
        if (ticket != null && !ticketList.contains(ticket)) {
            this.ticketList.add(ticket);
            ticket.setParkingPlace(this);
        }
    }

    public void removeTicket(Ticket ticket) {
        if (ticket != null && ticketList.contains(ticket)) {
            this.ticketList.remove(ticket);
            ticket.setParkingPlace(null);
        }
    }
}
