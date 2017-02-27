package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.ServiceException;
import com.epam.altynbekova.elective.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(SignInAction.class);
    private static final String REDIRECT_TO_SIGN_IN_FORM = "redirect:/do/?action=show-sign-in-form";
    private static final String REDIRECT_TO_SIGN_IN_SUCCESS = "redirect:/do/?action=sign-in-success";
    private static final String SIGN_IN_ERROR = "signInError";
    private static final String INCORRECT_PASSWORD_MSG = "message.error.password";
    private static final String USER_NOT_FOUND_MSG = "message.error.user.not.found";
    private static final String SIGN_IN_FORM = "authorization";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        if (!validateForm(SIGN_IN_FORM, request))
            return REDIRECT_TO_SIGN_IN_FORM;

        String login = request.getParameter(LOGIN_PARAM);
        request.getSession().setAttribute(LOGIN_PARAM, login);
        String password = request.getParameter(PASSWORD_PARAM);

        UserService userService = new UserService();
        User userFromDb;
        try {
            userFromDb = userService.getUserByLogin(login);
        } catch (ServiceException e) {
            LOG.error(e.getMessage(),e);
            request.getSession().setAttribute(SIGN_IN_ERROR, USER_NOT_FOUND_MSG);
            return REDIRECT_TO_SIGN_IN_FORM;
        }
        LOG.debug("Found user by login: {}", userFromDb);
        String hashedPassword = userFromDb.getPassword();
        if (!BCrypt.checkpw(password, hashedPassword)) {
            request.getSession().setAttribute(SIGN_IN_ERROR, INCORRECT_PASSWORD_MSG);
            return REDIRECT_TO_SIGN_IN_FORM;
        } else {
            request.getSession().removeAttribute(SIGN_IN_ERROR);
            request.getSession().setAttribute(USER, userFromDb);
            return REDIRECT_TO_SIGN_IN_SUCCESS;
        }
    }
}
