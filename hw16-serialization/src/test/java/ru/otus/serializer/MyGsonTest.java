package ru.otus.serializer;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyGsonTest {
    Gson gson;
    MyGson serializer;

    @BeforeEach
    public void setUp() {
        gson = new Gson();
        serializer = new MyGson();
    }

    @Test
    void compareGsonAndMyGson() {
        assertEquals(gson.toJson(null), serializer.toJson(null));
        assertEquals(gson.toJson((byte)1), serializer.toJson((byte)1));
        assertEquals(gson.toJson((short)1f), serializer.toJson((short)1f));
        assertEquals(gson.toJson(1), serializer.toJson(1));
        assertEquals(gson.toJson(1L), serializer.toJson(1L));
        assertEquals(gson.toJson(1f), serializer.toJson(1f));
        assertEquals(gson.toJson(1d), serializer.toJson(1d));
        assertEquals(gson.toJson("aaa"), serializer.toJson("aaa"));
        assertEquals(gson.toJson('a'), serializer.toJson('a'));
        assertEquals(gson.toJson(new int[] {1, 2, 3}), serializer.toJson(new int[] {1, 2, 3}));
        assertEquals(gson.toJson(List.of(1, 2 ,3)), serializer.toJson(List.of(1, 2 ,3)));
        assertEquals(gson.toJson(Collections.singletonList(1)), serializer.toJson(Collections.singletonList(1)));
    }
}
