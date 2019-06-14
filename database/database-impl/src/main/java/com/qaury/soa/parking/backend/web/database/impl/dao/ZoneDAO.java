package com.qaury.soa.parking.backend.web.database.impl.dao;

import com.qaury.soa.parking.backend.web.database.api.dao.IZoneDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.IZoneLocalDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.IZoneRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.Zone;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Singleton
@Local(IZoneLocalDAO.class)
@Remote(IZoneRemoteDAO.class)
public class ZoneDAO extends BaseDAO<Zone> implements IZoneDAO {

    @PersistenceContext(unitName = "SOAParkingMainDatabase")
    private EntityManager entityManager;

    public ZoneDAO() {
        super(Zone.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
