package com.chin._02._228;

import java.util.ArrayList;
import java.util.List;

public class _228_summary_ranges {
    // while写法
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        int count = 0;
        int i = 0;
        while (i < nums.length) {
            int low = i;
            i++;
            while (i < nums.length && nums[i] - nums[i - 1] == 1) i++;
            int high = i - 1;
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toString(nums[low]));
            if (low < high) {
                sb.append("->");
                sb.append(Integer.toString(nums[high]));
            }
            res.add(sb.toString());
        }
        return res;
    }

    // for写法
    public List<String> summaryRanges2(int[] nums) {
        List<String> res = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int low = i;
            while (i + 1 < nums.length && nums[i + 1] - nums[i] == 1) i++;
            int high = i;
            StringBuilder sb = new StringBuilder(Integer.toString(nums[low]));
            if (low < high) {
                sb.append("->");
                sb.append(Integer.toString(nums[high]));
            }
            res.add(sb.toString());
        }
        return res;
    }
}
