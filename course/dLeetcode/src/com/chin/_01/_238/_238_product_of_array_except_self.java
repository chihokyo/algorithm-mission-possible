package com.chin._01._238;

public class _238_product_of_array_except_self {
    // 暴力解法 超时
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            // 计算左边
            int leftP = 1;
            for (int j = 0; j < i; j++) {
                leftP *= nums[j];
            }
            // 计算右边
            int rightP = 1;
            for (int j = i + 1; j < n; j++) {
                rightP *= nums[j];
            }
            // 左右相乘
            res[i] = leftP * rightP;
        }
        return res;
    }

    // DP 空间复杂度n
    public int[] productExceptSelf1(int[] nums) {
        int n = nums.length;
        int[] leftP = new int[n];
        leftP[0] = 1;
        for (int i = 1; i < n; i++) {
            leftP[i] = leftP[i - 1] * nums[i - 1];
        }
        int[] rightP = new int[n];
        rightP[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            rightP[i] = rightP[i + 1] * nums[i + 1];
        }

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = leftP[i] * rightP[i];
        }
        return res;
    }

    // DP 空间复杂度1
    public int[] productExceptSelf2(int[] nums) {
        int n = nums.length;
        // 先将每个元素的左边乘积放在res里
        int[] res = new int[n];
        res[0] = 1;
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        // 每个元素的右边所有元素的乘积存储在一个变量中
        int rightP = 1;
        for (int i = n - 1; i >= 0; i--) {
            // 对于索引 i，左边的乘积为 output[i]，右边的乘积为 rightP
            res[i] = res[i] * rightP;
            // 更新右边乘积
            rightP = rightP * nums[i];
        }

        return res;
    }
}
