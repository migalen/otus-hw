package ru.otus.tests;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestSuccessfulSuite {
    @Before
    void beforeEach() {
        System.out.println("Before in TestSuccessfulSuite");
    }

    @Test
    void test1() {
        System.out.println("Test1 in TestSuccessfulSuite");
    }

    @Test
    void test2() {
        System.out.println("Test2 in TestSuccessfulSuite");
    }

    @After
    void afterEach() {
        System.out.println("After in TestSuccessfulSuite");
    }

}
