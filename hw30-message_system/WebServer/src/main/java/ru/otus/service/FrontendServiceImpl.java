package ru.otus.service;

import ru.otus.config.AppProperties;
import ru.otus.dto.UserData;
import ru.otus.dto.UserListData;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;

@Service
@RequiredArgsConstructor
public class FrontendServiceImpl implements FrontendService {

    @Qualifier("frontendMsClient")
    private final MsClient frontendMsClient;
    private final AppProperties appProperties;

    @Override
    public void findAll(MessageCallback<UserListData> callback) {
        Message outMsg = frontendMsClient.produceMessage(appProperties.getDatabaseServiceClientName(),
                null, MessageType.GET_USER_LIST_DATA, callback);
        frontendMsClient.sendMessage(outMsg);
    }

    @Override
    public void save(UserData userData) {
        Message outMsg = frontendMsClient.produceMessage(appProperties.getDatabaseServiceClientName(),
                userData, MessageType.SAVE_USER_DATA, data -> {});
        frontendMsClient.sendMessage(outMsg);
    }

    @Override
    public void delete(String login) {
        Message outMsg = frontendMsClient.produceMessage(appProperties.getDatabaseServiceClientName(),
                new UserData(null, login, null), MessageType.DELETE_USER_DATA, data -> {});
        frontendMsClient.sendMessage(outMsg);
    }
}
