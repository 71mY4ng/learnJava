package com.company.core;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConcurrentDemo {

    private static volatile boolean done = false;

    private static void runnableTest() {
        Runnable hellos = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("Hello " + i);
            }
            done = true;
        };

        Runnable goodbyes = () -> {
            int i = 1;
            while (!done) i++;
            System.out.println("GoodBye " + i);
        };

        Executor executor = Executors.newCachedThreadPool();
        executor.execute(hellos);
        executor.execute(goodbyes);

    }

    private static void concurrentHashMapTest() {
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

    }

    public static void main(String[] args) {
        runnableTest();
    }
}
