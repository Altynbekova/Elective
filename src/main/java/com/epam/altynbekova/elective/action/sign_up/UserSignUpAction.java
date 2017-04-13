package com.epam.altynbekova.elective.action.sign_up;

import com.epam.altynbekova.elective.action.AbstractAction;
import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.*;
import com.epam.altynbekova.elective.util.ActionConstant;
import com.epam.altynbekova.elective.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

abstract class UserSignUpAction<T extends User> extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(UserSignUpAction.class);
    private static final String REDIRECT_REGISTER_SUCCESS = "redirect:/do/?action=sign-up-success";

    UserSignUpAction() {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws SignUpActionException {

        String login = request.getParameter(ActionConstant.LOGIN_PARAM);
        request.getSession().setAttribute(ActionConstant.LOGIN_PARAM, login);

        boolean formIsValid;
        try {
            FormValidator validator = new FormValidator();
            Map<String, List<String>> fieldErrors = validator.validate(getFormName(), request);
            formIsValid = validator.isValid(request, fieldErrors);
        } catch (FormValidatorException e) {
            LOG.error(e.getMessage(),e);
            throw new SignUpActionException(MessageFormat.format
                    ("Cannot create validator for '{0}' form", getFormName()), e);
        }
        if (!formIsValid)
            return getRedirectPage();

        T user = null;
        user = setRequestParametersTo(user, request);

        try {
            final T registeredUser = registerUser(user);
            LOG.debug("{} has been inserted into database.", registeredUser);
        } catch (EntityExistsException e) {
            throw new UserExistsException(e.getMessage(), e);
        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            throw new SignUpActionException(e);
        }
        request.getSession().removeAttribute(ActionConstant.REGISTER_ERROR_ATTRIBUTE);
        return REDIRECT_REGISTER_SUCCESS;
    }

    protected abstract String getFormName();

    protected abstract String getRedirectPage();

    protected abstract T setRequestParametersTo(T user, HttpServletRequest request);

    protected abstract T registerUser(T user) throws ServiceException;
}
