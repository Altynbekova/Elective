package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowEvaluateResultAction extends AbstractAction {
    private static final String EVALUATE_STUDENT_RESULT_JSP = "evaluate-student-result";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return EVALUATE_STUDENT_RESULT_JSP;
    }
}
