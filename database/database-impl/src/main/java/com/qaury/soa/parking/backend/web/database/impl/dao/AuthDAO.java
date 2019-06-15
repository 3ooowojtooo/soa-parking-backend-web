package com.qaury.soa.parking.backend.web.database.impl.dao;


import com.qaury.soa.parking.backend.web.database.api.dao.IAuthDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.IAuthLocalDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.IAuthRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.Auth;
import com.qaury.soa.parking.backend.web.database.api.entity.Zone;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
@Local(IAuthLocalDAO.class)
@Remote(IAuthRemoteDAO.class)
public class AuthDAO extends BaseDAO<Auth> implements IAuthDAO {

    @PersistenceContext(unitName = "SOAParkingMainDatabase")
    private EntityManager entityManager;

    public AuthDAO() {
        super(Auth.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Auth findByLogin(String login) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Auth> cq = cb.createQuery(Auth.class);
        Root<Auth> root = cq.from(Auth.class);
        cq.select(root).where(cb.equal(root.get("login"), login));
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @Override
    @Transactional
    public List<Zone> getAllowedZonesFroPrincipal(String principalName) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Auth auth = findByLogin(principalName);
        if (auth.getRole().equals("Admin")) {
            CriteriaQuery<Zone> cq = cb.createQuery(Zone.class);
            Root<Zone> root = cq.from(Zone.class);
            cq.select(root);
            return getEntityManager().createQuery(cq).getResultList();
        } else {
            CriteriaQuery<Zone> cq = cb.createQuery(Zone.class);
            Root<Zone> root = cq.from(Zone.class);
            cq.select(root).where(cb.equal(root.get("controller").get("auth").get("login"), principalName));
            return getEntityManager().createQuery(cq).getResultList();
        }
    }
}
