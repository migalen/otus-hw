package ru.otus.service;

import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.model.Account;
import ru.otus.sessionmanager.SessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DbServiceAccountImpl implements DBServiceClient<Account, String> {

    private final JdbcMapper<Account> jdbcMapper;
    private final SessionManager sessionManager;

    private static final Logger logger = LoggerFactory.getLogger(DbServiceAccountImpl.class);

    public DbServiceAccountImpl(JdbcMapper<Account> jdbcMapper, SessionManager sessionManager) {
        this.jdbcMapper = jdbcMapper;
        this.sessionManager = sessionManager;
    }

    @Override
    public String saveEntity(Account account) {
        sessionManager.beginSession();
        try {
            jdbcMapper.insertOrUpdate(account);
            sessionManager.commitSession();

            logger.info("saved account: {}", account);
            return account.getId();
        } catch (Exception e) {
            sessionManager.rollbackSession();
            throw new DbServiceException(e);
        }
    }

    @Override
    public Optional<Account> getEntityById(String id) {
        sessionManager.beginSession();
        try {
            Account account = jdbcMapper.findById(id, Account.class);

            logger.info("account: {}", account);
            return Optional.ofNullable(account);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sessionManager.rollbackSession();
        }
        return Optional.empty();
    }
}
