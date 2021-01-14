package src.main.java.ru.otus;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionUtils {

    private ReflectionUtils() {}

    public static <T> T instantiate(Class<T> type, Object... args) {
        Constructor<T> constructor;
        try {
            if (args.length == 0) {
                constructor = type.getDeclaredConstructor();
                return makeAccessible(constructor, null).newInstance();
            } else {
                final Class<?>[] classes = toClasses(args);
                constructor = type.getDeclaredConstructor(classes);
                return makeAccessible(constructor, null).newInstance();
            }
        } catch (Throwable t) {
            throw throwAsUncheckedException(t);
        }
    }

    public static Object callMethod(Object object, Method method, Object... args) {
        try {
            return makeAccessible(method, object).invoke(object, args);
        } catch (Throwable t) {
            throw throwAsUncheckedException(t);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> RuntimeException throwAsUncheckedException(Throwable t) throws T {
        throw (T) t;
    }

    private static <T extends AccessibleObject> T makeAccessible(T obj, Object target) {
        if (!obj.canAccess(target)) {
            obj.setAccessible(true);
        }
        return obj;
    }

    private static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args)
                .map(Object::getClass)
                .toArray(Class<?>[]::new);
    }
}
