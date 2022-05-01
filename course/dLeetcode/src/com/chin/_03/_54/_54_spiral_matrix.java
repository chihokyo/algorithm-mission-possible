package com.chin._03._54;

import java.util.ArrayList;
import java.util.List;

public class _54_spiral_matrix {

    // 直接模拟
    public List<Integer> spiralOrder(int[][] matrix) {
        // 定义4个方向
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int rows = matrix.length; // 多少行
        int cols = matrix[0].length; // 多少列

        int row = 0, col = 0;
        int dir = 0;
        List<Integer> res = new ArrayList<>(); // 结果集
        boolean[][] visited = new boolean[rows][cols]; // 判断是否被访问

        // 开始遍历每一个元素
        for (int i = 0; i < rows * cols; i++) {
            res.add(matrix[row][col]); // 添加到结果集
            visited[row][col] = true; // 添加成功，就标记到已访问
            // 这里求的是下一个该走的位置
            // 第一次是 dirs[0][0], 也就是{0,1}里的0
            int nextRow = row + dirs[dir][0];
            // 第一次是 dirs[0][1], 也就是{0,1}里的1
            int nextCol = col + dirs[dir][1];

            // 判断4种情况
            if (nextRow < 0 // 超出范围 从右向左 ←
                    || nextRow >= rows // 超出范围 从左向右 →
                    || nextCol < 0 // 超出范围 从下到上 ↑
                    || nextCol >= cols // 超出范围 从上到 ↓
                    || visited[nextRow][nextCol] // 并且访问了
            ) {
                // 相当于 {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
                // 从 {{0, 1} 走到了 {1, 0}
                dir = (dir + 1) % 4; // 有可能越界，所以取模
            }

            // 重新计算方向 也就是向下一个该怎么走
            row = row + dirs[dir][0];
            col = col + dirs[dir][1];
        }

        return res;
    }

    // 按层模拟
    public List<Integer> spiralOrder2(int[][] matrix) {
        // 新建结果集
        List<Integer> res = new ArrayList<>();
        // 初始化行列开始结尾4个变量
        int rowS = 0, rowE = matrix.length - 1;
        int colS = 0, colE = matrix[0].length - 1;
        // top → right → bottom → left 进行遍历
        while (rowS <= rowE && colS <= colE) {
            // top 最上面1行
            for (int col = colS; col <= colE; col++) res.add(matrix[rowS][col]);
            // right
            for (int row = rowS + 1; row <= rowE; row++) res.add(matrix[row][colE]);
            // 这里要进行判断，如果只有1行和只有1列的情况下
            if (rowS < rowE && colS < colE) {
                // 为什么从-1开始，因为最后一个属于right那里，为什么没有=，因为最后1个属于 left
                // bottom
                for (int col = colE - 1; col > colS; col--) res.add(matrix[rowE][col]);
                // left
                // 为什么又不从-1开始，因为这里是从最底部开始的，正好是right那里拿来的
                // 为什么又没有== 因为==那个属于top 其实看一下图就能知道
                for (int row = rowE; row > rowS; row--) res.add(matrix[row][colS]);
            }
            rowS++;
            rowE--;
            colS++;
            colE--;
        }
        return res;
    }
}
