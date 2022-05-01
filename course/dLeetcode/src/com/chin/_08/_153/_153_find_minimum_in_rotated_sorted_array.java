package com.chin._08._153;

public class _153_find_minimum_in_rotated_sorted_array {
    /**
     * 暴力解法 遍历数组 找到最小值
     * 时间复杂度 O(n)
     * 空间复杂度 O(1)
     *
     * @param nums 数组
     * @return int 最小值
     */
    public int findMin(int[] nums) {
        int minVal = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // 找到小的就交换
            minVal = Math.min(minVal, nums[i]);
        }
        return minVal;
    }

    /**
     * 暴力解法 遍历数组 提前终止，只要找到前一位小的就是最小
     * 时间复杂度 O(n)
     * 空间复杂度 O(1)
     *
     * @param nums 数组
     * @return int 最小值
     */
    public int findMin2(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            // 因为按照厂里后一个应该比前一个大
            // 如果后一个比前一个小了，说明是拐点
            if (nums[i] < nums[i - 1]) {
                return nums[i];
            }
        }
        // 一直都是大的，说明最小的就是第一个
        return nums[0];
    }

    /**
     * 二分查找
     * 时间复杂度是： O(logn)
     * 空间复杂度是：O(1)
     *
     * @param nums 数组
     * @return int 最小值
     */
    public int findMin3(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 本来升序，那么mid对应的值，肯定是大于right
            // 小于的话，说明这里出现了一个旋转拐点
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                // 说明答案有可能是mid和mid之前的
                right = mid;
            }
        }
        // 只剩下一个最后一个，肯定就是最小的了
        return nums[left];
    }


}
