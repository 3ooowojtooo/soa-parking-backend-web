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
}
