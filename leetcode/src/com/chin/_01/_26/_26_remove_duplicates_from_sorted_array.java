package com.chin._01._26;

public class _26_remove_duplicates_from_sorted_array {
    // while写法
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int slow = 0;
        int fast = 1; // 第2个开始
        while (fast < nums.length) {
            if (nums[fast] != nums[slow]) {
                // 注意要先移动，在交换
                // 因为slow没＋之前，指向的是最后一个未重复的
                // 直接这样赋值的话，就会抹掉最后了
                slow++;
                nums[slow] = nums[fast];
            }
            fast++;
        }
        // slow记录的是符合区域的最后一个值，而不是长度
        return slow + 1;
    }

    // for写法
    public int removeDuplicates1(int[] nums) {
        if (nums.length == 0) return 0;
        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) {
                // 先赋值 后slow++
                nums[++slow] = nums[fast];
            }
        }
        return slow + 1;
    }
}
