package com.chin._01._448;

import java.util.ArrayList;
import java.util.List;

public class _448_find_all_numbers_disappeared_in_an_array {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        int len = nums.length;
        // 一轮遍历开始加倍
        for (int i = 0; i < len; i++) {
            int index = (nums[i] - 1) % len;
            nums[index] += len;
        }
        // 初始化结果集
        List<Integer> res = new ArrayList<>();
        // 开始第二轮遍历
        for (int i = 0; i < len; i++) {
            // 在1和len之间的都是没加倍，说明没出现过
            if (nums[i] >= 1 && nums[i] <= len) {
                res.add(i + 1);
            }
        }
        return res;
    }
}
