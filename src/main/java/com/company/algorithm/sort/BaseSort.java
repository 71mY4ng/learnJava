package com.company.algorithm.sort;

public class BaseSort {

    public static void sort(Comparable[] a) {};

    /**
     * v < w
     * @param v
     * @param w
     * @return
     */
    static boolean less(Comparable v, Comparable w) {
        return w.compareTo(v) > 0;
    }

    static boolean greater(Comparable v, Comparable w) {
        return v.compareTo(w) > 0;
    }

     static void exchange(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

     static void show(Comparable[] a) {

        System.out.print("[");
        for (Comparable item : a) {
            System.out.print(item + ", ");
        }
        System.out.println("]");
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }

}
