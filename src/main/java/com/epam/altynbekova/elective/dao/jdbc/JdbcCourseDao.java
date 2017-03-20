package com.epam.altynbekova.elective.dao.jdbc;

import com.epam.altynbekova.elective.dao.CourseDao;
import com.epam.altynbekova.elective.entity.*;
import com.epam.altynbekova.elective.exception.JdbcDaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

public class JdbcCourseDao extends JdbcDao<Course> implements CourseDao {
    private static final String FIND_COURSES_QUERY_KEY = "find.all.courses";
    private static final String FIND_AVAILABLE_COURSES_QUERY_KEY = "find.all.courses.available";
    private static final String FIND_COURSE_QUERY_KEY = "find.course.by.name";
    private static final String FIND_COURSES_FOR_STUD = "find.courses.all.by.student.id";
    private static final String FIND_COURSES_FOR_LECTURER = "find.courses.by.lecturer.id";
    private static final String FIND_COMPLETED_COURSES_FOR_STUD = "find.courses.completed.by.student.id";
    private static final String FIND_UNCOMPLETED_COURSES_FOR_STUD = "find.courses.uncompleted.by.student.id";
    private static final String FIND_COURSE_BY_ID_KEY = "find.course.by.id";
    private static final String UPDATE_COURSE_STATUS_QUERY_KEY = "update.course.availability";
    private static final String INVALIDATE_STUDENTS_QUERY_KEY = "update.courses.set.complete.null";
    private static final String INSERT_COURSE_QUERY_KEY = "insert.new.course";

    private static final String LECTURER_ID_COLUMN = "LECTURER.LECTURER_ID";
    private static final String JOB_TITLE_COLUMN = "LECTURER.JOB_TITLE";
    private static final String COURSE_ID_COLUMN = "COURSE_ID";
    private static final String NAME_COLUMN = "NAME";
    private static final String DESCRIPTION_COLUMN = "DESCRIPTION";
    private static final String IS_AVAILABLE_COLUMN = "IS_AVAILABLE";
    private static final String COMPLETION_ID_COLUMN = "STUDENT_COURSE.ID";
    private static final String STUDENT_COURSE_ID_COLUMN = "STUDENT_COURSE.COURSE_ID";
    private static final String IS_COMPLETE_COLUMN = "IS_COMPLETE";
    private static final String GRADE_COLUMN = "GRADE";
    private static final String FEEDBACK_COLUMN = "FEEDBACK";

    JdbcCourseDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Course> findAll() throws JdbcDaoException {
        String query = getQuery(FIND_COURSES_QUERY_KEY);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            List<Course> courses = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            setResultSetTo(courses, rs);
            return courses;
        } catch (SQLException e) {
            throw new JdbcDaoException("Cannot find any courses in database", e);
        }
    }

    @Override
    public List<Course> findAll(boolean isAvailable) throws JdbcDaoException {
        String query = getQuery(FIND_AVAILABLE_COURSES_QUERY_KEY);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            List<Course> courses = new ArrayList<>();
            ps.setBoolean(INDEX_1, isAvailable);
            ResultSet rs = ps.executeQuery();
            setResultSetTo(courses, rs);
            return courses;
        } catch (SQLException e) {
            throw new JdbcDaoException("Cannot find any courses in database", e);
        }
    }

    @Override
    public List<Course> findAllFor(User user) throws JdbcDaoException {
        String query;
        if (user.getRole() == Role.STUDENT)
            query = getQuery(FIND_COURSES_FOR_STUD);
        else
            query = getQuery(FIND_COURSES_FOR_LECTURER);

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            List<Course> courses = new ArrayList<>();
            ps.setInt(INDEX_1, user.getId());
            ResultSet rs = ps.executeQuery();
            setResultSetTo(courses, rs);
            return courses;
        } catch (SQLException e) {
            throw new JdbcDaoException(MessageFormat.format(
                    "Cannot find any courses in database for user {0}", user), e);
        }

    }

    @Override
    public Map<Course, Completion> findStudentCourses(int studentId, boolean isComplete) throws JdbcDaoException {
        String query;
        if (isComplete)
            query = getQuery(FIND_COMPLETED_COURSES_FOR_STUD);
        else
            query = getQuery(FIND_UNCOMPLETED_COURSES_FOR_STUD);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            Map<Course, Completion> courses = new HashMap<>();
            ps.setInt(INDEX_1, studentId);
            ResultSet rs = ps.executeQuery();
            setResultSetTo(courses, rs);
            return courses;
        } catch (SQLException e) {
            throw new JdbcDaoException(MessageFormat.format(
                    "Cannot find any courses in database where studentId={0} and  isComplete={1}",
                    studentId, isComplete), e);
        }
    }

    @Override
    public Course changeStatus(boolean isAvailable, int courseId) throws JdbcDaoException {
        String query = getQuery(UPDATE_COURSE_STATUS_QUERY_KEY);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            Course course = new Course();
            ps.setBoolean(INDEX_1, isAvailable);
            ps.setInt(INDEX_2, courseId);
            ps.executeUpdate();
            course.setId(courseId);
            course.setAvailable(isAvailable);
            return course;
        } catch (SQLException e) {
            throw new JdbcDaoException("Cannot update status of course in database", e);
        }
    }

    @Override
    public void invalidateStudents(int courseId) throws JdbcDaoException {
        String query = getQuery(INVALIDATE_STUDENTS_QUERY_KEY);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(INDEX_1, courseId);
            /*LOG.debug("ps.executeUpdate()...");
            int rowCount =
            LOG.debug("ps.executeUpdate()={}", rowCount);*/
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new JdbcDaoException("Cannot set NULL for students who completed course", e);
        }
    }

    @Override
    protected void setFieldsForInsertTo(PreparedStatement ps, Course entity) throws JdbcDaoException {
        try {
            ps.setString(INDEX_1, entity.getName());
            ps.setString(INDEX_2, entity.getName().toUpperCase());
            ps.setString(INDEX_3, entity.getDescription());
            ps.setInt(INDEX_4, entity.getLecturer().getId());
        } catch (SQLException e) {
            throw new JdbcDaoException(MessageFormat.format("Cannot insert new course {0} into database", entity), e);
        }
    }

    @Override
    protected void setFieldsForUpdateTo(PreparedStatement ps, Course entity) throws JdbcDaoException {
        throw new UnsupportedOperationException("Update query isn't implemented");
    }

    @Override
    protected void setResultSetTo(List<Course> courses, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Course course = new Course();
            Lecturer lecturer = new Lecturer();
            course.setId(rs.getInt(COURSE_ID_COLUMN));
            course.setDescription(rs.getString(DESCRIPTION_COLUMN));
            course.setName(rs.getString(NAME_COLUMN));
            course.setAvailable(rs.getBoolean(IS_AVAILABLE_COLUMN));
            lecturer.setId(rs.getInt(LECTURER_ID_COLUMN));
            lecturer.setFirstName(rs.getString(FIRST_NAME_COLUMN));
            lecturer.setLastName(rs.getString(LAST_NAME_COLUMN));
            lecturer.setJobTitle(rs.getString(JOB_TITLE_COLUMN));
            course.setLecturer(lecturer);
            courses.add(course);
        }
    }

    @Override
    protected String getInsertQueryKey() {
        return INSERT_COURSE_QUERY_KEY;
    }

    @Override
    protected String getUpdateQueryKey() {
        throw new UnsupportedOperationException("Update query is not implemented");
    }

    @Override
    protected String getSelectByIdQueryKey() {
        return FIND_COURSE_BY_ID_KEY;
    }

    @Override
    protected String getSelectByNameQueryKey() {
        return FIND_COURSE_QUERY_KEY;
    }

    private void setResultSetTo(Map<Course, Completion> courses, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Completion completionInfo = new Completion();
            Course course = new Course();

            course.setId(rs.getInt(STUDENT_COURSE_ID_COLUMN));
            course.setDescription(rs.getString(DESCRIPTION_COLUMN));
            course.setName(rs.getString(NAME_COLUMN));
            course.setAvailable(rs.getBoolean(IS_COMPLETE_COLUMN));
            completionInfo.setId(rs.getInt(COMPLETION_ID_COLUMN));
            completionInfo.setComplete(rs.getBoolean(IS_COMPLETE_COLUMN));
            completionInfo.setFeedback(rs.getString(FEEDBACK_COLUMN));
            completionInfo.setGrade(rs.getByte(GRADE_COLUMN));

            courses.put(course, completionInfo);
        }
    }
}
