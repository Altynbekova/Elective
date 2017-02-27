package com.epam.altynbekova.elective.dao;

import com.epam.altynbekova.elective.entity.Completion;
import com.epam.altynbekova.elective.entity.Course;
import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.DaoException;
import com.epam.altynbekova.elective.exception.JdbcDaoException;

import java.util.List;
import java.util.Map;

public interface CourseDao extends Dao<Course> {

    List<Course> findAll() throws DaoException;

    List<Course> findAll(boolean isAvailable) throws DaoException;

    List<Course> findAllFor(User user) throws DaoException;

    Map<Course, Completion> findStudentCourses(int studentId, boolean isComplete) throws DaoException;

    Course changeStatus(boolean isAvailable, int courseId) throws DaoException;

    void invalidateStudents(int courseId) throws DaoException;
}
