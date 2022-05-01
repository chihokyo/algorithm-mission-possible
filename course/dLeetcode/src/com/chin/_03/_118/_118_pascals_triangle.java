package com.chin._03._118;

import java.util.ArrayList;
import java.util.List;

public class _118_pascals_triangle {
    public List<List<Integer>> generate(int numRows) {
        // 新建结果集
        List<List<Integer>> res = new ArrayList<>();
        // 开始遍历列
        for (int row = 0; row < numRows; row++) {
            // 每一列单独的数组
            List<Integer> oneRow = new ArrayList<>();
            for (int col = 0; col <= row; col++) {
                // 判断一下列是不是第1个or最后1个
                if (col == 0 || col == row) {
                    oneRow.add(1);
                } else {
                    // 然后根据现在自己的col-1,col得到值
                    List<Integer> preRow = res.get(row - 1);
                    Integer curValue = preRow.get(col - 1) + preRow.get(col);
                    oneRow.add(curValue);
                }
            }
            // 内循环结束，这样每一行内部就得到了
            res.add(oneRow);
        }
        // 外循环结束
        return res;
    }
}
