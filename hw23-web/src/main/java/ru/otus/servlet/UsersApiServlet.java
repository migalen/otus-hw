package ru.otus.servlet;

import ru.otus.service.db.UserService;
import ru.otus.model.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class UsersApiServlet extends HttpServlet {

    private final UserService userService;

    public UsersApiServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> userList = userService.findAll();
        response.setContentType("application/html;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(getHtmlTableUser(userList));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> params = request.getParameterMap();
        User user = new User(null, params.get("name")[0], params.get("login")[0], params.get("password")[0]);
        userService.save(user);
        response.setContentType("application/html;charset=UTF-8");
    }

    protected String getHtmlTableUser(List<User> userList) throws IOException {
        StringBuffer htmlTable = new StringBuffer();
        htmlTable.append("<table>");
        for (User u : userList) {
            htmlTable.append("<tr>")
                    .append("<td>").append(u.getId().toString()).append("</td>")
                    .append("<td>").append(u.getName()).append("</td>")
                    .append("<td>").append(u.getLogin()).append("</td>")
                    .append("<td>").append(u.getPassword()).append("</td>")
                    .append("</tr>");
        }
        htmlTable.append("</table>");
        return String.valueOf(htmlTable);
    }

}
