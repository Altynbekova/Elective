package com.epam.altynbekova.elective.exception;

public class SignUpActionException extends ActionException {
    public SignUpActionException(String message) {
        super(message);
    }

    public SignUpActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignUpActionException(Throwable cause) {
        super(cause);
    }
}
