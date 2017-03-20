package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.FormValidatorException;
import com.epam.altynbekova.elective.validator.FormValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public abstract class AbstractAction implements Action {
    protected static final String REDIRECT_MAIN_PAGE = "redirect:/do/?action=show-main-page";
    protected static final String REDIRECT_REGISTER_SUCCESS = "redirect:/do/?action=sign-up-success";

    protected boolean validateForm(String formName, HttpServletRequest request) throws ActionException {
        try {
            FormValidator validator = new FormValidator();
            Map<String, List<String>> fieldErrors = validator.validate(formName, request);
            return validator.isValid(request, fieldErrors);
        } catch (FormValidatorException e) {
            throw new ActionException("Cannot create validator with data from validator properties file", e);
        }
    }
}
