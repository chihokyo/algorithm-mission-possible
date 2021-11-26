package com.chin._01._442;

import java.util.ArrayList;
import java.util.List;

public class _442_find_all_duplicates_in_an_array {

    /**
     * 方法1 负数
     *
     * @param nums
     * @return
     */
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>(); // 结果集不算额外空间
        for (int i = 0; i < nums.length; i++) {
            // 这里必须取绝对值，有可能索引为负数
            int index = Math.abs(nums[i]) - 1;
            // 说明已经出现过了1次
            if (nums[index] < 0) {
                // 这里也必须取绝对值，不然索引是负数
                // 原本的值添加到结果集
                res.add(Math.abs(nums[i]));
            } else {
                // 说明没有出现过，反转为负数
                // 需要把索引对应的值翻转
                nums[index] = -nums[index];
            }
        }
        return res;
    }

    /**
     * 方法2 两倍2n
     *
     * @param nums
     * @return
     */
    public List<Integer> findDuplicates2(int[] nums) {
        int len = nums.length;
        // 第一次遍历 重复的加倍
        for (int i = 0; i < len; i++) {
            // 由于这里可能是已经加倍过的，为了找到原来的index 需要这样计算
            // 注意这里要-1
            int index = (nums[i] - 1) % len;
            nums[index] += len;
        }
        // 第二次遍历 找到结果
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            // 高于总长度2倍的，说明加了2次，也就是重复了2次
            // 加入结果集的是值，而不是索引 这个时候如果直接add(i)
            // 索引 + 1 = 值
            if (nums[i] > 2 * len) res.add(i + 1);
        }
        return res;
    }
}
