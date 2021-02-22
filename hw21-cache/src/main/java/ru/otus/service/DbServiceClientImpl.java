package ru.otus.service;

import ru.otus.dao.Dao;
import ru.otus.model.Client;
import ru.otus.model.PhoneDataSet;
import ru.otus.sessionmanager.SessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DbServiceClientImpl implements DBService<Client, Long> {

    private final Dao<Client, Long> dao;

    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    public DbServiceClientImpl(Dao<Client, Long> dao) {
        this.dao = dao;
    }

    @Override
    public Long save(Client client) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Client entity = client;

                Long clientId = client.getId();
                if (clientId != null && clientId > 0) {
                    entity = dao.findById(clientId).orElse(client);
                    entity.setName(client.getName());
                    entity.setAge(client.getAge());
                    entity.setAddress(client.getAddress());
                    entity.setPhones(client.getPhones());
                }

                for (PhoneDataSet phone : entity.getPhones()) {
                    phone.setClient(entity);
                }
                entity.getAddress().setClient(entity);

                Long id = dao.insertOrUpdate(entity);

                sessionManager.commitSession();
                log.info("created entity: {}", id);
                return id;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Client> getById(Long id) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Client> entity = dao.findById(id);

                log.info("entity: {}", entity.orElse(null));
                return entity;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
