package ru.otus.dto;

import ru.otus.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.otus.messagesystem.client.ResultDataType;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@AllArgsConstructor
public class UserListData extends ResultDataType {
    private final List<UserData> users;

    public static UserListData toUserListData(List<User> users) {
        return new UserListData(
                users.stream().map(UserData::toUserData).collect(Collectors.toList())
        );
    }
}
