package com.epam.altynbekova.elective.dao;

import com.epam.altynbekova.elective.entity.Student;
import com.epam.altynbekova.elective.exception.DaoException;
import com.epam.altynbekova.elective.exception.JdbcDaoException;

import java.util.List;

public interface StudentDao  extends Dao<Student>{
    int insertReference() throws DaoException;

    List<Student> findAllByCourseId(int courseId) throws DaoException;

    Student completeCourse(Student student) throws DaoException;

    Student find(Student student) throws DaoException;

    boolean addCourse(int studentId, int courseId) throws DaoException;
}
