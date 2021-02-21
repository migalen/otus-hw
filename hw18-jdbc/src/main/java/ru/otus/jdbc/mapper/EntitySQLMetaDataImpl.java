package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    private static final Logger log = LoggerFactory.getLogger(EntitySQLMetaDataImpl.class);

    public EntitySQLMetaDataImpl(Class<?> clazz) {this.entityClassMetaData = new EntityClassMetaDataImpl<>(clazz);}

    @Override
    public String getSelectAllSql() {
        String query = String.format("select * from %s", entityClassMetaData.getName());
        log.trace("getSelectAllSql() - trace: query = {}", query);
        return query;
    }

    @Override
    public String getSelectByIdSql() {
        String query = String.format("select * from %s where %s  = ?",
                entityClassMetaData.getName(), entityClassMetaData.getIdField().getName().toLowerCase());
        log.trace("getSelectByIdSql() - trace: query = {}", query);
        return query;
    }

    @Override
    public String getInsertSql() {
        return getInsertQuery(entityClassMetaData.getAllFields());
    }

    @Override
    public String getInsertAutoincrementSql() {
        return getInsertQuery(entityClassMetaData.getFieldsWithoutId());
    }

    private String getInsertQuery(List<Field> fields) {
        String queryFields = fields.stream().map(Field::getName).map(String::toLowerCase).collect(Collectors.joining(", "));
        String queryValues = Stream.generate(() -> "?").limit(fields.size()).collect(Collectors.joining(", "));
        String query = String.format("insert into %s(%s) values (%s)",
                entityClassMetaData.getName(), queryFields, queryValues);
        log.trace("getInsertQuery() - trace: query = {}", query);
        return query;
    }

    @Override
    public String getUpdateSql() {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        String idStatement = entityClassMetaData.getIdField().getName().toLowerCase() + " = ?";
        String fieldsStatement = fieldsWithoutId.stream()
                                         .map(Field::getName)
                                         .map(String::toLowerCase)
                                         .map(name -> name + "= ?")
                                         .collect(Collectors.joining(", "));
        String query = String.format("update %s SET %s WHERE %s",
                entityClassMetaData.getName(), fieldsStatement, idStatement);
        log.trace("getUpdateSql() - trace: query = {}", query);
        return query;
    }
}
