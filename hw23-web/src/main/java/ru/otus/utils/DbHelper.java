package ru.otus.utils;

import ru.otus.flyway.MigrationsExecutorFlyway;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.model.User;
import ru.otus.service.db.UserService;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class DbHelper {

    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    private DbHelper() {
    }

    public static String getUrl() {
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        return configuration.getProperty("hibernate.connection.url");
    }

    public static String getUsername() {
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        return configuration.getProperty("hibernate.connection.username");
    }

    public static String getPassword() {
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        return configuration.getProperty("hibernate.connection.password");
    }

        public static SessionManagerHibernate buildSessionManager() {
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration, User.class);
        return new SessionManagerHibernate(sessionFactory);
    }
}
