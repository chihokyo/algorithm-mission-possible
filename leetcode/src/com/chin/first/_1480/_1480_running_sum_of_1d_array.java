package com.chin.first._1480;

public class _1480_running_sum_of_1d_array {
    // 暴力解法 时间复杂度O(n方)
    public int[] runningSum(int[] nums) {
        int n = nums.length;
        int[] prefixSum = new int[n];
        // 固定i
        for (int i = 0; i < n; i++) {
            int sum = 0;
            // 固定j，计算j到i所有的和赋值给prefixSum[i]
            for (int j = 0; j <= i; j++) {
                sum += nums[j];
            }
            // 赋值给prefixSum[i]
            prefixSum[i] = sum;
        }
        return prefixSum;
    }

    // 动态规划 时间复杂度O(n)
    public int[] runningSum1(int[] nums) {
        int n = nums.length;
        int[] prefixSum = new int[n];
        prefixSum[0] = nums[0];
        // 为什么要从1开始
        // 其实是因为prefixSum[i - 1] + nums[i]的时候i为0的情况
        for (int i = 1; i < n; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i];
        }
        return prefixSum;
    }
}
