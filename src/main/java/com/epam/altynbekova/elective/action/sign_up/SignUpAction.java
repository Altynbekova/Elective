package com.epam.altynbekova.elective.action.sign_up;

import com.epam.altynbekova.elective.action.AbstractAction;
import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.SignUpActionException;
import com.epam.altynbekova.elective.exception.UserExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(SignUpAction.class);
    private static final String REDIRECT_TO_REGISTER_PAGE = "redirect:/do/?action=show-sign-up-form&role=";
    private static final String ROLE_PARAM = "role";
    private static final String LECTURER = "lecturer";
    private static final String STUDENT = "student";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        SignUpActionFactory factory = new SignUpActionFactory();
        String redirectToRegister=REDIRECT_TO_REGISTER_PAGE;
        String view;
        try {
            if (LECTURER.equals(request.getParameter(ROLE_PARAM))) {
                redirectToRegister+= LECTURER;
                LecturerSignUpAction lecturerSignUpAction = factory.getLecturerSignUpAction();
                view = lecturerSignUpAction.execute(request, response);
            } else {
                redirectToRegister+= STUDENT;
                StudentSignUpAction studentSignUpAction = factory.getStudentSignUpAction();
                view = studentSignUpAction.execute(request, response);
            }
        }catch (UserExistsException e){
            LOG.error(e.getMessage(), e);
            request.getSession().setAttribute(REGISTER_ERROR_ATTRIBUTE, REGISTER_ERROR_MSG);
            return redirectToRegister;

        }catch (SignUpActionException e){
            LOG.error("Cannot register user in system", e.getMessage(),e);
            throw new ActionException(e);
        }
        return view;
    }
}
