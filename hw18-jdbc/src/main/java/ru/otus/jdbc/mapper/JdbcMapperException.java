package ru.otus.jdbc.mapper;

public class JdbcMapperException extends RuntimeException {

    public JdbcMapperException(Exception ex) {
        super(ex);
    }

    public JdbcMapperException(String msg) {
        super(msg);
    }
}
