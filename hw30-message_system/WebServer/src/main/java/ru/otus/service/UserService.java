package ru.otus.service;

import ru.otus.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User save(User user);

    void deleteByLogin(String login);
}
