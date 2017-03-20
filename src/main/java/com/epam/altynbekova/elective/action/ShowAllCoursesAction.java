package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.entity.Course;
import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.ServiceException;
import com.epam.altynbekova.elective.service.CourseService;
import com.epam.altynbekova.elective.util.ActionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAllCoursesAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(ShowAllCoursesAction.class);
    private static final String ALL_COURSES_JSP = "all-courses";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        CourseService courseService = new CourseService();
        try {
            final List<Course> courses = courseService.getAllCourses();
            request.setAttribute(ActionConstant.COURSES_ATTRIBUTE, courses);
        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            return ALL_COURSES_JSP;
        }
        return ALL_COURSES_JSP;
    }
}
