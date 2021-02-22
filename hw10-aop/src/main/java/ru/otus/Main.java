package ru.otus;

import ru.otus.proxy.TestLoggingProxy;
import ru.otus.proxy.ITestLogging;

public class Main {
    public static void main(String[] args) {
        ITestLogging iTestLogging = TestLoggingProxy.create();
        iTestLogging.calculation(1, 2);
    }
}
