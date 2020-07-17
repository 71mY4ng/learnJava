package com.company.concurrent.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CounterDemo1 {
    private final Map<String, Long> urlCounter = new ConcurrentHashMap<>();

    // 接口调用次数+1
    public long increase(String url) {
        Long oldValue = urlCounter.get(url);
        Long newValue = (oldValue == null) ? 1L : oldValue + 1;
        urlCounter.put(url, newValue);
        return newValue;
    }

    // 改进的increase方法增加了CAS操作
    public long increase2(String url) {
        Long oldValue, newValue;
        while (true) {
            oldValue = urlCounter.get(url);
            if (oldValue == null) {
                newValue = 1l;

                // 初始化成功，退出
                if (urlCounter.putIfAbsent(url, 1l) == null) // CAS 操作，初始化，如果存在KV，返回，不存在返回空
                    break;
                // 初始化失败，因为已经初始化过了
            } else {
                newValue = oldValue + 1;
                // +1 成功，退出循环
                if (urlCounter.replace(url, oldValue, newValue)) // CAS操作
                    break;
                // +1 失败，因为其他线程已经修改了旧的值
            }
        }
        return newValue;
    }

    public Long getCount(String url) {
        return urlCounter.get(url);
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        final CounterDemo1 counterDemo = new CounterDemo1();
        int callTime = 100000;
        final String url = "http://localhost:8080/hello";
        CountDownLatch countDownLatch = new CountDownLatch(callTime);
        for (int i = 0; i < callTime; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    counterDemo.increase(url);
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        System.out.println("调用次数：" + counterDemo.getCount(url));
    }
}