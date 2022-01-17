package com.chin._04._12;

import java.util.HashMap;

public class _12_integer_to_roman {

    public String intToRoman(int num) {
        // 首先要写一个关系
        // 这里有点使用了贪心的思想 从最大的开始找
        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romans = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder res = new StringBuilder();
        int index = 0;
        while (index < 13) {
            // 这里用的是while而不是if 是因为 有可能-去之后的值正好等于当前有的值 也就是nums里的值
            while (num > nums[index]) {
                // 这个时候已经小了 先添加到res结果集里面
                res.append(romans[index]);
                num -= nums[index]; // num = num - nums[index]
                index++;
            }
        }
        return res.toString();
    }
}
