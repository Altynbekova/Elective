package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.entity.Completion;
import com.epam.altynbekova.elective.entity.Course;
import com.epam.altynbekova.elective.entity.Student;
import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.ServiceException;
import com.epam.altynbekova.elective.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ShowStudentAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(ShowStudentAction.class);
    private static final String STUDENT_JSP = "student";
    private static final String STUDENT_PARAM = "student";
    private static final String COMPLETION_INFO_PARAM = "completionInfo";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        Student student = new Student();
        int courseId;
        int studentId;
        String courseIdParamValue = request.getParameter(COURSE_ID_PARAM);
        String studIdParamValue = request.getParameter(STUDENT_ID_PARAM);
        try {
            courseId = Integer.parseInt(courseIdParamValue);
            studentId = Integer.parseInt(studIdParamValue);
            student.setId(studentId);
            Map<Course, Completion> courseCompletionMap = new HashMap<>();
            courseCompletionMap.put(new Course(courseId), new Completion());
            student.setCourses(courseCompletionMap);

            StudentService studentService = new StudentService();
            student = studentService.findStudent(student);
            LOG.debug("Student found {}", student);

        } catch (NumberFormatException e) {
            LOG.error("Failed to parse params to int: courseId={}, studentId={}", courseIdParamValue, studIdParamValue, e);
            throw new ActionException(e);
        } catch (ServiceException e) {
            LOG.error("Cannot find course courseId={} completion info for student with studentId={}",
                    courseIdParamValue, studIdParamValue, e.getMessage(), e);
            throw new ActionException(e);

        }
        Iterator<Map.Entry<Course, Completion>> iterator;
        Completion completionInfo = new Completion();
        if (student != null) {
            iterator = student.getCourses().entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<Course, Completion> next = iterator.next();
                completionInfo = next.getValue();
            }
        }
        request.setAttribute(STUDENT_PARAM, student);
        request.setAttribute(COURSE_ID_PARAM, courseId);
        request.setAttribute(COMPLETION_INFO_PARAM, completionInfo);
        LOG.debug("Attributes were set into the request: {},{},{}", student, courseId, completionInfo);
        return STUDENT_JSP;
    }
}
