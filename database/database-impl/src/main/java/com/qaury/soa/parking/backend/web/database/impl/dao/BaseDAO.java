package com.qaury.soa.parking.backend.web.database.impl.dao;

import com.qaury.soa.parking.backend.web.database.api.dao.IBaseDAO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public abstract class BaseDAO<T> implements IBaseDAO<T> {

    private Class<T> entityClass;

    public BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    @Override
    public T persist(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public T edit(T entity) {
        getEntityManager().merge(entity);
        return entity;
    }

    @Override
    public T remove(T entity) {
        getEntityManager().remove(entity);
        return entity;
    }

    @Override
    public List<T> findAll() {
        Query query = getEntityManager().createQuery("SELECT e FROM " + entityClass.getName() + " e");
        List<T> result = (List<T>) query.getResultList();
        return result;
    }

    @Override
    public T find(Integer id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public void flush() {
        getEntityManager().flush();
    }
}
