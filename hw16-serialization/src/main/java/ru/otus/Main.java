package ru.otus;

import com.google.gson.Gson;
import ru.otus.pojo.AnyObject;
import ru.otus.serializer.MyGson;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        MyGson myGson = new MyGson();

        List<String> list = new ArrayList<>();
        list.add("String_1");
        list.add("String_2");
        list.add("String_3");
        list.add("String_4");
        list.add("String_5");

        Object[] arrayObject = new Object[]{1,2,3,4,5};

        AnyObject testAnyObject = new AnyObject("STRING", 1, 2, true, false, list, arrayObject, null);

        String strGson = gson.toJson(testAnyObject);
        String strMyGson = myGson.toJson(testAnyObject);

        System.out.println("Gson   : " + strGson);
        System.out.println("MyJson : " + strMyGson);
    }
}
