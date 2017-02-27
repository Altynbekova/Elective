package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

public class ChangeLocaleAction extends AbstractAction {
    private static final String LOCALE_PARAM = "locale";
    private static final String ENGLISH_LOCALE = "en";
    private static final String RUSSIAN_LOCALE = "ru";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String locale = request.getParameter(LOCALE_PARAM);
        if (locale != null) {
            if (locale.equals(RUSSIAN_LOCALE) || locale.equals(ENGLISH_LOCALE))
                Config.set(request.getSession(), Config.FMT_LOCALE, new Locale(locale));
        }

        return REDIRECT + request.getHeader(REFERER);
    }
}
