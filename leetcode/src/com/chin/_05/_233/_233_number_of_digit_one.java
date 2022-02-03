package com.chin._05._233;

public class _233_number_of_digit_one {

    // 数学推算 太难 不看 复杂度：O(log n)
    public int countDigitOne(int n) {
        int res = 0;
        for (long i = 1; i <= n; i *= 10) {
            // i 应该是长整型，要不然会溢出
            long divider = i * 10;
            // (n/10)*1 + min(max((n%10 - 1 + 1), 0), 1)
            // (n/100)*10 + min(max((n%100 - 10 + 1), 0), 10)
            // (n/1000)*100 + min(max((n%1000 - 100 + 1), 0), 100)
            res += (n / divider) * i + Math.min(Math.max(n % divider - i + 1, 0L), i);
        }
        return res;
    }

    // 暴力解法
    public int countDigitOne1(int n) {
        int res = 0;
        // 这里要从1开始 因为根据题意 就是1开始
        for (int i = 1; i <= n; i++) {
            // 数字 → 字符
            String s = String.valueOf(i);
            int oneRes = 0; // 临时计算每一个字符串有没有
            // 字符 → 字符串数组
            for (char c : s.toCharArray()) {
                // 是否为1
                if (c == '1') oneRes++;
            }
            res += oneRes;
        }
        return res;
    }
}

