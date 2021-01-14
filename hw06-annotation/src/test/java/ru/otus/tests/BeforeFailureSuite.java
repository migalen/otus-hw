package src.test.java.ru.otus.tests;

import src.main.java.ru.otus.annotations.After;
import src.main.java.ru.otus.annotations.Before;
import src.main.java.ru.otus.annotations.Test;

public class BeforeFailureSuite {

    @Before
    void beforeEach() {
        //System.out.println("Before");
        throw new RuntimeException();
    }

    @Test
    void test() {
        System.out.println("Test in BeforeFailureSuite");
    }

    @After
    void afterEach() {
        System.out.println("After in BeforeFailureSuite");
    }
}
