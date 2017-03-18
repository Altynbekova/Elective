package com.epam.altynbekova.elective.dao.jdbc;

import com.epam.altynbekova.elective.dao.StudentDao;
import com.epam.altynbekova.elective.entity.Completion;
import com.epam.altynbekova.elective.entity.Course;
import com.epam.altynbekova.elective.entity.Student;
import com.epam.altynbekova.elective.exception.DaoException;
import com.epam.altynbekova.elective.exception.JdbcDaoException;
import com.epam.altynbekova.elective.exception.NotUniqueJdbcDaoException;
import com.epam.altynbekova.elective.exception.PropertyManagerException;
import com.epam.altynbekova.elective.util.PropertyManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class JdbcStudentDao extends JdbcDao<Student> implements StudentDao {
    private static final String INSERT_QUERY_KEY = "insert.user.student";
    private static final String INSERT_REF_QUERY_KEY = "insert.student.reference";
    private static final String STUDENT_ID_COLUMN = "STUDENT_ID";
    private static final String FIND_STUDENTS_BY_COURSE_QUERY_KEY = "find.current.students.by.course.id";
    private static final String UPDATE_STUDENT_COURSE_KEY = "update.student.course";
    private static final String FIND_STUDENT_BY_COURSE_QUERY_KEY = "find.student.by.course.id";
    private static final String INSERT_COURSE_FOR_STUD_KEY = "insert.course.for.student";
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
    protected void setResultSetTo(List<Student> students, ResultSet rs) {
        try {
            while (rs.next()) {
                Map<Course, Completion> courses = new HashMap<>();
                Student student = new Student();
                Course course = new Course();
                Completion completionInfo = new Completion();

                student.setId(rs.getInt(STUDENT_ID_COLUMN));
                student.setFirstName(rs.getString(FIRST_NAME_COLUMN));
                student.setLastName(rs.getString(LAST_NAME_COLUMN));
                courses.put(course, completionInfo);
                student.setCourses(courses);

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        try {
            PropertyManager propertyManager = new PropertyManager(getQueryFileName());
            String query = propertyManager.getProperty(INSERT_REF_QUERY_KEY);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.executeUpdate();
                return -1;
            } catch (SQLException e) {
                throw new JdbcDaoException("Cannot insert reference id into the STUDENT table", e);
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(MessageFormat.format
                    ("Cannot get property value with key{0}", INSERT_REF_QUERY_KEY), e);
        }
    }

    @Override
    public List<Student> findAllByCourseId(int courseId) throws DaoException {
        try {
            PropertyManager propertyManager = new PropertyManager(getQueryFileName());
            String query = propertyManager.getProperty(FIND_STUDENTS_BY_COURSE_QUERY_KEY);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(INDEX_1, courseId);
                ResultSet rs = ps.executeQuery();
                List<Student> students = new ArrayList<>();
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt(USER_ID_COLUMN));
                    student.setFirstName(rs.getString(FIRST_NAME_COLUMN));
                    student.setLastName(rs.getString(LAST_NAME_COLUMN));
                    students.add(student);
                }
                return students;
            } catch (SQLException e) {
                throw new JdbcDaoException("Cannot find student registered for course with courseId=", e);
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(MessageFormat.format
                    ("Cannot get property value with key{0}", FIND_STUDENTS_BY_COURSE_QUERY_KEY), e);
        }
    }

    @Override
    public Student completeCourse(Student student) throws JdbcDaoException {
        try {
            PropertyManager propertyManager = new PropertyManager(getQueryFileName());
            String query = propertyManager.getProperty(UPDATE_STUDENT_COURSE_KEY);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                Map<Course, Completion> courses = student.getCourses();
                if (!courses.isEmpty()) courses.forEach((k, v) -> {
                    try {
                        ps.setByte(INDEX_1, v.getGrade());
                        ps.setString(INDEX_2, v.getFeedback());
                        ps.setInt(INDEX_3, student.getId());
                        ps.setInt(INDEX_4, k.getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                ps.executeUpdate();
                for (Completion completion : courses.values()) {
                    completion.setComplete(true);
                    break;
                }

                return student;

            } catch (SQLException e) {
                throw new JdbcDaoException(MessageFormat.format
                        ("Cannot update completion info for student {0} {1}", student, student.getCourses()), e);
            }

        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(MessageFormat.format
                    ("Cannot get query with property key={0}", UPDATE_STUDENT_COURSE_KEY), e);
        }
    }

    @Override
    public Student find(Student student) throws JdbcDaoException {
        try {
            PropertyManager propertyManager = new PropertyManager(getQueryFileName());
            String query = propertyManager.getProperty(FIND_STUDENT_BY_COURSE_QUERY_KEY);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                Map<Course, Completion> courses = student.getCourses();
                if (!courses.isEmpty()) {
                    for (Course course : courses.keySet()) {
                        ps.setInt(INDEX_2, course.getId());
                        break;
                    }
                }
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
                    HashMap<Course, Completion> evaluatedCourses = new HashMap();
                    evaluatedCourses.put(course, completionInfo);
                    student.setCourses(evaluatedCourses);
                }

                return student;

            } catch (SQLException e) {
                throw new JdbcDaoException(MessageFormat.format
                        ("Cannot find student {0}", student), e);
            }

        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(MessageFormat.format
                    ("Cannot get query with property key={0}", FIND_STUDENT_BY_COURSE_QUERY_KEY), e);
        }
    }

    @Override
    public boolean addCourse(int studentId, int courseId) throws JdbcDaoException {
        try {
            PropertyManager propertyManager = new PropertyManager(getQueryFileName());
            String query = propertyManager.getProperty(INSERT_COURSE_FOR_STUD_KEY);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(INDEX_1, studentId);
                ps.setInt(INDEX_2, courseId);
                int updateCount = ps.executeUpdate();
                return updateCount == 0 ? false : true;

            } catch (SQLException e) {
                if (e.getErrorCode() == NOT_UNIQUE_ERROR_VENDOR_CODE)
                    throw new NotUniqueJdbcDaoException(MessageFormat.format(
                            "Cannot add course for student (studentId={0}) into STUDENT_COURSE table. " +
                                    "The course is already exists (courseId={1})", studentId, courseId), e);
                throw new JdbcDaoException(MessageFormat.format(
                        "Cannot add course courseId={0}) into STUDENT_COURSE table", courseId), e);
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(MessageFormat.format
                    ("Cannot get property value with key={0}", INSERT_COURSE_FOR_STUD_KEY), e);
        }
    }
}
