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

public class FindCourseAction extends AbstractAction {
    private static final String REDIRECT_TO_FOUND_COURSES = "redirect:/do/?action=show-found-courses&result=";
    private static final String COURSE_NAME_PARAM = "courseName";
    private static final Logger LOG = LoggerFactory.getLogger(FindCourseAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        request.getSession().removeAttribute(ActionConstant.COURSES_ATTRIBUTE);

        String courseName = request.getParameter(COURSE_NAME_PARAM);
        CourseService courseService = new CourseService();
        try {
            List<Course> courses = courseService.getCoursesByName(courseName);
            request.getSession().setAttribute(ActionConstant.COURSES_ATTRIBUTE, courses);
            return REDIRECT_TO_FOUND_COURSES +ActionConstant.SUCCESS;

        } catch (ServiceException e) {
            LOG.error("Cannot find any course", e.getMessage(),e);
            throw new ActionException(e);
        }
    }
}
