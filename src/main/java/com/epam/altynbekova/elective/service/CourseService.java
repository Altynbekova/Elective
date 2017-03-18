package com.epam.altynbekova.elective.service;

import com.epam.altynbekova.elective.dao.CourseDao;
import com.epam.altynbekova.elective.dao.DaoFactory;
import com.epam.altynbekova.elective.entity.Completion;
import com.epam.altynbekova.elective.entity.Course;
import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.DaoException;
import com.epam.altynbekova.elective.exception.EntityExistsException;
import com.epam.altynbekova.elective.exception.NotUniqueJdbcDaoException;
import com.epam.altynbekova.elective.exception.ServiceException;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class CourseService {
    private static final String PERCENT = "%";

    public List<Course> getAllCourses() throws ServiceException {
        try (DaoFactory factory = DaoFactory.createJdbcFactory()) {
            CourseDao courseDao = factory.getCourseDao();
            return courseDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Course> getAllCourses(boolean isAvailable) throws ServiceException {
        try (DaoFactory factory = DaoFactory.createJdbcFactory()) {
            CourseDao courseDao = factory.getCourseDao();
            return courseDao.findAll(isAvailable);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Course> getCoursesByName(String courseName) throws ServiceException {
        try (DaoFactory factory = DaoFactory.createJdbcFactory()) {
            CourseDao courseDao = factory.getCourseDao();
            return courseDao.findAllByName(courseName.toUpperCase() + PERCENT);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Course> getCoursesFor(User user) throws ServiceException {
        try (DaoFactory factory = DaoFactory.createJdbcFactory()) {
            CourseDao courseDao = factory.getCourseDao();
            return courseDao.findAllFor(user);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Course addNewCourse(Course course) throws ServiceException, EntityExistsException {
        try (DaoFactory factory = DaoFactory.createJdbcFactory()) {
            CourseDao courseDao = factory.getCourseDao();
            return courseDao.save(course);
        } catch (NotUniqueJdbcDaoException e) {
            throw new EntityExistsException(MessageFormat.format
                    ("Cannot add new course {0}, it already exists", course), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Course getCourseById(int courseId) throws ServiceException {
        try (DaoFactory factory = DaoFactory.createJdbcFactory()) {
            CourseDao courseDao = factory.getCourseDao();
            return courseDao.findById(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Map<Course, Completion> getStudentCourses(int studentId, boolean isComplete) throws ServiceException {
        try (DaoFactory factory = DaoFactory.createJdbcFactory()) {
            CourseDao courseDao = factory.getCourseDao();
            return courseDao.findStudentCourses(studentId, isComplete);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Course changeStatus(boolean isAvailable, int courseId) throws ServiceException {
        try (DaoFactory factory = DaoFactory.createJdbcFactory()) {
            CourseDao courseDao = factory.getCourseDao();
            if (!isAvailable)
                courseDao.changeStatus(isAvailable, courseId);
            else {
                factory.beginTransaction();
                courseDao.changeStatus(isAvailable, courseId);
                courseDao.invalidateStudents(courseId);
                factory.commitTransaction();
            }
            Course course = new Course();
            course.setId(courseId);
            course.setAvailable(isAvailable);
            return course;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
