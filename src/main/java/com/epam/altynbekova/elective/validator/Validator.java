package com.epam.altynbekova.elective.validator;

public interface Validator {
    Boolean isValid(String parameter);

    String getMessage();

    void setMessage(String msg);
}
