package com.epam.altynbekova.elective.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionFactoryException extends Exception {
    private static final Logger LOG= LoggerFactory.getLogger(ActionFactoryException.class);
    public ActionFactoryException(String message) {
        super(message);
    }

    public ActionFactoryException(String message, Throwable cause) {
        super(message, cause);
        LOG.error(message, cause);
    }

    public ActionFactoryException(Exception e) {

        super(e);

    }
}
