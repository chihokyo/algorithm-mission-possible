package com.chi.sort;

import java.util.Arrays;

import static com.chi.sort.Sorter.printArray;

/**
 * 计数排序
 */
public class _08_Counting {
    public static void main(String[] args) {
        int[] array = {-4, 4, 3, 2, 1, 6, 5, 9, 8, 7, 10};

        System.out.println("排序前的数组：");
        printArray(array);

        // countsort1(array); // 思路1
        // countsort2(array); // 思路2 考虑负数
        countsort3(array); // 思路3 非稳定的

        // countsortPrac(array); // 练手

        System.out.println("排序后的数组：");
        printArray(array);
    }

    // 练手用
    private static void countsortPrac(int[] array) {
        int maxV = array[0];
        int minV = array[0];
        for (int i = 0; i < array.length; i++) {
            maxV = Math.max(maxV, array[i]);
            minV = Math.min(minV, array[i]);
        }
        int[] countArr = new int[maxV - minV + 1];
        for (int i = 0; i < array.length; i++) {
            countArr[array[i] - minV]++;
        }
        for (int i = 1; i < countArr.length; i++) {
            countArr[i] += countArr[i - 1];
        }
        int[] temArr = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            countArr[array[i] - minV]--;
            temArr[countArr[array[i] - minV]] = array[i];
        }
        for (int i = 0; i < temArr.length; i++) {
            array[i] = temArr[i];
        }
    }

    /**
     * 计数排序
     *
     * @param array
     */
    private static void countsort1(int[] array) {
        // 1. 找到最大值
        int maxValue = array[0];
        int[] countArr;
        for (int i = 0; i < array.length; i++) {
            maxValue = Math.max(maxValue, array[i]);
        }
        // 2. 新建计数数组
        // 找 到最大的那个 新建一个最大的数组 计数数组
        countArr = new int[maxValue + 1];
        for (int i = 0; i < array.length; i++) {
            countArr[array[i]]++;
        }
        // 走到这里应该都应该计数完毕了
        // 3.开始累加
        for (int i = 1; i < countArr.length; i++) {
            countArr[i] = countArr[i] + countArr[i - 1];
        }
        // 累加也结束了
        // 4.开始找对位置 进行赋值
        int[] tempArr = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            // 这里很重要！array[i] 找到值
            // countArr[array[i]] 找到索引
            // 先--
            countArr[array[i]]--;
            // 然后把array[i]放到正确的位置
            tempArr[countArr[array[i]]] = array[i];
        }
        // 5. 走到这里tempArr已经是正确位置的数组了 然后复制
        for (int i = 0; i < tempArr.length; i++) {
            array[i] = tempArr[i];
        }
    }

    /**
     * 考虑负数 其实上面的也就是把最小值当成0来算而已
     *
     * @param array
     */
    private static void countsort2(int[] array) {
        // 1. 找到最大值和最小值
        int maxValue = array[0];
        int minValue = array[0];
        for (int i = 0; i < array.length; i++) {
            maxValue = Math.max(maxValue, array[i]);
            minValue = Math.min(minValue, array[i]);
        }
        // 2. 新建计数数组
        int[] countArr = new int[maxValue - minValue + 1];
        // 3.开始计数
        for (int i = 0; i < array.length; i++) {
            // 记住这里要计算和min的差距才行
            countArr[array[i] - minValue]++;
        }
        // 4. 开始累加
        for (int i = 1; i < countArr.length; i++) {
            countArr[i] = countArr[i] + countArr[i - 1];
        }
        // 5.新建临时数组放位置
        int[] tempArr = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            // 从原数组找到计数数组需要计算偏移量 然后--
            countArr[array[i] - minValue]--;
            // 同样 这里也需要计算偏移量 只要到计数数组 都要计算偏移量
            tempArr[countArr[array[i] - minValue]] = array[i];

        }
        // 6. 走到这里tempArr已经是正确位置的数组了 然后复制
        for (int i = 0; i < tempArr.length; i++) {
            array[i] = tempArr[i];
        }
    }

    /**
     * 非稳定版本的
     *
     * @param array
     */
    private static void countsort3(int[] array) {
        int max = Arrays.stream(array).max().getAsInt();
        int min = Arrays.stream(array).min().getAsInt();

        // 计算计数数组的大小，注意因为可能存在负数，需要加上Math.abs(min)进行偏移
        int[] countArr = new int[max - min + 1];

        // 计数阶段
        for (int num : array) {
            countArr[num - min]++;
        }

        // 排序阶段
        int index = 0;
        for (int i = 0; i < countArr.length; i++) {
            // 这里相当于直接从count取值
            while (countArr[i] > 0) {
                array[index++] = i + min;
                countArr[i]--;
            }
        }
    }
}
