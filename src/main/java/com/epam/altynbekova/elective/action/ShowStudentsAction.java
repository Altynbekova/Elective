package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.entity.Student;
import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.ServiceException;
import com.epam.altynbekova.elective.service.StudentService;
import com.epam.altynbekova.elective.util.ActionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowStudentsAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(ShowStudentsAction.class);
    private static final String STUDENTS_JSP = "students";
    private static final String STUDENTS_NOT_FOUND = "studentsNotFound";
    private static final String STUDENTS_NOT_FOUND_MSG = "message.error.no.students";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {

        StudentService studentService = new StudentService();
        try {
            int courseId = Integer.parseInt(request.getParameter(ActionConstant.COURSE_ID_PARAM));
            List<Student> students = studentService.findStudents(courseId);

            if (students.size()==0)
                request.setAttribute(STUDENTS_NOT_FOUND, STUDENTS_NOT_FOUND_MSG);

            request.setAttribute(ActionConstant.STUDENTS_ATTRIBUTE, students);
            request.setAttribute(ActionConstant.COURSE_ID_PARAM,courseId);
            return STUDENTS_JSP;
        } catch (NumberFormatException e) {
            LOG.error("Course id parameter format error. Sent id={}", request.getParameter(ActionConstant.COURSE_ID_PARAM));
            throw new ActionException(e);
        } catch (ServiceException e) {
            LOG.error("Cannot get list of students registered for the course", e.getMessage(),e);
            throw new ActionException(e);
        }
    }
}
