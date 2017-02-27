package com.epam.altynbekova.elective.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionPoolException extends Exception {
    public static final Logger LOG= LoggerFactory.getLogger(ConnectionPoolException.class);

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
        LOG.error(message, cause);
    }
}