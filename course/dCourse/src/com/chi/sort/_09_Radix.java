package com.chi.sort;

import java.util.Arrays;

import static com.chi.sort.Sorter.printArray;

/**
 * 基数排序
 * 本质就是 按照个位十位+基数排序
 */
public class _09_Radix {
    public static void main(String[] args) {
        // int[] array = {4532, 2532, 1836, 7824, 6543, 7891, 8888};
        int[] array = {-12, 23, -4, 6, 78, -90, 45, -31, 17, 8};

        System.out.println("排序前的数组：");
        printArray(array);

        // radixsort1(array); // 思路1
        radixsort2(array); // 思路2 处理负数

        // radixsortPrac(array); // 练手

        System.out.println("排序后的数组：");
        printArray(array);
    }


    /**
     * 基数排序
     *
     * @param array
     */
    private static void radixsort1(int[] array) {
        // 找到最大值 因为你只有找到一个数组的最大值 你才能确定用什么来分
        int maxValue = Arrays.stream(array).max().getAsInt();
        // 比如最大值是88 那么到十分位88/10 是8，那么到百分位88/100 是0，
        for (int exp = 1; maxValue / exp > 0; exp *= 10) {
            int[] counArr = new int[10];
            for (int i = 0; i < array.length; i++) {
                // 拿到个位 进行排序
                int digit = (array[i] / exp) % 10;
                counArr[digit]++;
            }
            for (int i = 1; i < 10; i++) {
                counArr[i] += counArr[i - 1];
            }
            int[] temp = new int[array.length];
            for (int i = array.length - 1; i >= 0; i--) {
                counArr[(array[i] / exp) % 10]--; // 先找到位置 然后--
                temp[counArr[(array[i] / exp) % 10]] = array[i];
            }

            for (int i = 0; i < temp.length; i++) {
                array[i] = temp[i];
            }
        }
    }

    /**
     * 基数排序-如果有负数的情况下 需要多加考虑
     */
    private static void radixsort2(int[] array) {
        // 找到最小值，如果有负数，加上其绝对值将所有数变为非负
        int minValue = Arrays.stream(array).min().getAsInt();
        // 只要小
        if (minValue < 0) {
            for (int i = 0; i < array.length; i++) {
                // 就加上绝对值
                array[i] -= minValue;
            }
        }

        // 这部分是基数排序的常规操作
        int maxValue = Arrays.stream(array).max().getAsInt();
        for (int exp = 1; maxValue / exp > 0; exp *= 10) {
            int[] counArr = new int[10];
            for (int i = 0; i < array.length; i++) {
                int digit = (array[i] / exp) % 10;
                counArr[digit]++;
            }
            for (int i = 1; i < 10; i++) {
                counArr[i] += counArr[i - 1];
            }
            int[] temp = new int[array.length];
            for (int i = array.length - 1; i >= 0; i--) {
                counArr[(array[i] / exp) % 10]--;
                temp[counArr[(array[i] / exp) % 10]] = array[i];
            }
            for (int i = 0; i < temp.length; i++) {
                array[i] = temp[i];
            }
        }

        // 如果原先有负数，这里将所有数减去绝对值，变回原先的数值
        if (minValue < 0) {
            for (int i = 0; i < array.length; i++) {
                // 最后要记得转换成原来的数值
                array[i] += minValue;
            }
        }
    }
}
