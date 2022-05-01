package com.chin._02._163;

import java.util.ArrayList;
import java.util.List;

public class _163_missing_ranges {
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> res = new ArrayList<>();
        long pre = lower - 1; // 为了防止溢出 用的long
        for (int i = 0; i < nums.length; i++) {
            // 当前的值
            // nums[i] - pre 为了防止溢出 换成+
            if (nums[i] == pre + 2) {
                // 有缺失区间 正好第一位
                res.add(String.valueOf(pre + 1));
            } else if (nums[i] > pre + 2) {
                // 有缺失区间 直接拼接 （当前的值 - 1）
                res.add((pre + 1) + "->" + (nums[i] - 1));
            }
            pre = nums[i]; // 这里要注意 必须要进行转换到前一个值
        }

        // 走到这里应该就要走到最后了 判断最后一个值
        // 如果给的右闭区间 等于 最后一个值 + 1 那么久缺少这个值
        if (upper == pre + 1) res.add(String.valueOf(pre + 1));
            // 否则就是 + 1 到 右闭区间
        else if (upper > pre + 1) {
            res.add((pre + 1) + "->" + upper);
        }
        return res;
    }
}
