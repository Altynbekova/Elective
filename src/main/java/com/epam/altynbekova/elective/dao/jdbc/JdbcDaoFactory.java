package com.epam.altynbekova.elective.dao.jdbc;

import com.epam.altynbekova.elective.connection_pool.ConnectionPool;
import com.epam.altynbekova.elective.dao.*;
import com.epam.altynbekova.elective.exception.ConnectionPoolException;
import com.epam.altynbekova.elective.exception.JdbcDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoFactory extends DaoFactory {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcDaoFactory.class);
    private static final int TIMEOUT_5_SEC_CHECK_CONNECTION = 5;
    private static ConnectionPool connectionPool;
    private Connection connection;

    public JdbcDaoFactory() throws JdbcDaoException {
        try {
            this.connection = connectionPool.getConnection();
        } catch (ConnectionPoolException e) {
            LOG.error("Cannot get connection from connection pool", e.getMessage(), e);
            throw new JdbcDaoException("Cannot get connection from connection pool", e);
        }
    }

    public static void setConnectionPool(ConnectionPool connectionPool) {
        JdbcDaoFactory.connectionPool = connectionPool;
    }

    @Override
    public UserDao getUserDao() {
        return new JdbcUserDao(connection);
    }

    @Override
    public StudentDao getStudentDao() {
        return new JdbcStudentDao(connection);
    }

    @Override
    public LecturerDao getLecturerDao() {
        return new JdbcLecturerDao(connection);
    }

    @Override
    public CourseDao getCourseDao() {
        return new JdbcCourseDao(connection);
    }

    @Override
    public void beginTransaction() throws JdbcDaoException {
        try {
            if ((!connection.isClosed()) && (connection.getAutoCommit())) {
                connection.setAutoCommit(false);
                LOG.debug("beginTransaction()");
            }
        } catch (SQLException e) {
            throw new JdbcDaoException("Cannot begin transaction", e);
        }
    }

    @Override
    public void rollbackTransaction() throws JdbcDaoException {
        try {
            if ((!connection.isClosed()) && (!connection.getAutoCommit())) {
                connection.rollback();
                connection.setAutoCommit(true);
                LOG.debug("rollbackTransaction()");
            }
        } catch (SQLException e) {
            throw new JdbcDaoException("Cannot rollback transaction", e);
        }
    }

    @Override
    public void commitTransaction() throws JdbcDaoException {
        try {
            if ((!connection.isClosed()) && (!connection.getAutoCommit())) {
                connection.commit();
                LOG.debug("Transaction commit.");
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new JdbcDaoException("Cannot commit transaction", e);
        }
    }

    @Override
    public void close() throws JdbcDaoException {
        try {
            if (!connection.isClosed() || connection.isValid(TIMEOUT_5_SEC_CHECK_CONNECTION))
                connectionPool.setConnectionToPool(connection);
        } catch (SQLException | ConnectionPoolException e) {
            throw new JdbcDaoException("Cannot return connection to connection pool", e);
        }
    }
}
