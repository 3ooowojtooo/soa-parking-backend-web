package com.qaury.soa.parking.backend.web.database.api.dao;

import java.util.List;

public interface IBaseDAO<T> {

    T persist(T entity);

    T edit(T entity);

    T remove(T entity);

    List<T> findAll();

    T find(Integer id);

    void flush();
}