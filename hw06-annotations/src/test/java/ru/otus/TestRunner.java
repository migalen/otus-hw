package ru.otus;

import ru.otus.TestFrameworkCore;
import ru.otus.tests.AfterFailureSuite;
import ru.otus.tests.BeforeFailureSuite;
import ru.otus.tests.TestFailureSuite;
import ru.otus.tests.TestSuccessfulSuite;

public class TestRunner {

    public static void main(String[] args) {
        TestFrameworkCore.run(TestSuccessfulSuite.class);
        TestFrameworkCore.run(TestFailureSuite.class);
        TestFrameworkCore.run(BeforeFailureSuite.class);
        TestFrameworkCore.run(AfterFailureSuite.class);
    }

}
