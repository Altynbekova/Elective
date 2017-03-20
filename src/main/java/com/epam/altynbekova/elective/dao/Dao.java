package com.epam.altynbekova.elective.dao;

import com.epam.altynbekova.elective.entity.BaseEntity;
import com.epam.altynbekova.elective.exception.DaoException;

import java.util.List;

public interface Dao<T extends BaseEntity> {
    T save(T entity) throws DaoException;

    T findById(int id) throws DaoException;

    List<T> findAllById(int id) throws DaoException;

    T findByName(String entityName) throws DaoException;

    List<T> findAllByName(String entityName) throws DaoException;
}
