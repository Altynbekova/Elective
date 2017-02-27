package com.epam.altynbekova.elective.dao;

import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.DaoException;
import com.epam.altynbekova.elective.exception.NotUniqueJdbcDaoException;

public interface UserDao extends Dao<User> {
    User insert(User user) throws DaoException, NotUniqueJdbcDaoException;
}
