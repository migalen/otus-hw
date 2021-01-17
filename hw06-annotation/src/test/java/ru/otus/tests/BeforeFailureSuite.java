package ru.otus.tests;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

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
