package ru.otus.tests;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestFailureSuite {


    @Before
    void beforeEach() {
        System.out.println("Before in TestFailureSuite");
    }

    @Test
    void test() {
        throw new RuntimeException();
    }

    @After
    void afterEach() {
        System.out.println("After in TestFailureSuite");
    }
}
