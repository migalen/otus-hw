package ru.otus;

import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static void main(String... args) {
        DIYarrayList diYarrayList = new DIYarrayList<>();

        diYarrayList.add(23);
        Collections.addAll(diYarrayList, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21);
        Collections.addAll(diYarrayList, 20, 18, 16, 14, 12, 10, 8, 6, 4, 2, 0);
        diYarrayList.add(-1);

        DIYarrayList copyDiYarrayList = new DIYarrayList(diYarrayList.size());
        Collections.copy(copyDiYarrayList, diYarrayList);

        Collections.sort(diYarrayList, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 == null || o2 == null)
                    return 0;
                return ((Integer) o1).compareTo((Integer) o2);
            }
        });
    }
}