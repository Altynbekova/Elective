package com.epam.altynbekova.elective.dao;

import com.epam.altynbekova.elective.dao.jdbc.JdbcDaoFactory;
import com.epam.altynbekova.elective.exception.DaoException;
import com.epam.altynbekova.elective.exception.JdbcDaoException;

public abstract class DaoFactory implements AutoCloseable{

    public static DaoFactory createJdbcFactory() throws DaoException {
        try {
            return new JdbcDaoFactory();
        } catch (JdbcDaoException e) {
            throw new DaoException("Jdbc connection error",e);
        }
    }

    public abstract UserDao getUserDao();
    public abstract StudentDao getStudentDao();
    public abstract LecturerDao getLecturerDao();
    public abstract CourseDao getCourseDao();

    public abstract void beginTransaction() throws DaoException;
    public abstract void rollbackTransaction() throws DaoException;
    public abstract void commitTransaction() throws DaoException;

    @Override
    public void close() throws DaoException {

    }
}
