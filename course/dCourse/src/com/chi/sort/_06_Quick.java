package com.chi.sort;

import static com.chi.sort.Sorter.printArray;

/**
 * 快速排序
 */
public class _06_Quick {
    public static void main(String[] args) {
        int[] array = {38, 27, 43, 3, 9, 82, 10, -5, 555};

        System.out.println("排序前的数组：");
        printArray(array);

        // quicksort1(array, 0, array.length - 1); // 思路1
        quicksort2(array, 0, array.length - 1); // 思路2 三路排序
        // quicksortPractise(array, 0, array.length - 1);//


        System.out.println("排序后的数组：");
        printArray(array);
    }

    private static void quicksortPractise(int[] array, int low, int high) {
        if (low >= high) return;
        int j = partitionPractise(array, low, high);
        quicksortPractise(array, low, j - 1);
        quicksortPractise(array, j + 1, high);
    }

    private static int partitionPractise(int[] array, int low, int high) {
        int great = low;
        int less = low;
        int pivot = array[high];
        for (; great <= high - 1; great++) {
            if (array[great] <= pivot) {
                Sorter.swap(array, great, less);
                less++;
            }
        }
        // 走到最后了
        Sorter.swap(array, less, high);
        return less;

    }

    /**
     * 思路1 最传统做法 递归
     */
    private static void quicksort1(int[] array, int low, int high) {
        // 这个最小子问题就是数组前面没有了 或者只有自身一个了
        // [1,6,5,8] 这种情况下1在0的位置 low:0 ,j:0-1=-1,这样的话quicksort1(array,0,-1) 说明1左边没了
        // 可以看图
        if (low >= high) return;
        // 拿到基准点所在的index 左边都是小于的 右边都是大于的
        int j = partition(array, low, high);
        quicksort1(array, low, j - 1); // 记住这里要-1，因为j代表pivot的index
        quicksort1(array, j + 1, high);
    }

    // 划分
    private static int partition(int[] array, int low, int high) {

        int pivot = array[high]; // 初始化
        int great = low; // 初始化指针用来遍历
        int less = low; // 初始化用来指向比pivot小的元素的下一个index

        for (; great <= high - 1; great++) {
            if (array[great] < pivot) {
                Sorter.swap(array, great, less);
                less++; // 这一步千万不要忘记 交换之后less要向前走
            }
        }
        // 走到最后要交换
        Sorter.swap(array, high, less);
        // 此时就找到了less
        return less;
    }

    /**
     * 思路2 三路快排
     */
    private static void quicksort2(int[] array, int low, int high) {
        if (low >= high) return;
        // 这里开始是三路快排的过程
        int pivot = array[high];
        int less = low, great = high;
        int i = low;
        // 因为i>great结束了
        while (i <= great) {
            if (array[i] < pivot) {
                Sorter.swap(array, i, less);
                less++;
                i++;
            } else if (array[i] > pivot) {
                Sorter.swap(array, i, great);
                great--;
                i++;
            } else {
                i++;
            }
        }
        quicksort2(array, low, less - 1);
        quicksort2(array, great + 1, high);
    }
}
