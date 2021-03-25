package ru.otus.controller;

import ru.otus.dto.UserData;
import ru.otus.dto.UserListData;
import ru.otus.dto.UserMessage;
import ru.otus.service.FrontendService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final FrontendService frontendService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/users")
    public void generateUserList() {
        log.info("generateUserList() - start");
        frontendService.findAll(this::sendUsers);
        log.info("generateUserList() - end");
    }

    public void sendUsers(UserListData data) {
        val users = data.getUsers();
        log.info("sendUsers() - info: number of users = {}", users.size());
        users.forEach(this::sendUser);
    }

    public void sendUser(UserData user) {
        val userMessage = new UserMessage(user.getName(), user.getLogin(), user.getPassword());
        messagingTemplate.convertAndSend("/topic/response", userMessage);
    }

    @MessageMapping("/user/create")
    @SendTo("/topic/response")
    public UserMessage createUser(UserMessage userMessage) {
        log.info("createUser() - start: userMessage = {}", userMessage);
        val user = new UserData(userMessage.getName(), userMessage.getLogin(), userMessage.getPassword());
        frontendService.save(user);
        log.info("createUser() - end");
        return new UserMessage(user.getName(), user.getLogin(), user.getPassword());
    }

    @MessageMapping("/user/delete.{login}")
    public void deleteUser(@DestinationVariable String login) {
        log.info("deleteUser() - start: login = {}", login);
        frontendService.delete(login);
        log.info("deleteUser() - end");
    }
}
