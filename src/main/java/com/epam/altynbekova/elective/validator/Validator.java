package com.epam.altynbekova.elective.validator;

import com.epam.altynbekova.elective.exception.ValidatorException;

public interface Validator {
    Boolean isValid(String parameter);

    Boolean isValid(String parameter, String otherParameter);

    Boolean isValid(Long parameter);

    String getMessage();

    void setMessage(String msg);
}
