package com.chi.line.twopointer;

public class _344_ReverseWords {
    // 暴力解法
    public void reverseString(char[] s) {
        char[] temp = new char[s.length];
        // 初始化指针
        int j = s.length - 1;
        for (int i = 0; i < s.length; i++) {
            temp[j] = s[i];
            // 用完记得--
            j--;
        }

        for (int i = 0; i < s.length; i++) {
            s[i] = temp[i];
        }
    }

    // 对撞指针
    public void reverseString2(char[] s) {
        int j = s.length - 1; // 后面的j
        int i = 0; // 前面的i
        // 这里i=j的情况下 是不用交换的 只有i<j说明还没有交换完
        while (i < j) {
            char temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            i++;
            j--;
        }
    }
}
