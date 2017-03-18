package com.epam.altynbekova.elective.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyManagerException extends Exception {
    private static final Logger LOG= LoggerFactory.getLogger(PropertyManagerException.class);

    public PropertyManagerException(String message) {
        super(message);
    }

    public PropertyManagerException(String message, Throwable cause) {
        super(message, cause);
        LOG.error(message, cause);
    }
}
