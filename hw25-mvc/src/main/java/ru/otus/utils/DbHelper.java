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

    private static final Logger log = LoggerFactory.getLogger(DbHelper.class);

    private DbHelper() {
    }

    public static SessionManagerHibernate buildSessionManager() {
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        String dbUrl = configuration.getProperty("hibernate.connection.url");
        String dbUserName = configuration.getProperty("hibernate.connection.username");
        String dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration, User.class);
        return new SessionManagerHibernate(sessionFactory);
    }

    public static void populateDb(UserService userService) {
        log.info("populateDb() - start");

        if (userService.findAll().isEmpty()) {
            List<User> users = List.of(
                    new User(1L, "super_user_1", "login1", "super_password_1"),
                    new User(2L, "super_user_2", "login2", "super_password_2"),
                    new User(3L, "super_user_3", "login3", "super_password_3"),
                    new User(4L, "super_user_4", "login4", "super_password_4"),
                    new User(5L, "super_user_5", "login5", "super_password_5"),
                    new User(6L, "Migal Ujin", "migal_en", "super_password")
            );
            users.forEach(userService::save);
        }

        log.info("populateDb() - end");
    }
}
