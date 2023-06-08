package com.chi.sort;

import java.util.ArrayList;

import static com.chi.sort.Sorter.printArray;
import static com.chi.sort.Sorter.swap;

/**
 * 希尔排序
 */
public class _04_Shell {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 155, 2, 7, 3, 1, 6, 4, 2, 4, 4, 55, 8, 7, 2, -8};
        System.out.println("排序前的数组：");
        printArray(array);
        // shellsort1(array); // 替换
        // shellsort2(array); // 赋值
        // shellsort3(array); // 取数组的一半，网上烂大街的写法

        System.out.println("排序后的数组：");
        printArray(array);
    }


    private static void shellsortPractise(int[] array) {
        int len = array.length;
        int gap = 1;
        while (gap < len / 3) gap = 3 * gap + 1;
        while (gap > 1) {
            for (int i = gap; i < len; i++) {
                for (int j = i; j >= gap; j -= gap) {
                    if (array[j] > array[j - gap]) {
                        swap(array, j, j - gap);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    /**
     * 结合步长和插入排序的用法 -交换
     *
     * @param array
     */
    public static void shellsort1(int[] array) {
        // 获取步长数组
        ArrayList<Integer> gapSequence = getGap(array.length);
        // 为什么取0，是因为一直要遍历完最后一个元素 而且是从数字大到小
        for (int index = gapSequence.size() - 1; index >= 0; index--) {
            int gap = gapSequence.get(index);
            for (int i = gap; i < array.length; i++) {
                for (int j = i; j >= gap; j = j - gap) {
                    if (array[j] < array[j - gap]) {
                        swap(array, j, j - gap);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    /**
     * 结合步长和插入排序的用法 -赋值
     *
     * @param array
     */
    public static void shellsort2(int[] array) {
        ArrayList<Integer> gapSequence = getGap(array.length);
        // 为什么取0，是因为一直要遍历完最后一个元素 而且是从数字大到小
        for (int index = gapSequence.size() - 1; index >= 0; index--) {
            int gap = gapSequence.get(index);
            for (int i = gap; i < array.length; i++) {
                // 手牌取一下 暂存现在的值
                int temp = array[i];
                int j = i; // 新建指针
                // 1.只要j>=gap 并且 手牌更小 3.然后再跟步长千的那个值进行交换
                for (; j >= gap && array[j - gap] > temp; j -= gap) {
                    // 2.那么就要赋值
                    array[j] = array[j - gap];
                }
                // 此时temp所在的位置就找到了
                array[j] = temp;
            }
        }
    }

    /**
     * 网上烂大街的写法，每一次步长就取值数组长度一般
     */
    public static void shellsort3(int[] array) {
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // 到这里求到了gap的，然后就是插入排序的常规操作
            for (int i = gap; i < n; i++) {
                // 取到了gap手牌
                int temp = array[i];
                int j;
                // 定义了j，并且=i，对比向前走
                for (j = i; j >= gap && array[j - gap] > temp; j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }
    }

    /**
     * 如果步长不是1，而是gap的话是这样写的
     *
     * @param array
     */
    public static void shellsortGap(int[] array, int gap) {
        // 这里是写的步长为1，但是步长如果为gap呢？
        for (int i = gap; i < array.length; i += gap) {
            // 为什么要j>=gap而不是j>0 因为你是从gap开始的，j>0 的时候你下面-gap对比 就越界了
            // 为什么不是j>gap 因为当j=gap的时候 j-gap是等于0的 是可以取到的
            for (int j = i; j >= gap; j -= gap) {
                if (array[j] < array[j - gap]) {
                    swap(array, j, j - gap);
                } else {
                    break;
                }
            }
        }
    }

    // 求步长的算法
    public static ArrayList<Integer> getGap(int len) {
        ArrayList<Integer> list = new ArrayList<>();
        int k = 1;
        int h;
        // 最小的步长是1
        while (true) {
            h = ((int) Math.pow(3, k) - 1) / 2;
            // 如果已经大于数组的长度了 直接break
            if (h > len) break;
            list.add(h);
            k++;
        }
        System.out.println(list);
        return list;
    }
}
