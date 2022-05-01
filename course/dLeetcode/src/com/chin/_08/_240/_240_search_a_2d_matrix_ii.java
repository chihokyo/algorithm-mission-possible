package com.chin._08._240;

public class _240_search_a_2d_matrix_ii {
    /**
     * 暴力解法
     * 时间复杂度O(mn)
     * 空间复杂度O(1)
     *
     * @param matrix 二维数组
     * @param target 目标值
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = matrix.length, col = matrix[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 缩小搜索范围
     *
     * @param matrix 二维数组
     * @param target 目标值
     * @return
     */
    public boolean searchMatrix2(int[][] matrix, int target) {
        int row = matrix.length, col = matrix[0].length;
        // 起点，左下方
        int i = row - 1, j = 0;
        // 跳出来点，走完列 并且 也走完了行
        while (i >= 0 && j < col) {
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] > target) {
                // 大了，行--
                i--;
            } else {
                // 小了，列++
                j++;
            }
        }
        return false;
    }

    /**
     * 二分查找
     *
     * @param matrix 二维数组
     * @param target 目标值
     * @return
     */
    public boolean searchMatrix3(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0) return false;
        int n = matrix[0].length;
        // 先找出行列里偏小的
        int shortDim = Math.min(m, n);
        // 进行 shortDim 次的 二分
        for (int i = 0; i < shortDim; i++) {
            // 先二分行
            boolean rowFound = binarySearchRow(matrix, i, target);
            // 二分列
            boolean colFount = binarySearchCol(matrix, i, target);
            // 行列只要出了结果 就是有
            if (rowFound || colFount) {
                return true;
            }
        }
        return false;
    }

    private boolean binarySearchRow(int[][] matrix,
                                    int row, int target) {
        int lo = row;
        int hi = matrix[0].length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            // 这里要注意！matrix[行][mid]
            if (matrix[row][mid] == target) {
                return true;
            } else if (matrix[row][mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return false;
    }

    private boolean binarySearchCol(int[][] matrix,
                                    int col, int target) {
        int le = col;
        int hi = matrix.length - 1;

        while (le <= hi) {
            int mid = le + (hi - le) / 2;
            // 这里要注意！matrix[mid][列]
            if (matrix[mid][col] == target) {
                return true;
            } else if (matrix[mid][col] < target) {
                le = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return false;
    }
}
