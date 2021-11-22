package com.chin.first._283;

public class _283_move_zeroes {
    // 方法1 交换方法
    public void moveZeroes(int[] nums) {
        if (nums.length == 0) return;
        int slow = 0;
        int fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != 0) {
                // 考虑了一下没有0的情况
                if (slow != fast) {
                    int temp = nums[fast];
                    nums[fast] = nums[slow];
                    nums[slow] = temp;
                }
                slow++;
            }
            fast++;
        }
    }

    // 方法2 赋值方法（更快）
    public void moveZeroes2(int[] nums) {
        if (nums.length == 0) return;
        int slow = 0;
        // 这里while↑和for完全可以转换写
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                // 这里没有考虑 if (slow != fast)  赋值语句 快于 比较语句
                nums[slow] = nums[fast];
                slow++;
            }
        }
        for (int i = slow; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
