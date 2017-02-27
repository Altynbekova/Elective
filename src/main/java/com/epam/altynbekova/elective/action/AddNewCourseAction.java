package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.entity.Course;
import com.epam.altynbekova.elective.entity.Lecturer;
import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.EntityExistsException;
import com.epam.altynbekova.elective.exception.ServiceException;
import com.epam.altynbekova.elective.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddNewCourseAction extends AbstractAction{
    private static final Logger LOG = LoggerFactory.getLogger(AddNewCourseAction.class);
    private static final String REDIRECT_TO_ADD_COURSE_FORM = "redirect:/do/?action=show-new-course-form";
    private static final String REDIRECT_TO_ADD_COURSE_RESULT ="redirect:/do/?action=show-add-course-result&result=";
    private static final String ADD_NEW_COURSE_FORM = "addNewCourse";
    private static final String COURSE_NAME_PARAM = "name";
    private static final String COURSE_DESCRIPTION_PARAM = "description";
    private static final String COURSE_EXISTS_PARAM = "courseExistsError";
    private static final String COURSE_EXISTS_MSG = "message.error.add.new.course.invalid";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String courseName = request.getParameter(COURSE_NAME_PARAM);
        String courseDescription = request.getParameter(COURSE_DESCRIPTION_PARAM);

        if(!validateForm(ADD_NEW_COURSE_FORM,request)){
            request.getSession().removeAttribute(COURSE_EXISTS_PARAM);
            return REDIRECT_TO_ADD_COURSE_FORM;
        }

        Course course= new Course();
        course.setName(courseName);
        course.setDescription(courseDescription);
        User user=(User)request.getSession().getAttribute(USER);
        Lecturer lecturer = new Lecturer(user.getId());
        course.setLecturer(lecturer);

        CourseService courseService = new CourseService();
        try {
            request.getSession().removeAttribute(COURSE_EXISTS_PARAM);
            courseService.addNewCourse(course);
            return REDIRECT_TO_ADD_COURSE_RESULT +SUCCESS;
        }
        catch (EntityExistsException e){
            LOG.error(e.getMessage(),e);
            request.getSession().setAttribute(COURSE_EXISTS_PARAM, COURSE_EXISTS_MSG);
            return REDIRECT_TO_ADD_COURSE_FORM;
        }
        catch (ServiceException e) {
            LOG.error("Lecturer {} cannot add new course",user,e.getMessage(),e);
            throw new ActionException(e);
        }
    }
}
