package com.company.modal;

public class Sorting {
    public static int ARRAY[] = {3,2,4,1,5,6,7,10,9,8,12,14,13};

    public void BubbleSorting (int[] array) {
        print(array);
        swap(array, 2, 3);
        print(array);
    }

    private void swap(int[] target, int lIndex, int rIndex) {
        int temp = target[lIndex];
        target[lIndex] = target[rIndex];
        target[rIndex] = temp;
    }

    private void print(int[] array) {
        System.out.println("array: { ");
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i] + ", ");
        }
        System.out.println("}");
    }
}
