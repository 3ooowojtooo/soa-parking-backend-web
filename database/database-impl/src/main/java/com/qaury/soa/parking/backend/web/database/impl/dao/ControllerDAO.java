package com.qaury.soa.parking.backend.web.database.impl.dao;

import com.qaury.soa.parking.backend.web.database.api.dao.IControllerDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.IControllerLocalDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.IControllerRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.Controller;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Local(IControllerLocalDAO.class)
@Remote(IControllerRemoteDAO.class)
public class ControllerDAO extends BaseDAO<Controller> implements IControllerDAO {

    @PersistenceContext(unitName = "SOAParkingMainDatabase")
    private EntityManager entityManager;

    public ControllerDAO() {
        super(Controller.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
