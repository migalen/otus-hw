package src.test.java.ru.otus.tests;

import src.main.java.ru.otus.annotations.After;
import src.main.java.ru.otus.annotations.Before;
import src.main.java.ru.otus.annotations.Test;

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
