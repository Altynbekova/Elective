package com.epam.altynbekova.elective.exception;

import java.sql.SQLException;

public class NotUniqueJdbcDaoException extends RuntimeException {
    public NotUniqueJdbcDaoException() {
    }

    public NotUniqueJdbcDaoException(String message) {
        super(message);
    }

    public NotUniqueJdbcDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUniqueJdbcDaoException(Throwable cause) {
        super(cause);
    }
}
