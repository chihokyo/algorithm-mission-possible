package com.chin._05._989;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoNumberAdd {
    public List<Integer> addTwoNum(int[] nums1, int[] nums2) {
        List<Integer> res = new ArrayList<>();
        int len1 = nums1.length - 1;
        int len2 = nums2.length - 1;
        int carry = 0; // 默认为0
        while (len1 >= 0 || len2 >= 0) {
            int x = len1 < 0 ? 0 : nums1[len1];
            int y = len2 < 0 ? 0 : nums2[len2];
            int sum = x + y + carry;
            // sum % 10 相当于要最后个位
            res.add(sum % 10);
            carry = sum / 10; // 取出来进位
            len1--;
            len2--;
        }
        // 要先做判断不能为0，不然反转会回来会多个0
        // 不能忘记添加最后1个进位 比如 222 + 899 = (1)121
        if (carry != 0) res.add(carry);
        Collections.reverse(res); // 添加是从前面添加的
        return res;
    }
}
