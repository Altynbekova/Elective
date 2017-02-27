package com.epam.altynbekova.elective.action.signUp;

import com.epam.altynbekova.elective.entity.Lecturer;
import com.epam.altynbekova.elective.entity.Role;
import com.epam.altynbekova.elective.exception.EntityExistsException;
import com.epam.altynbekova.elective.exception.ServiceException;
import com.epam.altynbekova.elective.exception.SignUpActionException;
import com.epam.altynbekova.elective.service.LecturerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

class LecturerSignUpAction extends UserSignUpAction<Lecturer> {
    private static final String REDIRECT_TO_REGISTER_PAGE = "redirect:/do/?action=show-sign-up-form&role=lecturer";
    private static final String REGISTER_FORM = "lectRegister";
    private static final String JOB_TITLE_PARAM = "jobTitle";

    @Override
    protected String getFormName() {
        return REGISTER_FORM;
    }

    @Override
    protected String getRedirectPage() {
        return REDIRECT_TO_REGISTER_PAGE;
    }

    @Override
    protected Lecturer setRequestParametersTo(Lecturer lecturer, HttpServletRequest request) {
        lecturer=new Lecturer();
        lecturer.setRole(Role.LECTURER);
        lecturer.setFirstName(request.getParameter(FIRST_NAME_PARAM));
        lecturer.setLastName(request.getParameter(LAST_NAME_PARAM));
        lecturer.setJobTitle(request.getParameter(JOB_TITLE_PARAM));
        lecturer.setLogin(request.getParameter(LOGIN_PARAM));
        lecturer.setPassword(request.getParameter(PASSWORD_PARAM));
        return lecturer;
    }

    @Override
    protected Lecturer registerUser(Lecturer lecturer) throws ServiceException, EntityExistsException {
        try {
            LecturerService lecturerService = new LecturerService();
            return lecturerService.register(lecturer);
        }catch (EntityExistsException e){
            throw new EntityExistsException(e.getMessage(),e);
        }
    }
}
