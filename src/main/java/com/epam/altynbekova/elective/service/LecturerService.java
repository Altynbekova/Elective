package com.epam.altynbekova.elective.service;

import com.epam.altynbekova.elective.dao.DaoFactory;
import com.epam.altynbekova.elective.dao.LecturerDao;
import com.epam.altynbekova.elective.dao.StudentDao;
import com.epam.altynbekova.elective.dao.UserDao;
import com.epam.altynbekova.elective.entity.Lecturer;
import com.epam.altynbekova.elective.entity.Role;
import com.epam.altynbekova.elective.entity.Student;
import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.DaoException;
import com.epam.altynbekova.elective.exception.EntityExistsException;
import com.epam.altynbekova.elective.exception.NotUniqueJdbcDaoException;
import com.epam.altynbekova.elective.exception.ServiceException;
import org.mindrot.jbcrypt.BCrypt;

import java.text.MessageFormat;

public class LecturerService {

    public Lecturer register(Lecturer lecturer) throws ServiceException, EntityExistsException {
        lecturer.setPassword(BCrypt.hashpw(lecturer.getPassword(), BCrypt.gensalt()));

        try (DaoFactory daoFactory = DaoFactory.createJdbcFactory()) {
            UserDao userDao = daoFactory.getUserDao();
            LecturerDao lecturerDao = daoFactory.getLecturerDao();

            daoFactory.beginTransaction();
            userDao.insert(lecturer);
            lecturerDao.insert(lecturer.getJobTitle());
            daoFactory.commitTransaction();
        } catch (NotUniqueJdbcDaoException e) {
            throw new EntityExistsException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return lecturer;
    }
}
