package com.epam.altynbekova.elective.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoException extends Exception {
    private  static final Logger LOG= LoggerFactory.getLogger(DaoException.class);
    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
        LOG.error(message, cause);
    }
}
