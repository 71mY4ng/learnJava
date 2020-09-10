package com.company.algorithm.sort;

/**
 * Algorithm (ver 4) chapter 2.1.3
 *
 */
public class InsertionSort extends BaseSort {

    public static void sort(Comparable[] a) {
        int n = a.length;

        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                // 可以优化的一点是： 已排序的元素顺序已经可以确定，那么只需要在已排序的子集中找到位置插入，不需要逐一交换这位置之前的所有元素
                exchange(a, j, j - 1);
            }
        }
    };

    public static void main(String[] args) {
        Integer[] a = new Integer[]{9, 8 ,7, 6, 5 ,4 ,3 ,2, 1, 0};

//        Integer[] a2 = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        InsertionSort.sort(a);
        InsertionSort.show(a);
    }

}
