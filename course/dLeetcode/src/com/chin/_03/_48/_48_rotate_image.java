package com.chin._03._48;

public class _48_rotate_image {
    // 使用额外数组，空间和时间复杂度都是O(n^2)
    public void rotate1(int[][] matrix) {
        int n = matrix.length;
        // 初始化额外数组
        int[][] newM = new int[n][n];
        // 循环
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                // 规律，行列调换之后，row要求长度-1
                newM[col][n - row - 1] = matrix[row][col];
            }
        }
        // 因为这一题要求修改原矩阵，所以必须要重新赋值给原数组
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                matrix[row][col] = newM[row][col];
            }
        }
    }

    // 原地旋转 主要利用一个数在变换4次之后会回到原点，形成闭环
    public void rotate2(int[][] matrix) {
        int n = matrix.length;
        for (int row = 0; row < n / 2; row++) {
            for (int col = 0; col < (n + 1) / 2; col++) {
                // [row][col] = [col][n-row-1] 基本公式
                int temp = matrix[row][col];
                //  这里的连续赋值看图理解会比较快
                matrix[row][col] = matrix[n - col - 1][row];
                matrix[n - col - 1][row] = matrix[n - row - 1][n - col - 1];
                matrix[n - row - 1][n - col - 1] = matrix[col][n - row - 1];
                matrix[col][n - row - 1] = temp;
            }
        }
    }

    // 原地翻转 顺时针旋转90度=主对角线翻转+左右翻转 or 水平翻转+对角线翻转
    public void rotate3(int[][] matrix) {
        int n = matrix.length;
        // 水平翻转 [row][col] = [n - row - 1][col]
        // 行的话翻转1半就够了
        for (int row = 0; row < n / 2; row++) {
            for (int col = 0; col < n; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[n - row - 1][col];
                matrix[n - row - 1][col] = temp;
            }
        }
        // 对角线翻转 [row][col] = [col][row]
        // row要翻转n的次数
        for (int row = 0; row < n; row++) {
            // 对角线翻转 只需要翻转row次
            for (int col = 0; col < row; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }
    }
}