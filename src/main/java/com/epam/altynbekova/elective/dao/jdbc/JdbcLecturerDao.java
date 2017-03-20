package com.epam.altynbekova.elective.dao.jdbc;

import com.epam.altynbekova.elective.dao.LecturerDao;
import com.epam.altynbekova.elective.entity.Lecturer;
import com.epam.altynbekova.elective.exception.JdbcDaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

public class JdbcLecturerDao extends JdbcDao<Lecturer> implements LecturerDao {
    private static final String INSERT_QUERY_KEY = "insert.user.lecturer";
    private static final String INSERT_REF_QUERY_KEY = "insert.lecturer";

    JdbcLecturerDao(Connection connection) {
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
        throw new UnsupportedOperationException("setFieldsForUpdateTo() in JdbcLecturerDao isn't implemented");
    }

    @Override
    protected void setResultSetTo(List<Lecturer> entity, ResultSet rs) {
        throw new UnsupportedOperationException("setResultSetTo() in JdbcLecturerDao isn't implemented");
    }

    @Override
    protected String getInsertQueryKey() {
        return INSERT_QUERY_KEY;
    }

    @Override
    protected String getUpdateQueryKey() {
        throw new UnsupportedOperationException("Update query isn't implemented");
    }

    @Override
    protected String getSelectByIdQueryKey() {
        throw new UnsupportedOperationException("Select by id query isn't implemented");
    }

    @Override
    protected String getSelectByNameQueryKey() {
        throw new UnsupportedOperationException("Select by name query isn't implemented");
    }

    @Override
    public int insert(String jobTitle) throws JdbcDaoException {
        String query = getQuery(INSERT_REF_QUERY_KEY);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(INDEX_1, jobTitle);
                return ps.executeUpdate();
            } catch (SQLException e) {
                throw new JdbcDaoException("Cannot insert fields into the LECTURER table", e);
            }
    }
}
