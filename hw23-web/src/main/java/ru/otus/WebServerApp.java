package ru.otus;

import ru.otus.dao.UserDaoImpl;
import ru.otus.flyway.MigrationsExecutorFlyway;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.processor.TemplateProcessor;
import ru.otus.processor.TemplateProcessorImpl;
import ru.otus.server.SecuredWebServer;
import ru.otus.server.WebServer;
import ru.otus.service.auth.UserAuthService;
import ru.otus.service.auth.UserAuthServiceImpl;
import ru.otus.service.db.UserService;
import ru.otus.service.db.UserServiceImpl;
import ru.otus.utils.DbHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public final class WebServerApp {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    private static final Logger log = LoggerFactory.getLogger(WebServerApp.class);

    public static void main(String[] args) throws Exception {
        log.info("main() - start: args = {}", Arrays.toString(args));

        SessionManagerHibernate sessionManager = DbHelper.buildSessionManager();
        new MigrationsExecutorFlyway(DbHelper.getUrl(), DbHelper.getUsername(), DbHelper.getPassword()).executeMigrations();

        UserService userService = new UserServiceImpl(new UserDaoImpl(sessionManager));

        log.info("main() - info: db prepared");

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userService);
        WebServer usersWebServer =
                new SecuredWebServer(WEB_SERVER_PORT, authService, userService, templateProcessor);
        log.info("main() - info: webserver prepared");

        usersWebServer.start();
        log.info("main() - info: webserver started");

        usersWebServer.join();

        log.info("main() - end");
    }
}
