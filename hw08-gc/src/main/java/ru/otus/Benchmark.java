package ru.otus;

import java.util.ArrayList;
import java.util.List;

public class Benchmark implements BenchmarkMBean {
    private final int loopCounter;
    private volatile int size = 0;
    private List<Integer> trashList = new ArrayList();

    public Benchmark(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size:" + size);
        this.size = size;
    }

    public void run() throws InterruptedException {
        //testNormal();
        testLeak();
    }

    public void testNormal() throws InterruptedException {
        for (int idx = 0; idx < loopCounter; idx++) {
            int local = size;
            for (int i = 0; i < local; i++) {
                trashList.add(1000);
            }
            trashList.clear();
            Thread.sleep(10);
        }
    }

    public void testLeak() throws InterruptedException {
        for (int idx = 0; idx < loopCounter; idx++) {
            int local = size;
            for (int i = 0; i < local; i++) {
                trashList.add(1000);
            }
            for (int i = 0; i < local/2; i++) {
                trashList.remove(i);;
            }
            Thread.sleep(3000);
        }
    }

}
