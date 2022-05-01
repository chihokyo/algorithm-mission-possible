package com.chin._08._154;

// 建议配合153一起使用
public class _154_find_minimum_in_rotated_sorted_array_ii {
    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                // 多了这一种情况来判断重复，就是一步步向前比较，而不是直接移动到right
                right--;
            }
        }
        return nums[left];
    }
}
