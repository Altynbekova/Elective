package com.epam.altynbekova.elective.dao.jdbc;

import com.epam.altynbekova.elective.dao.LecturerDao;
import com.epam.altynbekova.elective.entity.Lecturer;
import com.epam.altynbekova.elective.exception.JdbcDaoException;
import com.epam.altynbekova.elective.exception.PropertyManagerException;
import com.epam.altynbekova.elective.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

public class JdbcLecturerDao extends JdbcDao<Lecturer> implements LecturerDao {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcLecturerDao.class);
    private static final String INSERT_QUERY_KEY = "insert.user.lecturer";
    private static final String INSERT_REF_QUERY_KEY = "insert.lecturer";

    public JdbcLecturerDao(Connection connection) {
        super(connection);
    }

    @Override
    protected void setFieldsForInsertTo(PreparedStatement ps, Lecturer entity) throws JdbcDaoException {
        try {
            ps.setString(INDEX_1, entity.getFirstName());
            ps.setString(INDEX_2, entity.getLastName());
            ps.setString(INDEX_3, entity.getLogin());
            ps.setString(INDEX_4, entity.getPassword());
            ps.setString(INDEX_5, entity.getJobTitle());
        } catch (SQLException e) {
            throw new JdbcDaoException(MessageFormat.format
                    ("Cannot set fields of the entity {0} to prepared statement", entity), e);
        }
    }

    @Override
    protected void setFieldsForUpdateTo(PreparedStatement ps, Lecturer entity) throws JdbcDaoException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected Lecturer setResultSetTo(Lecturer entity, ResultSet rs) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected void setResultSetTo(List<Lecturer> entity, ResultSet rs) {

    }

    @Override
    protected String getInsertQueryKey() {
        return INSERT_QUERY_KEY;
    }

    @Override
    protected String getUpdateQueryKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getSelectByIdQueryKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getSelectByNameQueryKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int insert(String jobTitle) throws JdbcDaoException {
        try {
            PropertyManager propertyManager = new PropertyManager(getQueryFileName());
            String query = propertyManager.getProperty(INSERT_REF_QUERY_KEY);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(INDEX_1, jobTitle);
                ps.executeUpdate();
                return -1;
            } catch (SQLException e) {
                throw new JdbcDaoException("Cannot insert fields into the LECTURER table", e);
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(MessageFormat.format
                    ("Cannot get property value with key{0}", INSERT_REF_QUERY_KEY), e);
        }
    }
}
