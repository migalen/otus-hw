package ru.otus.proxy;

import ru.otus.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestLoggingProxy {

    private TestLoggingProxy() {
    }

    public static ITestLogging create() {
        InvocationHandler handler = new MyHandler(new TestLogging());
        return (ITestLogging) Proxy.newProxyInstance(TestLoggingProxy.class.getClassLoader(),
                new Class<?>[]{ITestLogging.class}, handler);
    }

    static class MyHandler implements InvocationHandler {
        private final ITestLogging calculation;
        private final List<String> loggingMethods;

        MyHandler(ITestLogging calculation) {
            this.calculation = calculation;
            loggingMethods = methodLog(calculation.getClass().getMethods());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isMethodLog(method)) {
                System.out.println("Executed method: " + method.getName() + ": " + Arrays.toString(args));
            }
            return method.invoke(calculation, args);
        }

        private List<String> methodLog(Method[] methods) {
            List<String> methodsToLog = new ArrayList<>();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Log.class)) {
                    methodsToLog.add(method.getName());
                }
            }
            Collections.sort(methodsToLog);
            return methodsToLog;
        }

        private boolean isMethodLog(Method method) {
            return Collections.binarySearch(loggingMethods, method.getName()) >= 0;
        }

    }
}
