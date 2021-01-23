package ru.otus;

public enum Bill {
    FIFTY_RUB(50),
    HUNDRED_RUB(100),
    FIVE_HUNDRED_RUB(500),
    THOUSAND_RUB(1000);

    private final long value;

    Bill(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
