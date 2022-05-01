package com.chin._05._9;

public class _9_palindrome_number {

    public boolean isPalindrome3(int x) {
        // 0的时候肯定是ok
        if (x == 0) return true;
        // 如果是负数 或者个位数是0也不行
        if (x < 0 || x % 10 == 0) return false;
        int res = 0;
        // 这里为什么是res小于x
        // 因为按照数字大小比3位数肯定比2位数大，只要res比x小，说明一直没有比到一半
        // 等于(偶数)or大于(奇数) 都说明已经比完了
        while (res < x) {
            res = res * 10 + (x % 10);
            x = x / 10;
        }
        // 偶数的情况下相等，奇数的情况下去掉最后最后1位个位
        return res == x || x == res / 10;
    }

    // 这个写法也是转换成字符串 然后最后对比 但判断的溢出
    public boolean isPalindrome2(int x) {
        // 0的时候肯定是ok
        if (x == 0) return true;

        // 如果是负数 或者个位数是0也不行
        if (x < 0 || x % 10 == 0) return false;
        int res = 0;
        int xx = x; // ∵x在下面会被不停的修改 所以需要暂存一下
        while (x != 0) {
            int pop = x % 10;
            x = x / 10;
            // 反转整数的时候，可能出现溢出
            // MAX_VALUE = 2^31 - 1 = 2147483647
            if (res > Integer.MAX_VALUE / 10
                    || (res == Integer.MAX_VALUE / 10 && pop > 7)) return false;
            // MIN_VALUE = -2^31 = -2147483648
            if (res < Integer.MIN_VALUE / 10
                    || (res == Integer.MIN_VALUE / 10 && pop < -8)) return false;
            res = res * 10 + pop;
        }
        return xx == res; // 为什么用xx?答案在上面
    }

    // int → string
    public boolean isPalindrome1(int x) {
        String xStr = String.valueOf(x);
        int left = 0, right = xStr.length() - 1;
        while (left < right) {
            if (xStr.charAt(left) != xStr.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }
}
