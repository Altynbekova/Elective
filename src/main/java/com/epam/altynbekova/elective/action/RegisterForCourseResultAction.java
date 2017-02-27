package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterForCourseResultAction extends AbstractAction {
    private static final String REGISTER_COURSE_RESULT_JSP = "register-for-course-result";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return REGISTER_COURSE_RESULT_JSP;
    }
}
