package com.chin._05._415;

import java.util.Collections;

// 这一题基本和数组的两数相加一模一样
public class _415_add_strings {
    public String addStrings(String num1, String num2) {
        StringBuilder res = new StringBuilder();
        int len1 = num1.length() - 1;
        int len2 = num2.length() - 1;
        int carry = 0;
        while (len1 >= 0 || len2 >= 0) {
            // num1.charAt(len1)得到的还是字符 需要'0'才能得到相应的数字
            int x = len1 < 0 ? 0 : num1.charAt(len1) - '0';
            int y = len2 < 0 ? 0 : num2.charAt(len2) - '0';
            int sum = x + y + carry;
            res.append(sum % 10);
            carry = sum / 10;
            len1--;
            len2--;
        }
        if (carry != 0) res.append(carry);
        // 这一题是通过StringBuilder这个对象的+reverse方法进行反转之后+toString变成字符串类型
        return res.reverse().toString();
    }
}
