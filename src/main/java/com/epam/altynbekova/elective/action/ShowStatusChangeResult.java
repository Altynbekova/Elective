package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowStatusChangeResult extends AbstractAction {
    private static final String STATUS_CHANGE_RESULT_JSP = "change-status-result";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return STATUS_CHANGE_RESULT_JSP;
    }
}
