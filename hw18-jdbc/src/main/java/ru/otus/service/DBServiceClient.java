package ru.otus.service;

import java.util.Optional;

/**
 * Сервис работы с БД.
 *
 * @param <T> entity
 * @param <K> primary key type
 */
public interface DBServiceClient<T, K> {

    K saveEntity(T entity);

    Optional<T> getEntityById(K id);
}
