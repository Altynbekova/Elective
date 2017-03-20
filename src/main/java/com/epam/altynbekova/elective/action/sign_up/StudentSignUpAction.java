package com.epam.altynbekova.elective.action.sign_up;


import com.epam.altynbekova.elective.entity.Role;
import com.epam.altynbekova.elective.entity.Student;
import com.epam.altynbekova.elective.exception.EntityExistsException;
import com.epam.altynbekova.elective.exception.ServiceException;
import com.epam.altynbekova.elective.service.StudentService;
import com.epam.altynbekova.elective.util.ActionConstant;

import javax.servlet.http.HttpServletRequest;

class StudentSignUpAction extends UserSignUpAction<Student> {
    private static final String REDIRECT_TO_REGISTER_PAGE = "redirect:/do/?action=show-sign-up-form&role=student";
    private static final String REGISTER_FORM = "studRegister";

    @Override
    protected String getFormName() {
        return REGISTER_FORM;
    }

    @Override
    protected String getRedirectPage() {
        return REDIRECT_TO_REGISTER_PAGE;
    }

    @Override
    protected Student setRequestParametersTo(Student student, HttpServletRequest request) {
        student=new Student();
        student.setLogin(request.getParameter(ActionConstant.LOGIN_PARAM));
        student.setPassword(request.getParameter(ActionConstant.PASSWORD_PARAM));
        student.setFirstName(request.getParameter(ActionConstant.FIRST_NAME_PARAM));
        student.setLastName(request.getParameter(ActionConstant.LAST_NAME_PARAM));
        student.setRole(Role.STUDENT);
        return student;
    }

    @Override
    protected Student registerUser(Student student) throws ServiceException {
        try {StudentService studentService = new StudentService();
        return studentService.register(student);
        }catch (EntityExistsException e){
            throw new EntityExistsException(e.getMessage(),e);
        }
    }
}
