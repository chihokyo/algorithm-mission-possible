package com.chin._03._498;

public class _498_diagonal_traverse {
    public int[] findDiagonalOrder(int[][] mat) {
        // 特殊判断
        if (mat.length == 0) return new int[0];
        int rowLen = mat.length; // row行长度
        int colLen = mat[0].length; // col列长度
        int[] res = new int[rowLen * colLen]; // 初始化结果集
        int[][] dirs = {{-1, 1}, {1, -1}}; // 斜下走初始化，斜上走初始化

        int row = 0, col = 0, dir = 0; // 初始化行列方向
        // 开始遍历
        for (int i = 0; i < rowLen * colLen; i++) {
            // 先赋值
            res[i] = mat[row][col];
            // 然后找寻下一个
            row = row + dirs[dir][0];
            col = col + dirs[dir][1];

            // 以下4个判断的顺序不能打乱
            // 如果超过列
            if (col >= colLen) {
                col = colLen - 1;
                row += 2;
                dir = 1 - dir;
            }
            // 如果超过行
            if (row >= rowLen) {
                row = rowLen - 1;
                col += 2;
                dir = 1 - dir;
            }
            // 如果小于行
            if (row < 0) {
                row = 0;
                dir = 1 - dir;
            }
            // 如果小于列
            if (col < 0) {
                col = 0;
                dir = 1 - dir;
            }

        }
        return res;
    }
}
