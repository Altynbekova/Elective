package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowSignUpSuccessAction extends AbstractAction {
    private static final String SIGN_UP_SUCCESS_JSP = "sign-up-success";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return SIGN_UP_SUCCESS_JSP;
    }
}
