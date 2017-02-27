package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOutAction extends AbstractAction {
    public static final String REDIRECT_TO_MAIN_PAGE = "redirect:/do/?action=show-main-page";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        request.getSession().invalidate();

        return REDIRECT_TO_MAIN_PAGE;
    }
}
