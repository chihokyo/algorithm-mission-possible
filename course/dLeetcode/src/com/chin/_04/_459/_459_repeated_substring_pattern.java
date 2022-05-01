package com.chin._04._459;

public class _459_repeated_substring_pattern {

    // 字符串匹配
    public boolean repeatedSubstringPattern3(String s) {
        // 为什么不能是头和尾，如果是头or尾，都有可能是原数组
        // 从1开始
        // 得到的不能等于最后1个
        // 这种写法一举两得
        return (s + s).indexOf(s, 1) != s.length();
    }

    // 旋转1 超出时间限制
    public boolean repeatedSubstringPattern2(String s) {
        // 从len为1开始尝试进行旋转
        for (int len = 1; len * 2 <= s.length(); len++) {
            // 如果旋转之后得到的字符串和现在相同
            if (s.equals(rotate(s.toCharArray(), len))) {
                return true;
            }
        }
        return false;
    }


    // 反转
    public String rotate(char[] chars, int k) {
        int n = chars.length;
        k = k % n;
        reserve(chars, 0, n - 1);
        reserve(chars, 0, k - 1);
        reserve(chars, k, n - 1);
        return String.valueOf(chars);
    }

    // 翻转
    private void reserve(char[] chars, int start, int end) {
        while (start < end) {
            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;
            start++;
            end--;
        }
    }


    // 双指针模拟 错误的！NG
    public boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        for (int len = 1; len < n; len++) {
            boolean matched = true; // 标记是否匹配
            int i = 0;
            for (int j = len; j < n; j++) {
                if (s.charAt(len) != s.charAt(j)) {
                    matched = false; // 不匹配
                    i++;
                    break;
                }
            }
            if (matched) return true; // 匹配上了
        }
        return false;
    }

    // 双指针模拟（稍微优化）
    // len最大为n的一半
    // 但无论怎么优化 时间复杂度都是O(n^2) 空间是O(1)
    public boolean repeatedSubstringPattern1(String s) {
        int n = s.length();
        for (int len = 1; len * 2 <= n; len++) {
            // 因为n必定是len的倍数
            if (n % len == 0) {
                boolean matched = true; // 标记是否匹配
                int i = 0; // 初始化i
                for (int j = len; j < n; j++, i++) {
                    if (s.charAt(i) != s.charAt(j)) {
                        matched = false; // 不匹配
                        break;
                    }
                }
                if (matched) return true; // 匹配上了
            }
        }
        return false;
    }
}
