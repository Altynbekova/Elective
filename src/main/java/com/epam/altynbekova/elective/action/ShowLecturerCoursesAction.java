package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.entity.Course;
import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.ServiceException;
import com.epam.altynbekova.elective.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowLecturerCoursesAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(ShowLecturerCoursesAction.class);
    private static final String LECTURER_COURSES_JSP = "lecturer-courses";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        User user = (User) request.getSession().getAttribute(USER);
        CourseService courseService = new CourseService();
        try {
            final List<Course> courses = courseService.getCoursesFor(user);
            request.setAttribute(COURSES, courses);
        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            throw new ActionException(e);
        }
        return LECTURER_COURSES_JSP;
    }
}
