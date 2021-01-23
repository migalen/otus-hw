package ru.otus.listener.homework;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class MessageHistory {
    private final Message oldMsg;
    private final Message newMsg;
    private final LocalDateTime changedDateTime;

    public MessageHistory(Message oldMsg, Message newMsg, LocalDateTime changedDateTime) {
        this.oldMsg = MessageHistoryUtil.copyOf(oldMsg);
        this.newMsg = MessageHistoryUtil.copyOf(newMsg);
        this.changedDateTime = changedDateTime;
    }

    public Message getOldMsg() {
        return oldMsg;
    }

    public Message getNewMsg() {
        return newMsg;
    }

    public LocalDateTime getChangedDateTime() {
        return changedDateTime;
    }

    @Override
    public String toString() {
        return "MessageHistory{" +
                "oldMsg=" + oldMsg +
                ", newMsg=" + newMsg +
                ", changedDateTime=" + changedDateTime +
                '}';
    }
}

