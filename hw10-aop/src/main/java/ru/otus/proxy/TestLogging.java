package ru.otus.proxy;

import ru.otus.annotations.Log;

public class TestLogging implements ITestLogging {
    public TestLogging() {
    }

    @Override
    @Log
    public void calculation(int a, int b) {
        System.out.println(a + b);
    }
}
