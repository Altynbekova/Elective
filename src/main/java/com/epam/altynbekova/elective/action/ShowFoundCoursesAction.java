package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowFoundCoursesAction extends AbstractAction {
    private static final String FOUND_COURSES_JSP = "found-courses";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return FOUND_COURSES_JSP;
    }
}
