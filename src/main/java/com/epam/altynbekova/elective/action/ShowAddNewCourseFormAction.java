package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowAddNewCourseFormAction extends AbstractAction {
    private static final String ADD_NEW_COURSE_JSP = "add-course";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return ADD_NEW_COURSE_JSP;
    }
}
