package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class TestFrameworkCore {

    public static void run(Class<?> testClass) {
        try {
            new Runner(testClass).executeSuite();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static class Runner {
        private final List<Method> beforeEach = new ArrayList<>();
        private final List<Method> tests = new ArrayList<>();
        private final List<Method> afterEach = new ArrayList<>();
        private final Class<?> testClass;
        private Runner(Class<?> testClass) {
            this.testClass = testClass;
        }

        private void executeSuite() {
            System.out.println("___________________________________");
            System.out.println("Executing suite: " + testClass.getName());
            dispatchMethods(testClass.getDeclaredMethods());

            int countFailureTest = 0;
            for (Method test : tests) {
                Object object = ReflectionUtils.instantiate(testClass);
                try {
                    executeBefore(object);
                    ReflectionUtils.callMethod(object, test);
                } catch (Exception ex) {
                    countFailureTest++;
                    System.out.println("Test is not successful: " + test.getName());
                }
                executeAfter(object);
                printTestsStatistic(countFailureTest);
            }
        }

        private void executeBefore(Object object) {
            beforeEach.forEach(method -> {
                try {
                    ReflectionUtils.callMethod(object, method);
                } catch (Exception ex) {
                    System.out.println("Before is not successful: " + method.getName());
                    ex.printStackTrace();
                    throw ex;
                }
            });
        }

        private void executeAfter(Object object) {
            afterEach.forEach(method -> {
                try {
                    ReflectionUtils.callMethod(object, method);
                } catch (Exception ex) {
                    System.out.println("After is not successful: " + method.getName());
                    ex.printStackTrace();
                }
            });
        }

        private void printTestsStatistic(int countFailureTest) {
            System.out.println("Count tests: " + tests.size());
            System.out.println("Successful tests: " + (tests.size() - countFailureTest));
            System.out.println("Failure tests: " + countFailureTest);
        }

        private void dispatchMethods(Method[] methods) {
            for (Method method : methods) {
                method.setAccessible(true);
                if (method.isAnnotationPresent(Before.class) && !Modifier.isStatic(method.getModifiers())) {
                    beforeEach.add(method);
                }
                if (method.isAnnotationPresent(Test.class) && !Modifier.isStatic(method.getModifiers())) {
                    tests.add(method);
                }
                if (method.isAnnotationPresent(After.class) && !Modifier.isStatic(method.getModifiers())) {
                    afterEach.add(method);
                }
                method.setAccessible(false);
            }
        }
    }
}
