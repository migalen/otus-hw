package ru.otus.service;

import ru.otus.dto.UserData;
import ru.otus.dto.UserListData;

import ru.otus.messagesystem.client.MessageCallback;

public interface FrontendService {

    void findAll(MessageCallback<UserListData> callback);

    void save(UserData userData);

    void delete(String login);
}
