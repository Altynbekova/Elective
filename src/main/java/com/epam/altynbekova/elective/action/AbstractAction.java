package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.FormValidatorException;
import com.epam.altynbekova.elective.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public abstract class AbstractAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractAction.class);

    protected boolean validateForm(String formName, HttpServletRequest request) throws ActionException {
        try {
            FormValidator validator = new FormValidator();
            Map<String, List<String>> fieldErrors = validator.validate(formName, request);
            return validator.isValid(request, fieldErrors);
        } catch (FormValidatorException e) {
            LOG.error(e.getMessage(), e);
            throw new ActionException("Cannot create validator with data from validator properties file", e);
        }
    }
}
