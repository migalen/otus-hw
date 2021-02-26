package ru.otus;

public class Main {

    private final Object lock = new Object();

    private volatile boolean oddFlag = false;
    private volatile boolean directionFlag = true;
    private volatile int number = 1;

    public static void main(String[] args) {
        Main main = new Main();
        new Thread(main::printOddNumbers, "Thead 1 odd").start();
        new Thread(main::printEvenNumbers, "Thead 2 even").start();
    }

    private void printOddNumbers() {
        synchronized (lock) {
            while (number > 0) {
                while (oddFlag) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println(String.format("Error in printOddNumbers: %s", e));
                    }
                }
                printNumber();
                nextNumber();
                lock.notifyAll();
            }
        }
    }

    private void printEvenNumbers() {
        synchronized (lock) {
            while (number > 1) {
                while (!oddFlag) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println(String.format("Error in printEvenNumbers: %s", e));
                    }
                }
                printNumber();
                nextNumber();
                lock.notifyAll();
            }
        }
    }

    private void nextNumber() {
        if (number == 10) {
            directionFlag = false;
        }
        int value = directionFlag ? number++ : number--;
        oddFlag = value%2 > 0 ? true : false;
    }

    private void printNumber() {
        System.out.println(String.format("%s: %s", Thread.currentThread().getName(), number));
    }
}
