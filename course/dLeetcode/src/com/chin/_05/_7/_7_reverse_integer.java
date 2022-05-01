package com.chin._05._7;

public class _7_reverse_integer {

    // 溢出之前判断
    public int reverse1(int x) {
        int res = 0;
        while (x != 0) {
            int pop = x % 10;
            x = x / 10;
            int newRes = res * 10 + pop;
            // 判断是否溢出 新res退回如果是旧res，说明没有
            // 因为无论上溢出，下溢出 都得不到跟原来一样的数字
            if ((newRes - pop) / 10 != res) return 0;
            res = newRes;
        }
        return res;
    }


    // 溢出之后判断
    public int reverse2(int x) {
        int res = 0; // 结果
        while (x != 0) { // 只要不为0 就一直取余
            int pop = x % 10; // 取模求个位数
            x = x / 10;
            // MAX_VALUE = 2^31 - 1 = 2147483647
            if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            // MIN_VALUE = -2^31 = -2147483648
            if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            res = res * 10 + pop; // 反转得到数字
        }
        return res;
    }

    // 评论区看到的
    public int reverse3(int x) {
        long res = 0;
        while (x != 0) {
            // 弹出的 个位数 = x % 10;
            res = res * 10 + x % 10;
            x = x / 10;
        }
        // 强行转换成int之后看看是否相等
        return (int) res == res ? (int) res : 0;
    }


    // ∵使用了64了
    public int reverseNo(int x) {
        String xStr = String.valueOf(x);
        char[] xChars = xStr.toCharArray();
        int left = 0, right = xChars.length - 1;
        if (xChars[left] < '0' || xChars[left] > '9') left++;
        while (left < right) {
            char temp = xChars[left];
            xChars[left] = xChars[right];
            xChars[right] = temp;
            left++;
            right--;
        }
        // 走到这里应该已经获得了翻转的
        // 因为有可能要溢出，所以要先long起来 int支持到32 long支持到64
        // 题目要求不能使用64 所以以下都是不可行的
        long res = Long.parseLong(new String(xChars));
        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) return 0;
        // 强转成int
        return (int) res;
    }
}
