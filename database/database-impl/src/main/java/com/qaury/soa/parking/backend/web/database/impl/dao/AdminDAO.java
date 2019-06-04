package com.qaury.soa.parking.backend.web.database.impl.dao;

import com.qaury.soa.parking.backend.web.database.api.dao.IAdminDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.IAdminLocalDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.IAdminRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.Admin;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Local(IAdminLocalDAO.class)
@Remote(IAdminRemoteDAO.class)
public class AdminDAO extends BaseDAO<Admin> implements IAdminDAO {

    @PersistenceContext(unitName = "SOAParkingMainDatabase")
    private EntityManager entityManager;

    public AdminDAO() {
        super(Admin.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
