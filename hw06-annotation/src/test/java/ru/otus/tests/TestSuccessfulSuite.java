package src.test.java.ru.otus.tests;

import src.main.java.ru.otus.annotations.After;
import src.main.java.ru.otus.annotations.Before;
import src.main.java.ru.otus.annotations.Test;

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
