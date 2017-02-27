package com.epam.altynbekova.elective.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionException extends Exception {
    private static final Logger LOG= LoggerFactory.getLogger(ActionException.class);

    public ActionException(String message) {
        super(message);
    }

    public ActionException(String message, Throwable cause) {
        super(message, cause);
        LOG.error(message, cause);
    }

    public ActionException(Throwable cause) {
        super(cause);
    }
}
