package com.chi.line.twopointer;

public class _283_Movezeroes {

    // 暴力解法
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int[] temp = new int[nums.length];
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                temp[j] = nums[i];
                j++;
            }
        }
    }

    // 原地算法 时间复杂度O(n) 空间复杂度(1)
    public void moveZeroes2(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                // 这里主要是将指向i和j的元素进行交换
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                j++; // 注意一定不能忘记这里需要++
            }
        }
        return;
    }

    // 稍微进行优化
    public void moveZeroes3(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                // 不相等才交换
                if (nums[fast] != nums[slow]) {
                    // 这里主要是将指向i和j的元素进行交换
                    int temp = nums[fast];
                    nums[fast] = nums[slow];
                    nums[slow] = temp;
                }
                slow++; // 注意一定不能忘记这里需要++
            }
        }
        return;
    }

    // 直接赋值不交换
    public void moveZeroes4(int[] nums) {
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                // 这里直接赋值
                nums[slow] = nums[fast];
                slow++;
            }
        }
        // 剩余的记得归零
        for (; slow < nums.length; slow++) {
            nums[slow] = 0;
        }
    }
}


