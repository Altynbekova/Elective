package com.epam.altynbekova.elective.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcDaoException extends DaoException {

    public JdbcDaoException(String message) {
        super(message);
    }

    public JdbcDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
