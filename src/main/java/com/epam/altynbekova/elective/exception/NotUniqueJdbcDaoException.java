package com.epam.altynbekova.elective.exception;

public class NotUniqueJdbcDaoException extends RuntimeException {

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
