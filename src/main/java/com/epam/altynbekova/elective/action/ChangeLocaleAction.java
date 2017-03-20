package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.util.ActionConstant;

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

            String referer = request.getHeader(ActionConstant.REFERER);
            org.slf4j.LoggerFactory.getLogger(ChangeLocaleAction.class).info("{}={}", ActionConstant.REFERER, referer);
            return ActionConstant.REDIRECT+referer.substring(referer.lastIndexOf("/do/?action"));
        }
        return REDIRECT_MAIN_PAGE;
    }
}
