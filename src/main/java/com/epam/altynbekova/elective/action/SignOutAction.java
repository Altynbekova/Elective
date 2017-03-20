package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOutAction extends AbstractAction {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        request.getSession().invalidate();

        return REDIRECT_MAIN_PAGE;
    }
}
