package com.epam.altynbekova.elective.connection_pool;

import com.epam.altynbekova.elective.exception.ConnectionPoolException;
import com.epam.altynbekova.elective.exception.PropertyManagerException;
import com.epam.altynbekova.elective.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPool.class);
    private static final String DB_PROPERTIES_FILE_NAME = "db.properties";
    private static final String DRIVER_KEY = "db.connection.driver";
    private static final String URL_KEY = "db.connection.url";
    private static final String LOGIN_KEY = "db.connection.user.login";
    private static final String PASSWORD_KEY = "db.connection.user.password";
    private static final String MAXSIZE_KEY = "db.connectionPool.maxSize";
    private static final String TIME_OUT_KEY = "db.connectionPool.timeout";
    private static final int DEFAULT_POOL_START_SIZE = 6;
    private static final int TIMEOUT_5_SEC_CHECK_CONNECTION = 5;
    private int connectionsQuantity;
    private String url;
    private String login;
    private String password;
    private int poolMaxSize;
    private long timeout;
    private BlockingQueue<Connection> freeConnections;

    public void fillWithConnections() throws ConnectionPoolException {
        try {
            PropertyManager propertyManager = new PropertyManager(DB_PROPERTIES_FILE_NAME);
            String drivers = propertyManager.getProperty(DRIVER_KEY);

            try {
                Class.forName(drivers);
            } catch (ClassNotFoundException e) {
                throw new ConnectionPoolException(MessageFormat.format("Cannot find class {0}", drivers), e);
            }
            url = propertyManager.getProperty(URL_KEY);
            login = propertyManager.getProperty(LOGIN_KEY);
            password = propertyManager.getProperty(PASSWORD_KEY);
            poolMaxSize = Integer.parseInt(propertyManager.getProperty(MAXSIZE_KEY));
            timeout = Long.parseLong(propertyManager.getProperty(TIME_OUT_KEY));
        } catch (PropertyManagerException e) {
            throw new ConnectionPoolException(MessageFormat.format(
                    "Cannot get properties from file {0}", DB_PROPERTIES_FILE_NAME), e);
        }
        freeConnections = new ArrayBlockingQueue<>(poolMaxSize);

        connectionsQuantity = 0;
        for (int i = 0; i < DEFAULT_POOL_START_SIZE; i++) {
            Connection connection = getNewConnection();
            if (connection != null)
                freeConnections.offer(connection);
        }
        LOG.debug("Connection pool with size={} has been created.", freeConnections.size());
    }

    public synchronized Connection getConnection() throws ConnectionPoolException {
        Connection connection;
        if (connectionsQuantity < poolMaxSize) {
            connection = freeConnections.poll();
            if (connection == null) {
                connection = getNewConnection();
            }

            try {
                if (!connection.isValid(TIMEOUT_5_SEC_CHECK_CONNECTION)) {
                    LOG.debug("Connection taken from the pool is invalid. Starting to fill connection pool again...");
                    close();
                    fillWithConnections();
                    LOG.debug("Connection pool filled");
                }
            } catch (SQLException e) {
                throw new ConnectionPoolException(e.getMessage(), e);
            }
        } else {
            try {
                connection = freeConnections.poll(timeout, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new ConnectionPoolException(
                        "Thread has been interrupted while waiting for a connection to become available", e);
            }
            if (connection == null) {
                throw new ConnectionPoolException("No free connections are available");
            }
            try {
                if (!connection.isValid(TIMEOUT_5_SEC_CHECK_CONNECTION)) {
                    close();
                    fillWithConnections();
                }
            } catch (SQLException e) {
                LOG.error("Connection retrieved from the pool is invalid", e.getMessage(), e);
                throw new ConnectionPoolException("Connection retrieved from the pool is invalid", e);
            }

        }
        return connection;
    }

    public void setConnectionToPool(Connection returnedConnection) throws ConnectionPoolException {

        try {
            if (returnedConnection.isValid(TIMEOUT_5_SEC_CHECK_CONNECTION)) {
                LOG.debug("Trying to set connection to the pool...");
                freeConnections.offer(returnedConnection);
                LOG.debug("Connection has been set to the pool");
            }
        } catch (SQLException e) {
            LOG.error("Cannot return connection back to the pool", e.getMessage(), e);
            throw new ConnectionPoolException("Cannot return connection back to the pool", e);
        }
    }

    public void close() throws ConnectionPoolException {
        for (Connection con : freeConnections)
            try {
                if (!con.isClosed()) {
                    con.close();
                }

            } catch (SQLException e) {
                throw new ConnectionPoolException("Cannot close connection", e);
            }
        freeConnections.clear();
        LOG.debug("Connection pool has been closed.");
    }

    private Connection getNewConnection() throws ConnectionPoolException {

        Connection connection;
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            throw new ConnectionPoolException(
                    "Cannot get connection. DB access error occurred or the url is invalid.", e);
        }
        connectionsQuantity++;

        return connection;
    }
}

