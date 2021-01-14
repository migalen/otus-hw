package src.main.java.ru.otus;

import src.main.java.ru.otus.annotations.After;
import src.main.java.ru.otus.annotations.Before;
import src.main.java.ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestFrameworkCore {

    public static void run(Class<?> testClass) {
        try {
            new Run(testClass).executeSuite();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static class Run {
        private final List<Method> beforeEach = new ArrayList<>();
        private final List<Method> tests = new ArrayList<>();
        private final List<Method> afterEach = new ArrayList<>();
        private final Class<?> testClass;
        private Run(Class<?> testClass) {
            this.testClass = testClass;
        }

        private void executeSuite() {
            System.out.println("___________________________________");
            System.out.println("Executing suite: " + testClass.getName());
            dispatchMethods(testClass.getDeclaredMethods());

            int countFailureTest = 0;
            for (Method test : tests) {
                Object object = ReflectionUtils.instantiate(testClass);
                HashMap<String, String> statusTest = new HashMap<>();
                beforeEach.forEach(method -> {
                    try {
                        ReflectionUtils.callMethod(object, method);
                    } catch (Exception ex) {
                        System.out.println("Before is not successful: " + method.getName());
                        ex.printStackTrace();
                    }
                });

                try {
                    ReflectionUtils.callMethod(object, test);
                } catch (Exception ex) {
                    countFailureTest++;
                    System.out.println("Test is not successful: " + test.getName());
                }

                afterEach.forEach(method -> {
                    try {
                        ReflectionUtils.callMethod(object, method);
                    } catch (Exception ex) {
                        System.out.println("After is not successful: " + method.getName());
                        ex.printStackTrace();
                    }
                });
            }
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
