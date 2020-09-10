package com.company.algorithm.sort;



/**
 */
public class SelectionSort extends BaseSort {

    public static void sort(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            int minIndex = i;

            for (int j = i + 1; j < a.length; j++) {
                if (less(a[j], a[minIndex])) minIndex = j;
            }
            exchange(a, i, minIndex);
        }
    };

    public static void main(String[] args) {
        Integer[] a = new Integer[]{9, 8 ,7, 6, 5 ,4 ,3 ,2, 1, 0};

        Integer[] a2 = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        SelectionSort.sort(a2);
        SelectionSort.show(a2);
        System.out.println(SelectionSort.isSorted(a2));
    }
}
