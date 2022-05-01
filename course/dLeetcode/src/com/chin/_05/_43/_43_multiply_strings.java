package com.chin._05._43;

public class _43_multiply_strings {
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";
        int m = num1.length(), n = num2.length();
        // 根据数学推理，长度分别为m和n的数组相乘
        // 数组最大长度不会超过这个的
        int[] res = new int[m + n];
        for (int i = m - 1; i >= 0; i--) {
            int x = num1.charAt(i) - '0';
            for (int j = n - 1; j >= 0; j--) {
                int y = num2.charAt(j) - '0';
                int sum = res[i + j + 1] + x * y;
                res[i + j + 1] = sum % 10;
                res[i + j] += sum / 10;
            }
        }
        // 这个时候res已经是一个数组了，但可能有0
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            // 第一个位置如果是0一定是要舍弃
            if (i == 0 && res[i] == 0) continue;
            sb.append(res[i]);
        }
        return sb.toString();
    }

    // 复杂度up版本
public String multiply1(String num1, String num2) {
    // 如果任意数字为0 结果为0
    if (num1.equals("0") || num2.equals("0")) return "0";
    String res = "0";
    int m = num1.length(), n = num2.length();
    // 处理乘数的每一位
    for (int i = n - 1; i >= 0; i--) {
        // 新建临时字符串
        StringBuilder curRes = new StringBuilder();
        // 如果空出来了，需要补0
        for (int j = n - 1; j > i; j--) curRes.append("0");
        // 85*24的话就是从24的4开始处理
        int y = num2.charAt(i) - '0';
        int carry = 0;
        for (int j = m - 1; j >= 0; j--) {
            // 85*24的话就是从85的5开始处理
            int x = num1.charAt(j) - '0';
            // 获得乘积
            int product = x * y + carry;
            // 加入到结果
            curRes.append(product % 10);
            carry = product / 10;
        }
        if (carry != 0) curRes.append(carry);
        // 走到这里其实可以获得当前的结果，和临时的结果。
        // 当前的结果和临时的结果相加，在赋值给res
        res = addStrings(res, curRes.reverse().toString());
    }
    return res;
}

// 字符串两数相加
private String addStrings(String num1, String num2) {
    StringBuilder res = new StringBuilder();
    int i1 = num1.length() - 1, i2 = num2.length() - 1;
    int carry = 0;
    while (i1 >= 0 || i2 >= 0) {
        int x = i1 >= 0 ? num1.charAt(i1) - '0' : 0;
        int y = i2 >= 0 ? num2.charAt(i2) - '0' : 0;
        int sum = x + y + carry;
        res.append(sum % 10);
        carry = sum / 10;

        i1--;
        i2--;
    }
    if (carry != 0) res.append(carry);
    return res.reverse().toString();
    }
}
