package com.epam.altynbekova.elective.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExValidator extends ParentValidator implements Validator {

    private Pattern pattern;

    public RegExValidator() {
    }

    public void setRegex(String regex) {
        pattern = Pattern.compile(regex);
    }

    public Boolean isValid(String field){
        Matcher matcher = pattern.matcher(field);
        return matcher.matches();
    }

    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return "RegExValidator{" +
                "pattern=" + pattern +
                '}';
    }
}
