package com.qaury.soa.parking.backend.web.database.impl.dao;

import com.qaury.soa.parking.backend.web.database.api.dao.ITicketDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.ITicketLocalDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.ITicketRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.Ticket;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.util.Date;

@Singleton
@Local(ITicketLocalDAO.class)
@Remote(ITicketRemoteDAO.class)
public class TicketDAO extends BaseDAO<Ticket> implements ITicketDAO {

    @PersistenceContext(unitName = "SOAParkingMainDatabase")
    private EntityManager entityManager;

    public TicketDAO() {
        super(Ticket.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void deleteAllTicketsForPlaceId(int placeId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaDelete<Ticket> cd = cb.createCriteriaDelete(Ticket.class);
        Root<Ticket> root = cd.from(Ticket.class);
        cd.where(cb.equal(root.get("parkingPlace"), placeId));
        getEntityManager().createQuery(cd).executeUpdate();
    }

    @Override
    public void deleteExpiredTickets(int placeId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaDelete<Ticket> cd = cb.createCriteriaDelete(Ticket.class);
        Root<Ticket> root = cd.from(Ticket.class);
        cd.where(cb.and(cb.equal(root.get("parkingPlace").get("id"), placeId), cb.lessThan(root.<Date>get("dateTo"), new Date())));
        getEntityManager().createQuery(cd).executeUpdate();
    }
}
