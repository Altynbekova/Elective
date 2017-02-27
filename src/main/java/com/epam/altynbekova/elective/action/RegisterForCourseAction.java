package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.EntityExistsException;
import com.epam.altynbekova.elective.exception.ServiceException;
import com.epam.altynbekova.elective.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterForCourseAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterForCourseAction.class);
    private static final String REDIRECT_TO_REG_FOR_COURSE_RESULT = "redirect:/do/?action=register-for-course-result&result=";
    private static final String REG_FOR_COURSE_ERROR_ATTRIBUTE = "registerError";
    private static final String REG_FOR_COURSE_ERROR_MSG = "message.error.register.for.course.invalid";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        User user = (User) request.getSession().getAttribute(USER);
        String courseIdParam = request.getParameter(COURSE_ID_PARAM);

        try {
            StudentService studentService = new StudentService();
            int courseId = Integer.parseInt(courseIdParam);
            studentService.registerForCourse(user.getId(), courseId);
            request.getSession().removeAttribute(REG_FOR_COURSE_ERROR_ATTRIBUTE);
            return REDIRECT_TO_REG_FOR_COURSE_RESULT + SUCCESS;
        } catch (NumberFormatException e) {
            LOG.error("Cannot parse parameter {} to int", courseIdParam, e);
            throw new ActionException(e);
        } catch (EntityExistsException e) {
            request.getSession().setAttribute(REG_FOR_COURSE_ERROR_ATTRIBUTE, REG_FOR_COURSE_ERROR_MSG);
            return REDIRECT_TO_REG_FOR_COURSE_RESULT + ERROR;
        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            throw new ActionException(e);
        }
    }
}
