package com.chin._04._344;

public class _344_reverse_string {
    public void reverseString(char[] s) {
        int n = s.length;
        // 从i开始交换
        for (int i = 0; i < n / 2; i++) {
            int j = n - i - 1;
            char temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }
    }
}
