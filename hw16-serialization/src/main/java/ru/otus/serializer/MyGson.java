package ru.otus.serializer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringJoiner;

public class MyGson {

    public String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        StringBuilder json = new StringBuilder();
        serialize(obj, json, null);
        return String.valueOf(json);
    }

    private void serialize(Object obj, StringBuilder json, String key) {
        if (obj == null) {
            return;
        }
        writeKey(key, json);
        writeValue(obj, json);
    }

    private void writeKey(String key, StringBuilder json) {
        if (key != null) {
            json.append("\"");
            json.append(key);
            json.append("\":");
        }
    }

    private void writeValue(Object obj, StringBuilder json) {
        if (isPrimitive(obj)) {
            json.append(obj);
        }
        else {
            if (isString(obj)) {
                json.append("\"");
                json.append(obj);
                json.append("\"");
            } else {
                if (isArray(obj)) {
                    serializeArray(obj, json);
                } else {
                    if (isCollection(obj)) {
                        serializeCollection(obj, json);
                    }
                    else {
                        serializeOtherObject(obj, json);
                    }
                }
            }
        }
    }

    private boolean isPrimitive(Object obj) {
        if (obj instanceof Long) {
            return true;
        }
        if (obj instanceof Integer) {
            return true;
        }
        if (obj instanceof Short) {
            return true;
        }
        if (obj instanceof Boolean) {
            return true;
        }
        if (obj instanceof Double) {
            return true;
        }
        if (obj instanceof Float) {
            return true;
        }
        if (obj instanceof Byte) {
            return true;
        }
        return false;
    }

    private boolean isString(Object obj) {
        if (obj instanceof String) {
            return true;
        }
        return false;
    }

    private boolean isArray(Object obj) {
        return obj.getClass().isArray();
    }

    private boolean isCollection(Object obj) {
        return obj instanceof Collection;
    }

    private void serializeArray(Object obj, StringBuilder json) {
        boolean isFirst = true;
        json.append("[");
        for (int i = 0; i < Array.getLength(obj); i++) {
            if (Array.get(obj, i) != null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    json.append(",");
                }
                serialize(Array.get(obj, i), json, null);
            }
        }
        json.append("]");
    }

    private void serializeCollection(Object obj, StringBuilder json) {
        Iterator iterator = ((Collection) obj).iterator();
        boolean isFirst = true;
        json.append("[");
        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (object != null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    json.append(",");
                }
                serialize(object, json, null);
            }
        }
        json.append("]");
    }

    private void serializeOtherObject(Object obj, StringBuilder json) {
        Field[] fieldArray = obj.getClass().getDeclaredFields();
        boolean isFirst = true;
        json.append("{");
        for (int i = 0; i < fieldArray.length; i++) {
            try {
                Field field = fieldArray[i];
                field.setAccessible(true);
                if (field.get(obj) != null) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        json.append(",");
                    }
                    serialize(field.get(obj), json, field.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        json.append("}");
    }




}
