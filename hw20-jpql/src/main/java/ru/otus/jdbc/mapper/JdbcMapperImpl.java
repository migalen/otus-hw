package ru.otus.jdbc.mapper;

import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public final class JdbcMapperImpl<T> implements JdbcMapper<T> {

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T> dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    private static final Logger log = LoggerFactory.getLogger(JdbcMapperImpl.class);

    public JdbcMapperImpl(SessionManagerJdbc sessionManager,
                          DbExecutor<T> dbExecutor,
                          EntitySQLMetaData entitySQLMetaData,
                          EntityClassMetaData<T> entityClassMetaData) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public void insert(T objectData) {
        log.debug("insert() - start: objectData = {}", objectData);
        try {
            Object id = getFieldValue(entityClassMetaData.getIdField(), objectData);
            if (id instanceof Long && (long) id == 0L) {
                insertAutoincrement(objectData);
                return;
            }

            String query = entitySQLMetaData.getInsertSql();
            log.trace("insert() - trace: query = {}", query);

            List<Object> params = entityClassMetaData.getAllFields().stream()
                                          .map(field -> getFieldValue(field, objectData))
                                          .collect(Collectors.toList());
            log.trace("insert() - trace: params = {}", params);

            dbExecutor.executeInsert(getConnection(), query, params);
        } catch (Exception e) {
            log.debug("insert() - verdict: cannot be performed");
            throw new JdbcMapperException(e);
        }
        log.debug("insert() - end");
    }

    private void insertAutoincrement(T objectData) throws Exception {
        String query = entitySQLMetaData.getInsertAutoincrementSql();
        log.trace("insertAutoincrement() - trace: query = {}", query);

        List<Object> params = entityClassMetaData.getFieldsWithoutId().stream()
                                      .map(field -> getFieldValue(field, objectData))
                                      .collect(Collectors.toList());
        log.trace("insertAutoincrement() - trace: params = {}", params);
        String insertedId = dbExecutor.executeInsert(getConnection(), query, params);

        Field idField = entityClassMetaData.getIdField();
        idField.setAccessible(true);
        idField.set(objectData, Long.valueOf(insertedId));
    }

    @Override
    public void update(T objectData) {
        log.debug("update() - start: objectData = {}", objectData);
        try {
            String query = entitySQLMetaData.getUpdateSql();
            log.trace("update() - trace: query = {}", query);

            List<Object> params = entityClassMetaData.getFieldsWithoutId().stream()
                                          .map(field -> getFieldValue(field, objectData))
                                          .collect(Collectors.toList());
            params.add(getFieldValue(entityClassMetaData.getIdField(), objectData));
            log.trace("update() - trace: params = {}", params);

            dbExecutor.executeInsert(getConnection(), query, params);
        } catch (Exception e) {
            log.debug("update() - verdict: cannot be performed");
            throw new JdbcMapperException(e);
        }
        log.debug("update() - end");
    }

    @Override
    public void insertOrUpdate(T objectData) {
        log.debug("insertOrUpdate() - start: objectData = {}", objectData);
        try {
            Field idField = entityClassMetaData.getIdField();
            Object id = getFieldValue(idField, objectData);
            if (id != null && existsById(id)) {
                update(objectData);
            } else {
                insert(objectData);
            }
        } catch (Exception e) {
            log.debug("insertOrUpdate() - verdict: cannot be performed");
            throw new JdbcMapperException(e);
        }
        log.debug("insertOrUpdate() - end");
    }

    @SuppressWarnings("unchecked")
    private boolean existsById(Object id) throws SQLException {
        return dbExecutor.executeSelect(getConnection(), entitySQLMetaData.getSelectByIdSql(), id,
                rs -> {
                    try {
                        return rs.next() ? (T) new Object() : null;
                    } catch (SQLException e) {
                        throw new JdbcMapperException(e);
                    }
                }).isPresent();
    }

    @Override
    public T findById(Object id, Class<T> clazz) {
        log.debug("findById() - start: id = {}, clazz = {}", id, clazz);
        try {
            T entity = dbExecutor.executeSelect(getConnection(), entitySQLMetaData.getSelectByIdSql(), id,
                    rs -> {
                        try {
                            if (rs.next()) {
                                Object[] initArgs = entityClassMetaData.getAllFields().stream()
                                                            .map(field -> getQueryResultValue(field, rs))
                                                            .toArray();
                                return entityClassMetaData.getConstructor().newInstance(initArgs);
                            }
                        } catch (SQLException | ReflectiveOperationException e) {
                            throw new JdbcMapperException(e);
                        }
                        return null;
                    }).orElse(null);

            log.debug("findById() - end: entity = {}", entity);
            return entity;
        } catch (Exception e) {
            log.debug("findById() - verdict: cannot be performed");
            throw new JdbcMapperException(e);
        }
    }

    private Object getQueryResultValue(Field field, ResultSet rs) {
        try {
            Class<?> type = field.getType();
            String name = field.getName();
            return switch (type.getSimpleName()) {
                case "long" -> rs.getLong(name);
                case "int" -> rs.getInt(name);
                case "float" -> rs.getFloat(name);
                case "double" -> rs.getDouble(name);
                case "String" -> rs.getString(name);
                default -> throw new JdbcMapperException("Unsupported type of column");
            };
        } catch (SQLException e) {
            throw new JdbcMapperException(e);
        }
    }

    private Object getFieldValue(Field field, T objectData) {
        try {
            field.setAccessible(true);
            return field.get(objectData);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
