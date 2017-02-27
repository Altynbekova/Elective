package com.epam.altynbekova.elective.listener;

import com.epam.altynbekova.elective.connection_pool.ConnectionPool;
import com.epam.altynbekova.elective.dao.jdbc.JdbcDaoFactory;
import com.epam.altynbekova.elective.exception.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ConnectionPoolListener implements ServletContextListener {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPoolListener.class);
    private static ConnectionPool connectionPool;

    public static ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        connectionPool = new ConnectionPool();
        try {
            connectionPool.fillWithConnections();
        } catch (ConnectionPoolException e) {
            try {
                connectionPool.close();
            } catch (ConnectionPoolException e2) {
                LOG.error("Cannot close connection", e2);
            }
            LOG.error("Cannot fill connection pool with connections", e);
        }
        JdbcDaoFactory.setConnectionPool(connectionPool);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            connectionPool.close();
        } catch (ConnectionPoolException e) {
            LOG.error("Cannot close connection pool", e);
        }
    }
}
