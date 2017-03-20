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

public class OpenRegistrationForCourseAction extends AbstractAction{
    private static final Logger LOG = LoggerFactory.getLogger(OpenRegistrationForCourseAction.class);
    private static final String REDIRECT_TO_CHANGE_STATUS_RESULT = "redirect:/do/?action=show-status-change-result&result=";
    private static final String STATUS = "status";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String statusParamValue = request.getParameter(STATUS);
        String courseIdParamValue=request.getParameter(ActionConstant.COURSE_ID_PARAM);

        LOG.debug("request.getParameter(STATUS)={}",request.getParameter(STATUS));
        Course course;
        try {
            int courseId = Integer.parseInt(courseIdParamValue);
            boolean isAvailable = Boolean.parseBoolean(statusParamValue);

            CourseService courseService = new CourseService();
            course=courseService.changeStatus(isAvailable,courseId);
            request.setAttribute(ActionConstant.COURSE_ATTRIBUTE, course);
            return REDIRECT_TO_CHANGE_STATUS_RESULT+ActionConstant.SUCCESS;
        }catch (NumberFormatException e){
            LOG.error("Cannot parse courseID={} to int",courseIdParamValue,e);
            return REDIRECT_TO_CHANGE_STATUS_RESULT+ActionConstant.ERROR;
        } catch (ServiceException e) {
            LOG.error("Cannot set status of course with courseID={} to isAvailable={}",
                    courseIdParamValue,statusParamValue,e.getMessage(),e);
            return REDIRECT_TO_CHANGE_STATUS_RESULT+ActionConstant.ERROR;
        }
    }
}
