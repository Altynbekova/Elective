package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.entity.Completion;
import com.epam.altynbekova.elective.entity.Course;
import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.ServiceException;
import com.epam.altynbekova.elective.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ShowStudentCoursesAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(ShowStudentCoursesAction.class);
    private static final String STUDENT_COURSES_JSP = "student-courses";
    private static final String IS_COMPLETE_PARAM = "isComplete";
    private static final boolean IS_COMPLETE_DEFAULT_VALUE = false;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String isCompleteParamValue = request.getParameter(IS_COMPLETE_PARAM);
        boolean isComplete = IS_COMPLETE_DEFAULT_VALUE;
        if (isCompleteParamValue != null)
            isComplete = Boolean.parseBoolean(isCompleteParamValue);

        User user = (User) request.getSession().getAttribute(USER);
        CourseService courseService = new CourseService();
        try {
            Map<Course, Completion> courses = courseService.getStudentCourses(user.getId(), isComplete);
            request.setAttribute(COURSES, courses);
        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            throw new ActionException(e);
        }
        return STUDENT_COURSES_JSP;
    }
}
