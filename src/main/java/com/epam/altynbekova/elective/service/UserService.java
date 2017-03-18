package com.epam.altynbekova.elective.service;

import com.epam.altynbekova.elective.dao.DaoFactory;
import com.epam.altynbekova.elective.dao.UserDao;
import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.DaoException;
import com.epam.altynbekova.elective.exception.ServiceException;

public class UserService {
    public User getUserByLogin(String login) throws ServiceException {
        try (DaoFactory factory = DaoFactory.createJdbcFactory()) {
            UserDao userDao = factory.getUserDao();
            return userDao.findByName(login);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
