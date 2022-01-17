package com.chin._04._13;

public class _13_roman_to_integer {
    public int romanToInt(String s) {
        int res = 0;
        int pre = getNum(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            int cur = s.charAt(i);
            if (pre > cur) {
                res += pre;
            } else {
                res -= pre;
            }
            pre = cur; // 这里很容易忘记 因为要向前继续遍历的
        }
        res += pre; // 这里很容易忘记 因为最后会只剩下1个pre元素！且一定会剩下
        return res;
    }

    private int getNum(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }
}
