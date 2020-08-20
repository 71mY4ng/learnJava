package com.company.demo;

import org.junit.Test;

import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {

    private static Vector<Integer> vec = new Vector<>();

    @Test
    public void test_vector() {
        ReentrantLock sizeof = new ReentrantLock();

        while (true) {
            for (int i = 0; i < 10; i++) {
                vec.add(i);
            }

            Thread removeThread = new Thread(() -> {
                for (int i = 0; i < vec.size(); i++) {
                    vec.remove(i);
                }
            });

            Thread printThread = new Thread(() -> {
                for (Integer integer : vec) {
                    System.out.println(integer);
                }
            });

            removeThread.start();
            printThread.start();

            while (Thread.activeCount()  > 20 );
        }

    }
}
