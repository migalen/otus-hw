package src.test.java.ru.otus.tests;

import src.main.java.ru.otus.annotations.After;
import src.main.java.ru.otus.annotations.Before;
import src.main.java.ru.otus.annotations.Test;

public class AfterFailureSuite {

    @Before
    void beforeEach() {
        System.out.println("BeforeEach in AfterFailureSuite");
    }

    @Test
    void test() {
        System.out.println("Test in AfterFailureSuite");
    }

    @After
    void afterEachOne() {
        throw new RuntimeException();
    }

    @After
    void afterEachTwo() {
        System.out.println("After2 in AfterFailureSuite");
    }

}
