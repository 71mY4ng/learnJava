package com.company.algorithm.sort;

import org.apache.commons.lang3.time.StopWatch;

import java.util.Arrays;
import java.util.Random;

/**
 * 与归并排序相似的“分治思想”, 划分子区间来排序
 *
 * 但快速排序选举一个中间基准pivot 来作为每次分区的中间界，使得一边区间小于pivot 一边区间大于pivot, 以此类推
 *
 * 快速排序适用于大量元素，小集合性能不优于插入排序
 *
 * 根据集合内元素的重复数量，还可以试用三向切分来进行优化，即
 * <pre>
 *  if (a[i] > v)       exchange(a, lt++, i++);
 *  else if (a[i] < v)  exchange(a, i, gt--);
 *  else                i++;
 * </pre>
 *
 * 使得 a[lo..lt-1] < v, a[lt..gt] == v , v < a[gt + 1..hi]
 *
 */
public class QuickSort extends BaseSort {

    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    public static void sort3way(Comparable[] a) {
        quick3Way(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {

        if (hi <= lo) return;

        int pivot = partition(a, lo, hi);
        sort(a, lo, pivot - 1);
        sort(a, pivot + 1, hi);
    }

    private static void quick3Way(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;

        int lt = lo, gt = hi;
        int i = lo + 1;
        final Comparable v = a[lo];

        while (i <= gt) {
            int cmp = a[i].compareTo(v);

            if (cmp < 0) {
                exchange(a, lt++, i++);
            } else if (cmp > 0) {
                exchange(a, i, gt--);
            } else {
                i++;
            }
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        final Comparable v = a[lo];

        while (true) {
            while (less(a[++i], v)) if (i == hi) break;

            while (less(v, a[--j])) if (j == lo) break;

            if (i >= j) break;

            exchange(a, i, j);
        }

        exchange(a, lo, j);
        return j;
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{5 ,3 ,2, 1, 0, 9, 4, 6 ,7, 8,};

        final Random random = new Random();
        final Integer[] integers = random.ints(100, 0, 100).boxed().toArray(Integer[]::new);

        final Integer[] copy = Arrays.copyOf(integers, integers.length);

        QuickSort.show(integers);

        StopWatch sw = new StopWatch();
        sw.start();
        QuickSort.sort(integers);
        sw.stop();
        System.out.println("quick time:" + sw.getNanoTime());
        sw.reset();
        sw.start();
        QuickSort.sort(copy);
        sw.stop();
        System.out.println("quick3way time:" + sw.getNanoTime());

        System.out.println("end!");
        System.out.print("quick: \t\t");
        QuickSort.show(integers);
        System.out.print("quick3way: \t\t");
        QuickSort.show(copy);

    }
}
