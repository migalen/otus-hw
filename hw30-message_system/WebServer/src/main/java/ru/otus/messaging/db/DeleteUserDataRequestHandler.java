package ru.otus.messaging.db;

import ru.otus.dto.UserData;
import ru.otus.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageHelper;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DeleteUserDataRequestHandler implements RequestHandler<UserData> {

    private final UserService service;

    @Override
    public Optional<Message> handle(Message msg) {
        UserData data = MessageHelper.getPayload(msg);
        service.deleteByLogin(data.getLogin());
        return Optional.empty();
    }
}
