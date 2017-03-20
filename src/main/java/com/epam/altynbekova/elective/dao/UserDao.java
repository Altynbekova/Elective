package com.epam.altynbekova.elective.dao;

import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.DaoException;

public interface UserDao extends Dao<User> {
    User insert(User user) throws DaoException;
}
