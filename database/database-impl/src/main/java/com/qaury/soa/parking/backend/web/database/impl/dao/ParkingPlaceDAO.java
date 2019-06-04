package com.qaury.soa.parking.backend.web.database.impl.dao;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceLocalDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.ParkingPlace;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Local(IParkingPlaceLocalDAO.class)
@Remote(IParkingPlaceRemoteDAO.class)
public class ParkingPlaceDAO extends BaseDAO<ParkingPlace> implements IParkingPlaceDAO {

    @PersistenceContext(unitName = "SOAParkingMainDatabase")
    private EntityManager entityManager;

    public ParkingPlaceDAO() {
        super(ParkingPlace.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
