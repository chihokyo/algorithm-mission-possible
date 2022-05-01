package com.chin._02._665;

public class _665_non_decreasing_array {
    // 边界 int i = 1; 从1 开始
    public boolean checkPossibility(int[] nums) {
        int count = 0;
        // 从第二个开始判断
        for (int i = 1; i < nums.length; i++) {
            // 如果前面的小于后面的[4,2,6] 这个时候就需要修改了
            if (nums[i] < nums[i - 1]) {
                // 修改
                count++;
                // 如果修改次数超1，直接false
                if (count > 1) return false;
                // 那么到底修改哪一个呢？这就要判断了
                // 如果小于前面的前面的元素的话 那么nums[i] < nums[i - 2]
                // nums[i-1]要大于nums[i-2] 因为要一直保持前面是非递减区域
                // [452] 就是 [455]
                //  (i - 2 >= 0 越界条件要优先考虑
                if (i - 2 >= 0 && nums[i] < nums[i - 2]) {
                    nums[i] = nums[i - 1];
                } else {
                    // [427] 就是 [477] 因为此时nums[i-2]肯定大于等于nums[i]
                    nums[i - 1] = nums[i];
                }
            }
        }
        // 全部循环没有false，就是t
        return true;
    }

    // 边界 int i = 0; 从0 开始
    public boolean checkPossibility2(int[] nums) {
        int n = nums.length;
        int count = 0;
        for (int i = 0; i + 1 < nums.length; i++) {
            if (nums[i + 1] < nums[i]) {
                count++;
                if (count > 1) return false;
                //453 → 455
                if (i - 1 >= 0 && nums[i + 1] < nums[i - 1]) {
                    nums[i + 1] = nums[i];
                } else {
                    // 426 → 466
                    nums[i] = nums[i + 1];
                }
            }
        }
        return true;
    }
}
