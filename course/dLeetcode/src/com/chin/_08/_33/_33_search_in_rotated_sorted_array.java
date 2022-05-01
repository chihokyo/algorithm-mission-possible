package com.chin._08._33;

public class _33_search_in_rotated_sorted_array {

    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 找到了 直接就可以返回
            if (target == nums[mid]) return mid;
            // 这样说明左边肯定是有序的，left mid 范围内肯定有序
            if (nums[left] <= nums[mid]) {
                // 左边有序的话，就要看看target在不在[left,mid]之间
                // 等于nums[mid]就不用判断了，因为已经判断了
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // 右边，mid right 范围内肯定有序
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}
