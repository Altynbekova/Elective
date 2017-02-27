package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.entity.Course;
import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.ServiceException;
import com.epam.altynbekova.elective.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenRegistrationForCourseAction extends AbstractAction{
    private static final Logger LOG = LoggerFactory.getLogger(OpenRegistrationForCourseAction.class);
    private static final String REDIRECT_TO_CHANGE_STATUS_RESULT = "redirect:/do/?action=show-status-change-result&result=";
    private static final String STATUS = "status";
    private static final String COURSE = "course";
    private static final String SUCCESS="success";
    private static final String ERROR="error";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String statusParamValue = request.getParameter(STATUS);
        String courseIdParamValue=request.getParameter(COURSE_ID_PARAM);

        LOG.debug("request.getParameter(STATUS)={}",request.getParameter(STATUS));
        Course course;
        try {
            int courseId = Integer.parseInt(courseIdParamValue);
            boolean isAvailable = Boolean.parseBoolean(statusParamValue);

            CourseService courseService = new CourseService();
            course=courseService.changeStatus(isAvailable,courseId);
            request.setAttribute(COURSE, course);
            return REDIRECT_TO_CHANGE_STATUS_RESULT+SUCCESS;
        }catch (NumberFormatException e)
        {
            LOG.error("Cannot parse courseID={} to int",courseIdParamValue,e);
            return REDIRECT_TO_CHANGE_STATUS_RESULT+ERROR;
        } catch (ServiceException e) {
            LOG.error("Cannot set status of course with courseID={} to isAvailavle={}",
                    courseIdParamValue,statusParamValue,e.getMessage(),e);
            return REDIRECT_TO_CHANGE_STATUS_RESULT+ERROR;
        }
    }
}
