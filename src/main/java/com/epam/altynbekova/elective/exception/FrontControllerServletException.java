package com.epam.altynbekova.elective.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrontControllerServletException extends Exception {
    private static final Logger LOG= LoggerFactory.getLogger(FrontControllerServletException.class);

    public FrontControllerServletException(String message) {
        super(message);
    }

    public FrontControllerServletException(String message, Throwable cause) {
        super(message, cause);
        LOG.error(message,cause);
    }
}
