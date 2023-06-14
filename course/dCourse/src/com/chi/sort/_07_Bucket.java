package com.chi.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.chi.sort.Sorter.printArray;

/**
 * 桶排序
 */
public class _07_Bucket {
    public static void main(String[] args) {
        int[] array = {38, 27, 43, 3, 9, 82, 10, -5, 555};

        System.out.println("排序前的数组：");
        printArray(array);

        bucketsort(array);

        System.out.println("排序后的数组：");
        printArray(array);
    }

    /**
     * 思路其实很清晰 只要按照大方向走
     *
     * @param array
     */
    public static void bucketsort(int[] array) {
        // 1. 先找到最大值和最小值
        int maxValue = array[0];
        int minValue = array[0];
        for (int i = 0; i < array.length; i++) {
            maxValue = Math.max(maxValue, array[i]);
            minValue = Math.min(minValue, array[i]);
        }
        // 2.计算桶的数量 & 初始化桶
        // 这个求数量理解起来也很难
        int bucketCount = (maxValue - minValue) / array.length + 1;
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // 3.分配每个元素到桶里
        for (int num : array) {
            // 求出放在那个index 这个算法死记硬背吧 这个理解起来难
            int bucketIndex = (num - minValue) / array.length;
            buckets.get(bucketIndex).add(num);
        }
        // 4. 对每个桶进行排序
        int cur = 0;
        // 先取出来每个桶
        for (List<Integer> bucket :
                buckets) {
            // 排序每个桶
            Collections.sort(bucket);
            for (int num : bucket) {
                // 最后输出到原数组
                array[cur++] = num;
            }
        }
    }


}
