package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.exception.ActionException;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class ShowSignUpFormAction extends AbstractAction {
    public static final String REGISTER_JSP = "sign-up";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return REGISTER_JSP;
    }
}
