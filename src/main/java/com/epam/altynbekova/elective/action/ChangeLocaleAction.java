package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.util.ActionConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

import static com.epam.altynbekova.elective.util.ActionConstant.REDIRECT_MAIN_PAGE;

public class ChangeLocaleAction extends AbstractAction {
    private static final String LOCALE_PARAM = "locale";
    private static final String ENGLISH_LOCALE = "en";
    private static final String RUSSIAN_LOCALE = "ru";
    private static final String ACTION_PATH_PREFIX = "/do/?action";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String referer = request.getHeader(ActionConstant.REFERER);
        String locale = request.getParameter(LOCALE_PARAM);

        if (locale != null && (locale.equals(RUSSIAN_LOCALE) || locale.equals(ENGLISH_LOCALE))) {
            Config.set(request.getSession(), Config.FMT_LOCALE, new Locale(locale));

            if (referer.contains(ACTION_PATH_PREFIX))
                return ActionConstant.REDIRECT + referer.substring(referer.indexOf(ACTION_PATH_PREFIX));

            return REDIRECT_MAIN_PAGE;
        }

        return REDIRECT_MAIN_PAGE;
    }
}
