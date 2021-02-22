package ru.otus.model;

import ru.otus.annotaion.Id;

public class Account {

    @Id
    private final String id;
    private final String type;
    private final double rest;

    public Account(String id, String type, double rest) {
        this.id = id;
        this.type = type;
        this.rest = rest;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getRest() {
        return rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                       "id='" + id + '\'' +
                       ", type='" + type + '\'' +
                       ", rest=" + rest +
                       '}';
    }
}
