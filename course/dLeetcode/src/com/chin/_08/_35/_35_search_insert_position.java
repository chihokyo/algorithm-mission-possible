package com.chin._08._35;

// 这一题的本质是找到第一个大于等于target的元素index
public class _35_search_insert_position {
    // 思路1 在循环内要查找目标值
    public int searchInsert1(int[] nums, int target) {
        // 特殊情况考虑 1为空 2没长度 3比数组最后1个值还大 说明在最后 也就是长度
        // 但是lc上面说明了长度最少为1，肯定不可能出错
        if (nums == null) return -1; // lc可省略
        if (nums.length == 0) return 0; // lc可省略
        if (target > nums[nums.length - 1]) return nums.length;

        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target <= nums[mid]) {
                // 小于等于 说明可能是答案，但是还要看前一个是不是小
                // 小才是答案
                // mid - 1 有可能过界 所以考虑一下
                if (mid == 0 || nums[mid - 1] < target) return mid;
                    // 如果前面还要大，那就继续缩小右边
                else right = mid - 1;
            } else {
                // 这里说明 target>nums[mid] 肯定
                left = mid + 1;
            }
        }
        // 没有直接返回负一
        return -1;
    }

    // 思路2 不断缩小循环体
    public int searchInsert2(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // mid的值更大 缩小右边
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                // mid的值更小 缩小左边
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }
}
