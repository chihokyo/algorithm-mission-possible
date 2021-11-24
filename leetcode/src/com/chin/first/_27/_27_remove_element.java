package com.chin.first._27;

public class _27_remove_element {
    // 对撞指针
    public int removeElement(int[] nums, int val) {
        if (nums.length == 0) return 0;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            if (nums[left] == val) {
                nums[left] = nums[right];
                right--;
            } else {
                // 不为val的时候向前走
                left++;
            }
        }
        return right + 1; // left also OK
    }

    // 快慢指针 while
    public int removeElement1(int[] nums, int val) {
        if (nums.length == 0) return 0;
        int slow = 0;
        int fast = 0;
        while (fast < nums.length) {
            // 不等就赋值，保证slow所在的位置一直是不等的区域的下一位
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
            // 每次判断完记得向前走
            fast++;
        }
        return slow;
    }

    // 快慢指针 for
    public int removeElement2(int[] nums, int val) {
        if (nums.length == 0) return 0;
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                nums[slow++] = nums[fast];
            }
        }
        return slow;
    }

}
