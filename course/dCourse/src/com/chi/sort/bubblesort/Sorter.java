package com.chi.sort.bubblesort;

public class Sorter {
    // 给索引 交换元素
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
