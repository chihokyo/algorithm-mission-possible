package com.chin._08._704;

public class _704_binary_search {
    // 第1种思路，表示的是不断在某个区间内查找元素
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        int left = 0, right = nums.length - 1;
        // left 等于 right的意思 相当于只有1个，或者只剩1个的情况
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                // 目标值更小
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    // 第2种思路，在循环体内排除一定不存在目标元素的区间
    // 不用判断某个值是否等于mid 而是排除区间
    // 如果大于mid 说明左边的空间全部是可以删除掉的
    public int search2(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        int left = 0, right = nums.length - 1;
        // 这里注意是没有==的情况的，因为==的情况证明left和right相遇
        // 证明只有1个，只有1个的时候是需要我们自己来判断大小的
        // 只有1个的时候是应该结束循环的
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 目标值更大 说明在右边那个区间进行查找
            if (target > nums[mid]) {
                left = mid + 1;
            } else {
                // 因为这里的情况是<= 说明有可能是本身
                // 这里是不能取值到+1的
                right = mid;
            }
        }
        // 走到这里说明left==right了，一定是这样的
        // 并且这里只剩下一个元素了没有处理了，这个答案是对的话就是不是就false
        if (nums[left] == target) return left; // 这里写right也可以
        return -1;
    }

    // 第2种思路的变异版本
    public int search3(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        int left = 0, right = nums.length - 1;
        while (left < right) {
            // 注意这里 因为会有向上取整的问题
            int mid = left + (right - left + 1) / 2;
            // 改成了小于
            if (target < nums[mid]) {
                // 这里也是
                right = mid - 1;
            } else {
                // 这里也是
                left = mid;
            }
        }
        if (nums[left] == target) return left;
        return -1;
    }
}
