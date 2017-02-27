package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowSignInFormAction extends AbstractAction {
    private static final String SIGN_IN_JSP = "sign-in";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return SIGN_IN_JSP;
    }
}
