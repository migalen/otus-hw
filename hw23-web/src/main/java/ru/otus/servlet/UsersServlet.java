package ru.otus.servlet;

import ru.otus.service.db.UserService;
import ru.otus.model.User;
import ru.otus.processor.TemplateProcessor;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

public class UsersServlet extends HttpServlet {

    private final UserService userService;
    private final TemplateProcessor templateProcessor;

    private static final String USERS_PAGE_TEMPLATE = "users.html";

    public UsersServlet(TemplateProcessor templateProcessor, UserService userService) {
        this.templateProcessor = templateProcessor;
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, Collections.emptyMap()));
    }
}
