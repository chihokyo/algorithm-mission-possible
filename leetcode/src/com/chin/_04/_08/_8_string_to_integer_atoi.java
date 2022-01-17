package com.chin._04._08;

public class _8_string_to_integer_atoi {
    public int myAtoi(String s) {
        char[] chars = s.toCharArray();
        int i = 0;

        // 丢弃空格
        while (i < s.length() && s.charAt(i) == ' ') i++;
        if (i == s.length()) return 0; // 特殊情况 全部为空
        // 丢弃+-是否存在
        int sign = 1; // 默认为+
        if (chars[i] == '-' || chars[i] == '+') {
            sign = chars[i] == '-' ? -1 : 1;
            i++;
        }

        // 计算结果是否溢出
        int base = 0; // 记录基底的 也就是第一位，第一位的最前面默认是0
        // 必须是数字
        while (i < chars.length && chars[i] >= '0' && chars[i] <= '9') {
            // 检查
            // 2147483647    -2147483648
            // 这一题纠结点在于在base之前判断，还是之后判断 最好是在前面判断
            // ①/10之后溢出 or ②正好等于  2147483647的情况
            if (base > Integer.MAX_VALUE / 10 || (base == Integer.MAX_VALUE / 10 && chars[i] - '0' > 7)) {
                if (sign > 0) return Integer.MAX_VALUE;
                else return Integer.MIN_VALUE;
            }
            // 如果没有-'0'这个0 出现的就不是数字，而是asc码了
            base = base * 10 + chars[i] - '0';
            i++; // 下一位
        }
        return sign * base;
    }

    // 字符串 转换成 整数的算法
    public static void main(String[] args) {
        String str = "-88";
        int i = 0; // 记录位数
        int sign = 1; // 记录正负数
        // 这里的话要看首位是不是±符号
        if (str.charAt(i) == '-' || str.charAt(i) == '+') {
            sign = str.charAt(i) == '-' ? -1 : 1;
            i++;
        }
        int base = 0; // 记录基底的 也就是第一位，第一位的最前面默认是0
        while (i < str.length()) {
            // 如果没有-'0'这个0 出现的就不是数字，而是asc码了
            base = base * 10 + (str.charAt(i) - '0');
            i++; // 下一位
        }
        System.out.println(sign * base);
    }
}
