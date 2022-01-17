package com.chin._04._06;

import java.util.ArrayList;
import java.util.List;

public class _6_zigzag_conversion {
    public String convert(String s, int numRows) {
        // 小于2 直接return
        if (numRows < 2) return s;
        // 以下都是初始化一个包含字符串拼接的列表
        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) rows.add(new StringBuilder());
        // 初始化i表示第i个row flag表示是否转向
        int i = 0, flag = -1;
        // 遍历所有的字符
        for (char c : s.toCharArray()) {
            // 添加到相应的i也就是row
            rows.get(i).append(c);
            if (i == 0 || i == numRows - 1) flag = -flag;
            i += flag;
        }
        // 到这里应该是一个已经搞定的拼接列表list 所以要转换
        StringBuilder res = new StringBuilder();
        for (StringBuilder row : rows) res.append(row);
        return res.toString();
    }
}
