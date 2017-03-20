package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowMainPageAction extends AbstractAction {
    private static final String INDEX_JSP = "index";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return INDEX_JSP;
    }
}
