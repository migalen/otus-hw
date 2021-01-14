package src.test.java.ru.otus;

import src.main.java.ru.otus.TestFrameworkCore;
import src.test.java.ru.otus.tests.AfterFailureSuite;
import src.test.java.ru.otus.tests.BeforeFailureSuite;
import src.test.java.ru.otus.tests.TestFailureSuite;
import src.test.java.ru.otus.tests.TestSuccessfulSuite;

public class TestRunner {

    public static void main(String[] args) {
        TestFrameworkCore.run(TestSuccessfulSuite.class);
        TestFrameworkCore.run(TestFailureSuite.class);
        TestFrameworkCore.run(BeforeFailureSuite.class);
        TestFrameworkCore.run(AfterFailureSuite.class);
    }

}
