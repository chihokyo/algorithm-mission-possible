package com.chin._08._1539;

public class _1539_kth_missing_positive_number {
    // 遍历
    public int findKthPositive1(int[] arr, int k) {
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            if (arr[i] - i - 1 >= k) {
                return k + i;
            }
        }
        return k + len;
    }

    // 直接模拟
    // 时间复杂度 ： O(n + k)
    public int findKthPositive2(int[] arr, int k) {
        int missConut = 0; // 记录缺失次数
        int curNum = 1; // 记录当前走到哪个值
        int lastMissNum = -1; // 记录上一值

        int i = 0; // 计数1，2，3...
        // 结束条件 当缺失次数 < k 因为当等于k的时候可以直接跳出
        while (missConut < k) {
            // 说明没缺
            if (arr[i] == curNum) {
                // 这里必须要计算下长度，因为当i大于数组长度的时候 说明后面全都是缺失的
                // 后面既然全部都是缺失的，那么就直接计算成缺失就行，就不要走这里了
                i = (i + 1 < arr.length) ? i + 1 : i;
            } else {
                // 说明缺了
                missConut++;
                // 缺失的值 就是当前的值了
                lastMissNum = curNum;
            }
            // 无论有无缺失 每一次都要向前走
            curNum++;
        }
        // 最后跳出的时候 missCount=k 此时就是答案
        return lastMissNum;
    }

    // 二分法
    public int findKthPositive3(int[] arr, int k) {
        // 第一个就大于kk 说明k就是第k个。
        // 比如[5,6,7,8,9] k是4，那么答案也是4 → 1,2,3,4
        if (arr[0] > k) return k;
        int left = 0, right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 差<=k 说明在右边
            if (arr[mid] - mid - 1 < k) {
                left = mid + 1;
            } else {
                // 差>k 说明在左边
                right = mid;
            }
        }
        // 走到这里只剩下一个了
        // 这里要找左边的邻居arr[left - 1]，而不能找 arr[left]
        // 不懂建议画图，这题就这么来的
        int leftMissCount = arr[left - 1] - (left - 1) - 1;
        return arr[left - 1] + (k - leftMissCount);
    }
}
