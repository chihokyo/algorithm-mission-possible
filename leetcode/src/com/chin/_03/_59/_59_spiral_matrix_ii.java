package com.chin._03._59;

import java.util.ArrayList;
import java.util.List;

public class _59_spiral_matrix_ii {
    // 直接模拟
    public int[][] generateMatrix(int n) {
        // top,right,bottom,left
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int row = 0, col = 0, dir = 0;

        int[][] res = new int[n][n];
        boolean[][] visited = new boolean[n][n];

        for (int i = 0; i < n * n; i++) {
            res[row][col] = i + 1;
            visited[row][col] = true;

            int nextRow = row + dirs[dir][0];
            int nextCol = col + dirs[dir][1];

            if (nextRow < 0 ||
                    nextCol < 0 ||
                    nextRow >= n ||
                    nextCol >= n ||
                    // 如果已经被访问过
                    visited[nextRow][nextCol]) {
                dir = (dir + 1) % 4;
            }

            row = row + dirs[dir][0];
            col = col + dirs[dir][1];
        }
        return res;
    }

    // 按层模拟
    public int[][] generateMatrix2(int n) {
        // 新建结果集
        int[][] res = new int[n][n];
        // 初始化行列开始结尾4个变量
        int rowS = 0, rowE = n - 1;
        int colS = 0, colE = n - 1;

        int i = 1; // 因为题目要求1开始
        // top → right → bottom → left 进行遍历
        while (rowS <= rowE && colS <= colE) {
            // top 最上面1行
            for (int col = colS; col <= colE; col++) res[rowS][col] = i++;
            // right
            for (int row = rowS + 1; row <= rowE; row++) res[row][colE] = i++;
            // 这里要进行判断，如果只有1行和只有1列的情况下
            if (rowS < rowE && colS < colE) {
                // 为什么从-1开始，因为最后一个属于right那里，为什么没有=，因为最后1个属于 left
                // bottom
                for (int col = colE - 1; col > colS; col--) res[rowE][col] = i++;
                // left
                // 为什么又不从-1开始，因为这里是从最底部开始的，正好是right那里拿来的
                // 为什么又没有== 因为==那个属于top 其实看一下图就能知道
                for (int row = rowE; row > rowS; row--) res[row][colS] = i++;
            }
            rowS++;
            rowE--;
            colS++;
            colE--;
        }
        return res;
    }
}
