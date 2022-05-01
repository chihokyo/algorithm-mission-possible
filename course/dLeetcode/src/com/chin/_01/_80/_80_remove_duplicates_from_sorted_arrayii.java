package com.chin._01._80;

public class _80_remove_duplicates_from_sorted_arrayii {
    // while写法
    public int removeDuplicates(int[] nums) {
        if (nums.length == 2) return 2;
        int slow = 2;
        int fast = 2;
        while (fast < nums.length) {
            // 检查上上个应该被保留的元素nums[slow−2]是否和当前待检查元素nums[fast]相同
            if (nums[fast] != nums[slow - 2]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }

    // for写法
    public int removeDuplicates1(int[] nums) {
        if (nums.length == 2) return 2;
        int slow = 2;
        for (int fast = 2; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow - 2]) {
                nums[slow++] = nums[fast];
            }
        }
        return slow;
    }
}
