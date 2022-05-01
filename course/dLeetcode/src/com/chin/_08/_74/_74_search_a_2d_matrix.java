package com.chin._08._74;

public class _74_search_a_2d_matrix {
    // 暴力遍历
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = matrix.length; // 多少行
        int col = matrix[0].length; // 多少列
        // 遍历起来
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    // 二维 → 一维 → 二分
    public boolean searchMatrix2(int[][] matrix, int target) {
        int row = matrix.length;
        int col = matrix[0].length;
        int left = 0, right = row * col - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 这个最主要看列取整，列取余
            int num = matrix[mid / col][mid % col];
            if (target == num) return true;
            else if (target > num) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }

    // 缩小范围
    public boolean searchMatrix3(int[][] matrix, int target) {
        int row = 0, col = matrix[0].length - 1;
        // 注意这里的跳出条件
        // 不能写 col > 0 而是 col >= 0
        // 如果写col > 0的版本也可以 看 searchMatrix4
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                // 比目标值大，那么列--
                col--;
            } else {
                // 比目标值小，那么行++
                row++;
            }
        }
        return false;
    }

    // col > 0 版本
    public boolean searchMatrix4(int[][] matrix, int target) {
        // 区别点1 matrix[0].length
        int row = 0, col = matrix[0].length;
        // 区别点2
        while (row < matrix.length && col > 0) {
            // 区别点3
            if (matrix[row][col - 1] == target) {
                return true;
                // 区别点4
            } else if (matrix[row][col - 1] > target) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }
}
