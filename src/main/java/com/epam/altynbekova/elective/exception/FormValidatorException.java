package com.epam.altynbekova.elective.exception;

public class FormValidatorException extends Exception {

    public FormValidatorException(String message) {
        super(message);
    }

    public FormValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormValidatorException(Throwable cause) {
        super(cause);
    }
}
