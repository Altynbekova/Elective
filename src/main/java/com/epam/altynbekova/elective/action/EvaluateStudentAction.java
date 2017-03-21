package com.epam.altynbekova.elective.action;

import com.epam.altynbekova.elective.entity.Completion;
import com.epam.altynbekova.elective.entity.Course;
import com.epam.altynbekova.elective.entity.Student;
import com.epam.altynbekova.elective.exception.ActionException;
import com.epam.altynbekova.elective.exception.ServiceException;
import com.epam.altynbekova.elective.service.StudentService;
import com.epam.altynbekova.elective.util.ActionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class EvaluateStudentAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(EvaluateStudentAction.class);
    private static final String REDIRECT_TO_EVALUATE_FORM = "redirect:/do/?action=show-evaluate-student-form";
    private static final String EVALUATE_STUDENT_FORM = "evaluate";
    private static final String STUDENT_ID = "&studentId=";
    private static final String COURSE_ID = "&courseId=";
    private static final String GRADE_PARAM = "grade";
    private static final String FEEDBACK_PARAM = "feedback";
    private static final String REDIRECT_EVALUATE_RESULT_SUCCESS =
            "redirect:/do/?action=show-evaluate-student-result&result=success";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String studIdParamValue = request.getParameter(ActionConstant.STUDENT_ID_PARAM);
        String courseIdParamValue = request.getParameter(ActionConstant.COURSE_ID_PARAM);

        if (!validateForm(EVALUATE_STUDENT_FORM, request))
            return REDIRECT_TO_EVALUATE_FORM +
                    STUDENT_ID + studIdParamValue +
                    COURSE_ID + courseIdParamValue;

        Student evaluatedStudent;
        try {
            int courseId = Integer.parseInt(courseIdParamValue);
            int studentId = Integer.parseInt(studIdParamValue);

            Student student = new Student();
            student.setId(studentId);
            Map<Course, Completion> courseCompletionMap = new HashMap<>();
            Completion completionInfo = new Completion();
            completionInfo.setGrade(Byte.parseByte(request.getParameter(GRADE_PARAM)));
            completionInfo.setFeedback(request.getParameter(FEEDBACK_PARAM));
            courseCompletionMap.put(new Course(courseId), completionInfo);
            student.setCourses(courseCompletionMap);

            StudentService studentService = new StudentService();
            evaluatedStudent = studentService.evaluateStudent(student);
        } catch (NumberFormatException e) {
            LOG.error("Invalid number parameter courseId={} and/or studentId={}",
                    courseIdParamValue, studIdParamValue, e.getMessage(), e);
            throw new ActionException(e);
        } catch (ServiceException e) {
            LOG.error("Cannot update course {}={} completion info for student with {}={}",
                    ActionConstant.COURSE_ID_PARAM, courseIdParamValue,
                    ActionConstant.STUDENT_ID_PARAM, studIdParamValue, e.getMessage(), e);
            throw new ActionException(e);

        }
        request.getSession().setAttribute(ActionConstant.COURSE_ID_PARAM, evaluatedStudent.getCourses().keySet().iterator().next().getId());
        request.getSession().setAttribute(ActionConstant.STUDENT_ID_PARAM, evaluatedStudent.getId());

        return REDIRECT_EVALUATE_RESULT_SUCCESS;
    }
}
