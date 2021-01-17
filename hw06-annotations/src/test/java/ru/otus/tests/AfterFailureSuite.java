package ru.otus.tests;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

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
