package com.chin._08._852;

public class _852_peak_index_in_a_mountain_array {

    /**
     * 暴力解法
     * 时间复杂度O(n)
     *
     * @param arr 数组
     * @return int 峰顶
     */
    public int peakIndexInMountainArray(int[] arr) {
        // 遍历起来
        for (int i = 0; i < arr.length - 1; i++) {
            // 一直向上，前一个大于后一个，终止
            if (arr[i] > arr[i + 1]) return i;
        }
        // 一直向上的话，那么峰顶就是最后一个index
        return arr.length - 1;
    }

/**
 * 二分查找
 *
 * @param arr 数组
 * @return int 峰顶
 */
public int peakIndexInMountainArray2(int[] arr) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        int mid = left + (right - left) / 2;
        // 中间值 < 中间值+1 说明在走上坡路 左边肯定都不是 排除
        if (arr[mid] < arr[mid + 1]) {
            left = mid + 1;
        } else {
            // 已经在走下坡路，自身可能是峰顶
            right = mid;
        }
    }
    // 最后只剩下一个，那肯定是的 right也对
    return left;
}
}
