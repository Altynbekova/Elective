package com.epam.altynbekova.elective.dao;

import com.epam.altynbekova.elective.entity.Lecturer;
import com.epam.altynbekova.elective.exception.DaoException;
import com.epam.altynbekova.elective.exception.JdbcDaoException;

public interface LecturerDao extends Dao<Lecturer> {
    int insert(String jobTitle) throws DaoException;
}
