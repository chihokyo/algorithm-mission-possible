package com.chin._02._189;

public class _189_rotate_array {
    // 方法1：额外数组
    public void rotate(int[] nums, int k) {
        int len = nums.length;
        // 新建临时数组
        int[] newArr = new int[len];
        for (int i = 0; i < len; i++) {
            // 求index 取模就可以完成翻转
            int index = (i + k) % len;
            // 临时[index] = 数组[i]
            newArr[index] = nums[i];
        }
        // 这里题目要求结果是原数组，所以还要完全复制到原数组
        System.arraycopy(newArr, 0, nums, 0, len);
    }

    // 方法2：环状替换
    public void rotate2(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        int gcdNum = gcd(n, k);
        for (int start = 0; start < gcdNum; start++) {
            int cur = start;
            int pre = nums[start];
            // 循环替换
            do {
                int next = (cur + k) % n;
                int tmp = nums[next];
                nums[next] = pre;
                pre = tmp;
                cur = next;
            } while (start != cur);
        }
    }

    // 求x和y的最大公约数
    private int gcd(int x, int y) {
        return y > 0 ? gcd(y, x % y) : x;
    }

    // 方法3：翻转数组
    public void rotate3(int[] nums, int k) {
        int n = nums.length;
        k %= n; // k = k % n;
        reserve(nums, 0, n - 1);
        reserve(nums, 0, k - 1);
        reserve(nums, k, n - 1);
    }

    private void reserve(int[] nums, int start, int end) {
        int n = nums.length;
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}
