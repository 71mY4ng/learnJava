package com.company;

public class Main {

    public static void main(String[] args) {
        Prime(100);
    }


    public static void Prime(int num) {
        boolean isPrime = true;
        int primeNum = 0;
        for (int n = 2; n <= num; n++) {
            for (int d = 2; d < n; d++) {
                if (n % d == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                primeNum++;
                System.out.print(n + "\t");
                if (primeNum % 5 == 0)
                    System.out.println();
            }
            isPrime = true;
        }
        System.out.println("prime nums above less than " + num);
    }
}
