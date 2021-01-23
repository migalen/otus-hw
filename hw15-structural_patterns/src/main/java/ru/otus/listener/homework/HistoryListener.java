package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;

public class HistoryListener implements Listener {

    private final Deque<MessageHistory> stack = new ArrayDeque<>();

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        MessageHistory messageHistory = new MessageHistory(oldMsg, newMsg, LocalDateTime.now());
        stack.push(messageHistory);
    }

    public MessageHistory restore() {
        var messageHistory = stack.pop();
        System.out.println("Message changed at:" + messageHistory.getChangedDateTime());
        return messageHistory;
    }
}