package com.epam.altynbekova.elective.validator;

public class ParentValidator implements Validator {
    private String message;

    ParentValidator() {
    }


    @Override
    public Boolean isValid(String parameter) {
        return null;
    }

    @Override
    public Boolean isValid(String parameter, String otherParameter) {
        return null;
    }

    @Override
    public Boolean isValid(Long parameter) {
        return null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}