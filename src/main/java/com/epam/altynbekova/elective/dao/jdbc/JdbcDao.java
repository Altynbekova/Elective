package com.epam.altynbekova.elective.dao.jdbc;

import com.epam.altynbekova.elective.dao.Dao;
import com.epam.altynbekova.elective.entity.BaseEntity;
import com.epam.altynbekova.elective.exception.DaoException;
import com.epam.altynbekova.elective.exception.JdbcDaoException;
import com.epam.altynbekova.elective.exception.NotUniqueJdbcDaoException;
import com.epam.altynbekova.elective.exception.PropertyManagerException;
import com.epam.altynbekova.elective.util.PropertyManager;
import org.h2.jdbc.JdbcSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {
    protected static final String USER_ID_COLUMN = "USER_ID";
    protected static final String LOGIN_COLUMN = "LOGIN";
    protected static final String PASSWORD_COLUMN = "PASSWORD";
    protected static final String FIRST_NAME_COLUMN = "USER.FIRST_NAME";
    protected static final String LAST_NAME_COLUMN = "USER.LAST_NAME";
    protected static final String ROLE_NAME_COLUMN = "ROLE_NAME";
    protected static final int NOT_UNIQUE_ERROR_VENDOR_CODE = 23505;
    protected static final int FIRST_LIST_ELEMENT_INDEX = 0;
    protected static final int INDEX_1 = 1;
    protected static final int INDEX_2 = 2;
    protected static final int INDEX_3 = 3;
    protected static final int INDEX_4 = 4;
    protected static final int INDEX_5 = 5;
    private static final Logger LOG = LoggerFactory.getLogger(JdbcDao.class);
    private static final String QUERY_FILE_NAME = "query.properties";
    protected Connection connection;

    public JdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T save(T entity) throws JdbcDaoException, NotUniqueJdbcDaoException {
        String query;
        if (entity.getId() != null) {
            query = getQuery(getUpdateQueryKey());
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                setFieldsForUpdateTo(ps, entity);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new JdbcDaoException(MessageFormat.format("Cannot update entity {} in database", entity), e);
            }
        } else {
            query = getQuery(getInsertQueryKey());
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                setFieldsForInsertTo(ps, entity);
                ps.executeUpdate();
                setId(ps, entity);
            } catch (SQLException e) {
                if (e.getErrorCode() == NOT_UNIQUE_ERROR_VENDOR_CODE)
                    throw new NotUniqueJdbcDaoException("Cannot save entity in database. Check unique fields", e);
                throw new JdbcDaoException(MessageFormat.format("Cannot insert entity {} into database", entity), e);
            }
        }

        return entity;
    }

    @Override
    public T findById(int id) throws JdbcDaoException {
        List<T> entities = findAllById(id);
        if (entities.size() != 0)
            return entities.get(FIRST_LIST_ELEMENT_INDEX);
        else {
            throw new JdbcDaoException(MessageFormat.format("Entity with id={0} doesn't exist", id));
        }

    }

    @Override
    public List<T> findAllById(int id) throws JdbcDaoException {
        List<T> entities = new ArrayList<>();
        String query = getQuery(getSelectByIdQueryKey());
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(INDEX_1, id);
            ResultSet rs = ps.executeQuery();
            setResultSetTo(entities, rs);
            return entities;
        } catch (SQLException e) {
            throw new JdbcDaoException(MessageFormat.format("Cannot find entities with id={} in database", id), e);
        }
    }

    @Override
    public T findByName(String entityName) throws JdbcDaoException {
        List<T> entities = findAllByName(entityName);
        if (entities.size() != 0)
            return entities.get(FIRST_LIST_ELEMENT_INDEX);
        else {
            LOG.error("Entity with name {} doesn't exist", entityName);
            throw new JdbcDaoException(MessageFormat.format("Entity with name {0} doesn't exist", entityName));
        }
    }

    @Override
    public List<T> findAllByName(String entityName) throws JdbcDaoException {
        String query = getQuery(getSelectByNameQueryKey());
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            List<T> entities = new ArrayList<T>();
            ps.setString(INDEX_1, entityName);
            ResultSet rs = ps.executeQuery();
            setResultSetTo(entities, rs);
            return entities;
        } catch (SQLException e) {
            throw new JdbcDaoException(MessageFormat.format(
                    "Cannot find entity by name in database where entityName={0}", entityName), e);
        }
    }

    private String getQuery(String key) throws JdbcDaoException {
        String query;
        try {
            PropertyManager propertyManager = new PropertyManager(QUERY_FILE_NAME);
            query = propertyManager.getProperty(key);
        } catch (PropertyManagerException e) {
            throw new JdbcDaoException(MessageFormat.format("Cannot get property with key={0} from file {1}",
                    key, QUERY_FILE_NAME), e);
        }
        return query;
    }

    private void setId(PreparedStatement ps, T entity) throws JdbcDaoException {
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            entity.setId(id);
        } catch (SQLException e) {
            throw new JdbcDaoException("Cannot set generated id for entity", e);
        }

    }

    protected String getQueryFileName() {
        return QUERY_FILE_NAME;
    }

    protected abstract void setFieldsForInsertTo(PreparedStatement ps, T entity) throws JdbcDaoException;

    protected abstract void setFieldsForUpdateTo(PreparedStatement ps, T entity) throws JdbcDaoException;

    protected T setResultSetTo(T entity, ResultSet rs) throws JdbcDaoException {
        List<T> entities = new ArrayList<>();
        try {
            setResultSetTo(entities, rs);
            return entities.get(FIRST_LIST_ELEMENT_INDEX);
        } catch (SQLException e) {
            throw new JdbcDaoException("Cannot set values for fields of entity", e);
        }
    }

    protected abstract void setResultSetTo(List<T> entity, ResultSet rs) throws SQLException;

    protected abstract String getInsertQueryKey();

    protected abstract String getUpdateQueryKey();

    protected abstract String getSelectByIdQueryKey();

    protected abstract String getSelectByNameQueryKey();

}
