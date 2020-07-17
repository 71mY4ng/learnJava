package com.company.collections;

import java.util.HashSet;
import java.util.TreeSet;

public class SetDemo {
    public static HashSet<Integer> primeSet = new HashSet<>();

    public static void prime(int n) {
        boolean isPrime = true;
        for (int i = 2; i <= n; i++) {
            for (int d = 2; d < i; d++) {
                if (i % d == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                primeSet.add(i);
            }
            isPrime = true;
        }
    }
    public static void main(String[] args) {

        int n = 100;
        prime(n);
        for (Integer primenum : primeSet) {
            System.out.println(primenum);
        }


    }
}
