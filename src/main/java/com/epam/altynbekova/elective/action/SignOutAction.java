package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.altynbekova.elective.util.ActionConstant.REDIRECT_MAIN_PAGE;

public class SignOutAction extends AbstractAction {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        request.getSession().invalidate();

        return REDIRECT_MAIN_PAGE;
    }
}
