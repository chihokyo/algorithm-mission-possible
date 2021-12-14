package com.chin._03._119;

import java.util.ArrayList;
import java.util.List;

public class _119_pascals_triangle_ii {
    // 空间复杂度 O(k*k / 2) 也就是 空间复杂度：O(k^2)
    public List<Integer> getRow(int rowIndex) {
        // 新建结果集
        List<List<Integer>> res = new ArrayList<>();
        // 因为要第5行，所以事实上是index+1那一行 要==
        for (int row = 0; row <= rowIndex; row++) {
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
        // ！！只要那一行
        return res.get(rowIndex);
    }

    // 因为每次都要申请k长度的数组，并且申请k次。所以空间复杂度依然：O(k^2)
    public List<Integer> getRow2(int rowIndex) {
        // 因为只需要rowIndex的结果 所以初始化的时候，不需要一整个结果集
        // 前一个的row
        List<Integer> preRow = new ArrayList<>();
        // 因为要第5行，所以事实上是index+1那一行 要==
        for (int row = 0; row <= rowIndex; row++) {
            // 每一列单独的数组
            List<Integer> oneRow = new ArrayList<>();
            for (int col = 0; col <= row; col++) {
                // 判断一下列是不是第1个or最后1个
                if (col == 0 || col == row) {
                    oneRow.add(1);
                } else {
                    // 然后根据现在自己的col-1,col得到值
                    // 直接获取当前的行
                    oneRow.add(preRow.get(col - 1) + preRow.get(col));
                }
            }
            preRow = oneRow;
        }
        // 这个时候前一个row就是当前的row
        return preRow;
    }

    // 空间复杂度O（k）
    public List<Integer> getRow3(int rowIndex) {
        List<Integer> res = new ArrayList<>();
        res.add(1); // 首先第1行第1个肯定是1
        for (int row = 1; row <= rowIndex; row++) {
            res.add(0); // 这里java特有的，需要先初始化
            for (int col = row; col > 0; col--) {
                // 当前的索引值 = 前一个值 + 自身的值
                res.set(col, res.get(col - 1) + res.get(col));
            }
        }
        return res;
    }
}
