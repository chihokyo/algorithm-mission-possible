package com.chin.first._344;

public class _344_reverse_string {
    // while
    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        // 为什么不是<= 因为相等的时候必定是在轴心这时候指向同一个
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }

    // for
    public void reverseString1(char[] s) {
        int len = s.length;
        for (int i = 0; i < len / 2; i++) {
            int j = len - 1 - i;
            char temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }
    }
}
