package com.company.algorithm.sort;

/**
 * 相比 选择排序 exchange 的次数要更多
 */
public class BubbleSort extends BaseSort {

    public static void sort(Comparable[] a) {
        if (a == null || a.length < 2) return;

        int maxIndex = a.length;
        while (maxIndex > 0) {
            for (int i = 0; i < maxIndex; i++) {
                if (greater(a[i], a[i + 1])) exchange(a, i, i + 1);
            }
            maxIndex--;
        }
    };

    public static void main(String[] args) {
        Integer[] a = new Integer[]{9, 8 ,7, 6, 5 ,4 ,3 ,2, 1, 0};

//        Integer[] a2 = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        BubbleSort.sort(a);
        BubbleSort.show(a);
        System.out.println(BubbleSort.isSorted(a));
    }
}
