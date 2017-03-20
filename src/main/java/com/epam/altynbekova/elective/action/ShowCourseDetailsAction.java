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

public class ShowCourseDetailsAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(ShowCourseDetailsAction.class);
    private static final String COURSE_DETAILS_JSP = "course-details";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        try {
            int courseId = Integer.parseInt(request.getParameter(ActionConstant.COURSE_ID_PARAM));
            CourseService courseService = new CourseService();
            Course course = courseService.getCourseById(courseId);
            LOG.debug("Course found by id {}", course);

            request.getSession().setAttribute(ActionConstant.COURSE_ATTRIBUTE, course);
            return COURSE_DETAILS_JSP;
        } catch (NumberFormatException e) {
            LOG.error("Course id parameter format error. Sent id={}",request.getParameter(ActionConstant.COURSE_ID_PARAM));
            throw new ActionException(e);
        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            throw new ActionException(e);
        }
    }
}
