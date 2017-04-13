package com.epam.altynbekova.elective.dao.jdbc;

import com.epam.altynbekova.elective.dao.StudentDao;
import com.epam.altynbekova.elective.entity.Completion;
import com.epam.altynbekova.elective.entity.Course;
import com.epam.altynbekova.elective.entity.Student;
import com.epam.altynbekova.elective.exception.DaoException;
import com.epam.altynbekova.elective.exception.JdbcDaoException;
import com.epam.altynbekova.elective.exception.NotUniqueJdbcDaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

class JdbcStudentDao extends JdbcDao<Student> implements StudentDao {
    private static final String INSERT_QUERY_KEY = "insert.user.student";
    private static final String INSERT_REF_QUERY_KEY = "insert.student.reference";
    private static final String FIND_STUDENTS_BY_COURSE_QUERY_KEY = "find.current.students.by.course.id";
    private static final String UPDATE_STUDENT_COURSE_KEY = "update.student.course";
    private static final String FIND_STUDENT_BY_COURSE_QUERY_KEY = "find.student.by.course.id";
    private static final String INSERT_COURSE_FOR_STUD_KEY = "insert.course.for.student";
    private static final String STUDENT_ID_COLUMN = "STUDENT_COURSE.STUDENT_ID";
    private static final String IS_COMPLETE_COLUMN = "IS_COMPLETE";
    private static final String GRADE_COLUMN = "GRADE";
    private static final String FEEDBACK_COLUMN = "FEEDBACK";
    private static final String COURSE_ID_COLUMN = "COURSE_ID";

    JdbcStudentDao(Connection connection) {
        super(connection);
    }

    @Override
    protected void setFieldsForInsertTo(PreparedStatement ps, Student entity) throws JdbcDaoException {
        try {
            ps.setString(INDEX_1, entity.getFirstName());
            ps.setString(INDEX_2, entity.getLastName());
            ps.setString(INDEX_3, entity.getLogin());
            ps.setString(INDEX_4, entity.getPassword());
        } catch (SQLException e) {
            throw new JdbcDaoException(MessageFormat.format
                    ("Cannot set fields of the entity {0} to prepared statement", entity), e);
        }
    }

    @Override
    protected void setFieldsForUpdateTo(PreparedStatement ps, Student entity) throws JdbcDaoException {
        throw new UnsupportedOperationException("Update query isn't implemented");
    }

    @Override
    protected void setResultSetTo(List<Student> students, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Student student = new Student();
            student.setId(rs.getInt(STUDENT_ID_COLUMN));
            student.setFirstName(rs.getString(FIRST_NAME_COLUMN));
            student.setLastName(rs.getString(LAST_NAME_COLUMN));
            students.add(student);
        }
    }

    @Override
    public String getInsertQueryKey() {
        return INSERT_QUERY_KEY;
    }

    @Override
    protected String getUpdateQueryKey() {
        throw new UnsupportedOperationException("Update query isn't implemented");
    }

    @Override
    protected String getSelectByIdQueryKey() {
        throw new UnsupportedOperationException("Select by id query isn't implemented");
    }

    @Override
    protected String getSelectByNameQueryKey() {
        throw new UnsupportedOperationException("Select by name query isn't implemented");
    }

    @Override
    public int insertReference() throws JdbcDaoException {
        String query = getQuery(INSERT_REF_QUERY_KEY);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new JdbcDaoException("Cannot insert reference id into the STUDENT table", e);
        }
    }

    @Override
    public List<Student> findAllByCourseId(int courseId) throws DaoException {
        String query = getQuery(FIND_STUDENTS_BY_COURSE_QUERY_KEY);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(INDEX_1, courseId);
            ResultSet rs = ps.executeQuery();
            List<Student> students = new ArrayList<>();
            setResultSetTo(students, rs);
            return students;
        } catch (SQLException e) {
            throw new JdbcDaoException("Cannot find student registered for course with courseId=", e);
        }
    }

    @Override
    public Student completeCourse(Student student) throws JdbcDaoException {
        String query = getQuery(UPDATE_STUDENT_COURSE_KEY);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            setFieldsTo(ps, student);
            ps.executeUpdate();

            Map<Course, Completion> courses = student.getCourses();
            Iterator<Completion> iterator = courses.values().iterator();
            if (iterator.hasNext()) {
                Completion completion = iterator.next();
                completion.setComplete(true);
            }

            return student;

        } catch (SQLException e) {
            throw new JdbcDaoException(MessageFormat.format
                    ("Cannot update completion info for student {0} {1}", student, student.getCourses()), e);
        }
    }

    private void setFieldsTo(PreparedStatement ps, Student student) throws SQLException {
        Map<Course, Completion> courses = student.getCourses();
        Iterator<Map.Entry<Course, Completion>> iterator = courses.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<Course, Completion> courseCompletionEntry = iterator.next();
            Course course = courseCompletionEntry.getKey();
            Completion completion = courseCompletionEntry.getValue();
            ps.setByte(INDEX_1, completion.getGrade());
            ps.setString(INDEX_2, completion.getFeedback());
            ps.setInt(INDEX_3, student.getId());
            ps.setInt(INDEX_4, course.getId());
        }
    }

    @Override
    public Student find(Student student) throws JdbcDaoException {
        String query = getQuery(FIND_STUDENT_BY_COURSE_QUERY_KEY);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            Map<Course, Completion> courses = student.getCourses();
            Iterator<Course> iterator = courses.keySet().iterator();
            ps.setInt(INDEX_2, iterator.next().getId());
            ps.setInt(INDEX_1, student.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                student.setFirstName(rs.getString(FIRST_NAME_COLUMN));
                student.setLastName(rs.getString(LAST_NAME_COLUMN));
                Completion completionInfo = new Completion();
                completionInfo.setComplete(rs.getBoolean(IS_COMPLETE_COLUMN));
                completionInfo.setGrade(rs.getByte(GRADE_COLUMN));
                completionInfo.setFeedback(rs.getString(FEEDBACK_COLUMN));
                Course course = new Course(rs.getInt(COURSE_ID_COLUMN));
                Map<Course, Completion> evaluatedCourses = new HashMap<>();
                evaluatedCourses.put(course, completionInfo);
                student.setCourses(evaluatedCourses);
            }

            return student;

        } catch (SQLException e) {
            throw new JdbcDaoException(MessageFormat.format
                    ("Cannot find student {0}", student), e);
        }
    }

    @Override
    public boolean addCourse(int studentId, int courseId) throws JdbcDaoException {
        String query = getQuery(INSERT_COURSE_FOR_STUD_KEY);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(INDEX_1, studentId);
            ps.setInt(INDEX_2, courseId);
            int updateCount = ps.executeUpdate();
            return updateCount != 0;

        } catch (SQLException e) {
            if (e.getErrorCode() == NOT_UNIQUE_ERROR_VENDOR_CODE)
                throw new NotUniqueJdbcDaoException(MessageFormat.format(
                        "Cannot add course for student (studentId={0}) into STUDENT_COURSE table. " +
                                "The course is already exists (courseId={1})", studentId, courseId), e);
            throw new JdbcDaoException(MessageFormat.format(
                    "Cannot add course courseId={0}) into STUDENT_COURSE table", courseId), e);
        }
    }
}
