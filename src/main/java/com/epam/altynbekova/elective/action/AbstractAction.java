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
    protected static final String REFERER = "referer";
    protected static final String REDIRECT = "redirect:";
    protected static final String FIRST_NAME_PARAM = "firstName";
    protected static final String LAST_NAME_PARAM = "lastName";
    protected static final String LOGIN_PARAM = "login";
    protected static final String PASSWORD_PARAM = "password";
    protected static final String COURSE_ID_PARAM = "courseId";
    protected static final String STUDENT_ID_PARAM = "studentId";
    protected static final String USER = "user";
    protected static final String COURSES = "courses";
    protected static final String SUCCESS = "success";
    protected static final String ERROR = "error";
    protected static final String REGISTER_ERROR_ATTRIBUTE = "registerErrors";
    protected static final String REGISTER_ERROR_MSG = "message.error.login.exists";

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
