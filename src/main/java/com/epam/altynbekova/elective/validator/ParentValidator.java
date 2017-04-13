package com.epam.altynbekova.elective.validator;

public abstract class ParentValidator implements Validator {
    private String message;

    ParentValidator() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}