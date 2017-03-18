package com.epam.altynbekova.elective.dao.jdbc;

import com.epam.altynbekova.elective.dao.UserDao;
import com.epam.altynbekova.elective.entity.Role;
import com.epam.altynbekova.elective.entity.User;
import com.epam.altynbekova.elective.exception.JdbcDaoException;
import com.epam.altynbekova.elective.exception.NotUniqueJdbcDaoException;
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

public class JdbcUserDao extends JdbcDao<User> implements UserDao {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcUserDao.class);
    private static final String INSERT_USER_QUERY_KEY = "insert.user";
    private final String SELECT_BY_LOGIN_QUERY_KEY = "find.user.by.login";
    private static final String SELECT_BY_ID_QUERY_KEY = "find.user.by.id";

    JdbcUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public User insert(User user) throws JdbcDaoException, NotUniqueJdbcDaoException {
        try {
            PropertyManager propertyManager = new PropertyManager(getQueryFileName());
            String query = propertyManager.getProperty(INSERT_USER_QUERY_KEY);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                setFieldsForInsertTo(ps, user);
                ps.executeUpdate();
            } catch (SQLException e) {
                if (e.getErrorCode() == NOT_UNIQUE_ERROR_VENDOR_CODE)
                    throw new NotUniqueJdbcDaoException("Cannot save entity to database. Check unique fields", e);
                throw new JdbcDaoException(MessageFormat.format("Cannot insert user {0} into database", user), e);
            }
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(MessageFormat.format("Cannot get property {0} from file", INSERT_USER_QUERY_KEY), e);
        }
        return user;
    }

    @Override
    protected void setFieldsForInsertTo(PreparedStatement ps, User entity) throws JdbcDaoException {
        try {
            ps.setString(INDEX_1, entity.getFirstName());
            ps.setString(INDEX_2, entity.getLastName());
            ps.setString(INDEX_3, entity.getLogin());
            ps.setString(INDEX_4, entity.getPassword());
            ps.setString(INDEX_5, entity.getRole().getValue().toUpperCase());
        } catch (SQLException e) {
            throw new JdbcDaoException(MessageFormat.format
                    ("Cannot set fields of the entity {0} to prepared statement", entity), e);
        }
    }

    @Override
    protected void setFieldsForUpdateTo(PreparedStatement ps, User entity) throws JdbcDaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected User setResultSetTo(User entity, ResultSet rs) {
        entity = new User();
        try {
            entity.setId(rs.getInt(USER_ID_COLUMN));
            entity.setLogin(rs.getString(LOGIN_COLUMN));
            entity.setPassword(rs.getString(PASSWORD_COLUMN));
            entity.setFirstName(rs.getString(FIRST_NAME_COLUMN));
            entity.setLastName(rs.getString(LAST_NAME_COLUMN));
            entity.setRole(Role.getByName(rs.getString(ROLE_NAME_COLUMN)));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOG.debug("Mapped resultSet to user entity: {}", entity);
        return entity;
    }

    @Override
    protected void setResultSetTo(List<User> users, ResultSet rs) throws SQLException {
        while (rs.next()) {
            users.add(setCourseFieldsFrom(rs));
        }
    }

    private User setCourseFieldsFrom(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(USER_ID_COLUMN));
        user.setLogin(rs.getString(LOGIN_COLUMN));
        user.setPassword(rs.getString(PASSWORD_COLUMN));
        user.setFirstName(rs.getString(FIRST_NAME_COLUMN));
        user.setLastName(rs.getString(LAST_NAME_COLUMN));
        user.setRole(Role.getByName(rs.getString(ROLE_NAME_COLUMN)));
        return user;
    }

    @Override
    protected String getInsertQueryKey() {

        return INSERT_USER_QUERY_KEY;
    }

    @Override
    protected String getUpdateQueryKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getSelectByIdQueryKey() {
        return SELECT_BY_ID_QUERY_KEY;
    }

    @Override
    protected String getSelectByNameQueryKey() {
        return SELECT_BY_LOGIN_QUERY_KEY;
    }
}
