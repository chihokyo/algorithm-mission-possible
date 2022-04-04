package com.chin._08._34;

public class _34_find_first_and_last_position_of_element_in_sorted_array {
    // 第1种复杂度是n的难度
    public int[] searchRange(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            while (left < right && nums[left] < target) {
                left++;
            }
            while (left < right && nums[right] > target) {
                right--;
            }
            // 相等直接返回
            if (nums[left] == target && nums[right] == target) {
                return new int[]{left, right};
            } else {
                // 不等的话证明无解，直接跳出
                break;
            }
        }

        return new int[]{-1, -1};
    }

    // 第2种是把难度拆分成2个小题目
    // 符合条件的第1个目标值的索引
    // 符合条件的最后1个目标值的索引
    public int[] searchRange2(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new int[]{-1, -1};
        // 找到第1个
        int firstTargetIndex = searchFirstTarget(nums, target);
        // 说明没找到
        if (firstTargetIndex == -1) return new int[]{-1, -1};

        // 找到最后1个
        int lastTargetIndex = searchLastTarget(nums, target);
        // 拼接在一起
        return new int[]{firstTargetIndex, lastTargetIndex};
    }

    private int searchFirstTarget(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target == nums[mid]) {
                // 如果为第1个元素 or 如果前面一个直接小
                if (mid == 0 || nums[mid - 1] < target) return mid;
                    // 如果前面一个还是等于 说明right还要缩小
                else right = mid - 1;
            } else if (target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    private int searchLastTarget(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target == nums[mid]) {
                // 如果已经为最后1个元素 or 如果后面一个更大
                if (mid == nums.length - 1 || nums[mid + 1] > target) return mid;
                    // 如果前面一个还是等于 说明right还要缩小
                else left = mid + 1;
            } else if (target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    // 第3种 用的是排除法
    public int[] searchRange3(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new int[]{-1, -1};
        // 找到第1个
        int firstTargetIndex = searchFirstTarget2(nums, target);
        // 说明没找到
        if (firstTargetIndex == -1) return new int[]{-1, -1};

        // 找到最后1个
        int lastTargetIndex = searchLastTarget2(nums, target);
        // 拼接在一起
        return new int[]{firstTargetIndex, lastTargetIndex};
    }

    private int searchFirstTarget2(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 说明左边的都要被抛弃
            if (target > nums[mid]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        if (nums[left] == target) return left;
        return -1;
    }

    private int searchLastTarget2(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            // 说明右边的都要被抛弃
            if (target < nums[mid]) {
                right = mid - 1;
            } else {
                // 说明右边都要被抛弃
                left = mid;
            }
        }
        if (nums[left] == target) return left;
        return -1;
    }
}
