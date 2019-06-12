package com.qaury.soa.parking.backend.web.database.impl.dao;

import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceLocalDAO;
import com.qaury.soa.parking.backend.web.database.api.dao.IParkingPlaceRemoteDAO;
import com.qaury.soa.parking.backend.web.database.api.entity.Controller;
import com.qaury.soa.parking.backend.web.database.api.entity.ParkingPlace;
import com.qaury.soa.parking.backend.web.database.api.entity.Zone;
import org.hibernate.cfg.annotations.QueryBinder;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

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

    @Override
    public Controller findControllerForParkingPlace(int placeId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Controller> cq = cb.createQuery(Controller.class);
        Root<Controller> root = cq.from(Controller.class);
        //cq.select(root).where(root.get("zone").get("parkingPlaceList").get("id").in(placeId));
        cq.select(root);
        return getEntityManager().createQuery(cq).getResultList().get(0);
    }

    @Override
    public boolean isPlaceTicketNotPurchasedNotificationValid(int placeId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Boolean> cq = cb.createQuery(Boolean.class);
        Root<ParkingPlace> root = cq.from(ParkingPlace.class);
        cq.select(root.get("notPurchasedNotification")).where(cb.equal(root.get("id"), placeId));
        Boolean notPurchased = getEntityManager().createQuery(cq).getSingleResult();
        if (notPurchased == null)
            return false;
        return notPurchased;
    }

    @Override
    public boolean isPlaceTicketExpiredNotificationValid(int placeId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Boolean> cq = cb.createQuery(Boolean.class);
        Root<ParkingPlace> root = cq.from(ParkingPlace.class);
        cq.select(root.get("ticketExpireNotification")).where(cb.equal(root.get("id"), placeId));
        Boolean ticketExpired = getEntityManager().createQuery(cq).getSingleResult();
        if (ticketExpired == null)
            return false;
        return ticketExpired;
    }

    @Override
    public void setPlaceTicketNotPurchased(int placeId, boolean state) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<ParkingPlace> cu = cb.createCriteriaUpdate(ParkingPlace.class);
        Root<ParkingPlace> root = cu.from(ParkingPlace.class);
        cu.set(root.get("notPurchasedNotification"), state).where(cb.equal(root.get("id"), placeId));
        getEntityManager().createQuery(cu).executeUpdate();
    }

    @Override
    public void setPlaceTicketExpired(int placeId, boolean state) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<ParkingPlace> cu = cb.createCriteriaUpdate(ParkingPlace.class);
        Root<ParkingPlace> root = cu.from(ParkingPlace.class);
        cu.set(root.get("ticketExpireNotification"), state).where(cb.equal(root.get("id"), placeId));
        getEntityManager().createQuery(cu).executeUpdate();
    }
}
