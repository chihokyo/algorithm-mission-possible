package com.chin._05._67;

// 这一题和415几乎一模一样就是改变了一下10进制到2进制
public class _67_add_binary {
    public String addBinary(String a, String b) {
        StringBuilder res = new StringBuilder();
        int len1 = a.length() - 1;
        int len2 = b.length() - 1;
        int carry = 0;
        while (len1 >= 0 || len2 >= 0) {
            int x = len1 < 0 ? 0 : a.charAt(len1) - '0';
            int y = len2 < 0 ? 0 : b.charAt(len2) - '0';
            int sum = x + y + carry;
            // 这里是从10进制→2进制
            res.append(sum % 2);
            // 这里是从10进制→2进制
            carry = sum / 2;
            len1--;
            len2--;
        }
        if (carry != 0) res.append(carry);
        return res.reverse().toString();
    }
}
