package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowEvaluateFormAction extends AbstractAction {
    private static final String EVALUATE_STUDENT_FORM_JSP = "evaluate-student-form";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return EVALUATE_STUDENT_FORM_JSP;
    }
}
