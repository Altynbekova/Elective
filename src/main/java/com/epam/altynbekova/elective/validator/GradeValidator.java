package com.epam.altynbekova.elective.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GradeValidator extends ParentValidator {
    private static final Logger LOG = LoggerFactory.getLogger(GradeValidator.class);

    private int minValue;
    private int maxValue;

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public Boolean isValid(String value) {
        boolean isValid;
        try {
            int grade = Integer.parseInt(value);
            isValid=(grade>=minValue&&grade<=maxValue);
        } catch (NumberFormatException e) {
            isValid = false;
            LOG.error("Entered grade {} is not correct", value);
        }
        return isValid;
    }

    @Override
    public String toString() {
        return "GradeValidator{" +
                "minValue=" + minValue +
                ", maxValue=" + maxValue +
                '}';
    }
}
