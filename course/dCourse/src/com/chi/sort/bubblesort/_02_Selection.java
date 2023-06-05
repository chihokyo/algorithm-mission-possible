package com.chi.sort.bubblesort;

import static com.chi.sort.bubblesort.Sorter.swap;

/**
 * 选择排序
 */
public class _02_Selection {
    public static void main(String[] args) {
        int[] array = {155, 2, 7, 3, 1, 6, 4, 2, 4, 4, 55, 8, 7, 2};

        System.out.println("排序前的数组：");
        printArray(array);

        // selection1(array); // 传统写法
        // selection2(array); // 双指针 用while
        selection3(array); // 双指针 用for

        System.out.println("排序后的数组：");
        printArray(array);
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    /**
     * 传统写法
     * 时间复杂度O(n^2)
     *
     * @param array
     */
    public static void selection1(int[] array) {
        int n = array.length;
        // 去掉自身 所以是长度-1轮
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i; // 假设此时最小的就是minIndex
            // 这里必须遍历到最后一个值 如果你写的是n-1 最后一个值就不会被比较了
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    // 交换
                    swap(array, j, minIndex);
                }
            }
        }
    }

    /**
     * 双指针-对撞指针while写法
     *
     * @param array
     */
    public static void selection2(int[] array) {
        int len = array.length;
        int left = 0, right = len - 1;
        while (left < right) {
            int minIndex = left;
            int maxIndex = right;
            for (int i = left; i <= right; i++) {
                if (array[i] < array[minIndex]) minIndex = i;
                if (array[i] > array[maxIndex]) maxIndex = i;
            }
            // 走到这里应该找到了当前未排序最大的索引和最小的索引了
            Sorter.swap(array, minIndex, left); // 先交换一下最小的
            // 这里想当重要是关键 为什么需要minIndex复制给maxIndex呢？
            // 因为你找到了最小值的索引会跟left进行交换元素吧 如果此时left索引所在的元素就是最大值
            // 这样一来，最大值（left和maxIndex 相同 但是left所在的元素）正好刚刚被你给换走了。换到哪里去了？换到了minIndex去了
            // 所以你必须找回来
            if (maxIndex == left) maxIndex = minIndex;
            Sorter.swap(array, maxIndex, right); // 这样才可以
            left++;
            right--; // 缩小范围
        }
    }

    /**
     * 双指针-对撞指针for写法
     *
     * @param array
     */
    public static void selection3(int[] array) {
        int n = array.length;
        for (int left = 0, right = n - 1; left < right; left++, right--) {
            int minIndex = left;
            int maxIndex = right;
            for (int i = left; i <= right; i++) {
                if (array[i] < array[minIndex]) minIndex = i;
                if (array[i] > array[maxIndex]) maxIndex = i;
            }
            Sorter.swap(array, minIndex, left); // 先交换一下最小的
            if (maxIndex == left) maxIndex = minIndex; // 必须重新赋值
            Sorter.swap(array, maxIndex, right); // 这样才可以
        }
    }
}
