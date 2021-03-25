package ru.otus.messagesystem.message;

public enum MessageType {
    GET_USER_LIST_DATA("GetUserListData"),
    SAVE_USER_DATA("SaveUserData"),
    DELETE_USER_DATA("DeleteUserData");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
