package com.company.concurrent;

public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        int segmentShift = 28;  // 高4位参加hash
        int segmentMask = 15;   // 掩码 1111
        String key = "imkey";
//        int hash = Integer.parseInt("0001111", 2);
        int hash = key.hashCode();
        System.out.println(hash);

        System.out.println(hash << segmentShift);
        hash = (hash >>> segmentShift) & segmentMask;
        System.out.println(hash);
        hash = (hash >>> segmentShift) & segmentMask;
        System.out.println(hash);
    }
}
