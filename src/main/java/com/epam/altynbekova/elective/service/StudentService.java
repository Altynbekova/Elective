package com.epam.altynbekova.elective.service;

import com.epam.altynbekova.elective.dao.DaoFactory;
import com.epam.altynbekova.elective.dao.StudentDao;
import com.epam.altynbekova.elective.dao.UserDao;
import com.epam.altynbekova.elective.entity.Student;
import com.epam.altynbekova.elective.exception.DaoException;
import com.epam.altynbekova.elective.exception.EntityExistsException;
import com.epam.altynbekova.elective.exception.NotUniqueJdbcDaoException;
import com.epam.altynbekova.elective.exception.ServiceException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class StudentService {
    public Student register(Student student) throws ServiceException {
        student.setPassword(BCrypt.hashpw(student.getPassword(), BCrypt.gensalt()));

        try (DaoFactory daoFactory = DaoFactory.createJdbcFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            StudentDao studentDao = daoFactory.getStudentDao();

            daoFactory.beginTransaction();
            userDao.insert(student);
            studentDao.insertReference();
            daoFactory.commitTransaction();
        } catch (NotUniqueJdbcDaoException e) {
            throw new EntityExistsException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return student;
    }

    public List<Student> findStudents(int courseId) throws ServiceException {
        try (DaoFactory daoFactory = DaoFactory.createJdbcFactory()) {
            StudentDao studentDao = daoFactory.getStudentDao();
            return studentDao.findAllByCourseId(courseId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Student evaluateStudent(Student student) throws ServiceException {
        try (DaoFactory daoFactory = DaoFactory.createJdbcFactory()) {
            StudentDao studentDao = daoFactory.getStudentDao();
            return studentDao.completeCourse(student);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Student findStudent(Student student) throws ServiceException {
        try (DaoFactory daoFactory = DaoFactory.createJdbcFactory()) {
            StudentDao studentDao = daoFactory.getStudentDao();
            return studentDao.find(student);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public boolean registerForCourse(int studentId, int courseId) throws ServiceException {
        try (DaoFactory daoFactory = DaoFactory.createJdbcFactory()) {
            StudentDao studentDao = daoFactory.getStudentDao();
            return studentDao.addCourse(studentId, courseId);
        } catch (NotUniqueJdbcDaoException e) {
            throw new EntityExistsException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
