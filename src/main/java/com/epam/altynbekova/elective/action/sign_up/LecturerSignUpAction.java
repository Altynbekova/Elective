package com.epam.altynbekova.elective.action.sign_up;

import com.epam.altynbekova.elective.entity.Lecturer;
import com.epam.altynbekova.elective.entity.Role;
import com.epam.altynbekova.elective.exception.EntityExistsException;
import com.epam.altynbekova.elective.exception.ServiceException;
import com.epam.altynbekova.elective.service.LecturerService;
import com.epam.altynbekova.elective.util.ActionConstant;

import javax.servlet.http.HttpServletRequest;

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
        lecturer.setFirstName(request.getParameter(ActionConstant.FIRST_NAME_PARAM));
        lecturer.setLastName(request.getParameter(ActionConstant.LAST_NAME_PARAM));
        lecturer.setJobTitle(request.getParameter(JOB_TITLE_PARAM));
        lecturer.setLogin(request.getParameter(ActionConstant.LOGIN_PARAM));
        lecturer.setPassword(request.getParameter(ActionConstant.PASSWORD_PARAM));
        return lecturer;
    }

    @Override
    protected Lecturer registerUser(Lecturer lecturer) throws ServiceException {
        try {
            LecturerService lecturerService = new LecturerService();
            return lecturerService.register(lecturer);
        }catch (EntityExistsException e){
            throw new EntityExistsException(e.getMessage(),e);
        }
    }
}
