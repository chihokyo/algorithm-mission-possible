package com.chin._05._66;

public class _66_plus_one {
    public int[] plusOne2(int[] digits) {
        // 三种情况
        // 1. 1654
        // 2. 2889
        // 3. 9999
        // 开始遍历
        for (int i = digits.length - 1; i >= 0; i--) {
            // 最后一个加起来
            digits[i]++;
            // 如果等于10 那么当前的数字就是10
            if (digits[i] == 10) {
                // 注意这里不能直接return 因为循环还要继续 不然 2889 就不可能是2900 而是2890
                digits[i] = 0;
                // 如果不等于10 那么相当于直接 1654这种情况 直接返回
            } else {
                return digits;
            }
        }
        // 如果全部循环结束之后并没有返回 那就是9999的情况 变成10000
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }

    // 用取模判定是否为10
    public int[] plusOne1(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i]++;
            // 用10取模来判定
            // ∵任何跟个位数跟10取模，除了10以外全部是本身，10取模10是0
            digits[i] = digits[i] % 10;
            if (digits[i] != 0) return digits;
        }
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }
}
