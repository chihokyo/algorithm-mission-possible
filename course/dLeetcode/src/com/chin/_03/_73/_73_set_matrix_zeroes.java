package com.chin._03._73;

public class _73_set_matrix_zeroes {
    // 方法1 时间空间复杂度都是O(mn)
    public void setZeroes1(int[][] matrix) {

        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        // 初始化2个数组记录行列信息
        boolean[] rows = new boolean[rowLen];
        boolean[] cols = new boolean[colLen];
        // 开始遍历然后记录信息
        for (int row = 0; row < rowLen; row++) {
            for (int col = 0; col < colLen; col++) {
                // 其中任意元素只要为0
                if (matrix[row][col] == 0) {
                    // 对应的行和列就为true
                    rows[row] = true;
                    cols[col] = true;
                }
            }
        }

        // 走到这里行列信息记录完毕，修改原数组
        for (int row = 0; row < rowLen; row++) {
            for (int col = 0; col < colLen; col++) {
                // 只要行和列任意达标 是true
                if (rows[row] || cols[col]) {
                    // 修改那一元素
                    matrix[row][col] = 0;
                }
            }
        }
    }

    // 方法2 时间复杂度就是O(mn)空间复杂度就是O(1) 2个变量
    public void setZeroes2(int[][] matrix) {
        // 初始化行列信息
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        // 先遍历第1行第1列
        boolean flagRow = false;
        for (int i = 0; i < colLen; i++) {
            if (matrix[0][i] == 0) flagRow = true;
        }

        boolean flagCol = false;
        for (int i = 0; i < rowLen; i++) {
            if (matrix[i][0] == 0) flagCol = true;
        }
        // 从第2行开始记录
        for (int row = 1; row < rowLen; row++) {
            for (int col = 1; col < colLen; col++) {
                if (matrix[row][col] == 0) {
                    matrix[0][col] = 0;
                    matrix[row][0] = 0;
                }
            }
        }
        // 修改原数组
        for (int row = 1; row < rowLen; row++) {
            for (int col = 1; col < colLen; col++) {
                // 当第一行任意一个等于0
                if (matrix[0][col] == 0 || matrix[row][0] == 0) {
                    matrix[row][col] = 0;
                }
            }
        }

        // 第1行第1列全为0
        if (flagRow) {
            for (int i = 0; i < colLen; i++) matrix[0][i] = 0;
        }

        if (flagCol) {
            for (int i = 0; i < rowLen; i++) matrix[i][0] = 0;
        }

    }

    // 方法3 时间复杂度就是O(mn)空间复杂度就是O(1) 1个变量
    public void setZeroes3(int[][] matrix) {
        // 初始化行列信息
        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        boolean flagRow = false;
        // 因为只记录第1列的信息，所以row从0开始
        for (int row = 0; row < rowLen; row++) {
            if (matrix[row][0] == 0) flagRow = true;
            for (int col = 1; col < colLen; col++) {
                // 只要元素为0，所在的行列就为0
                if (matrix[row][col] == 0) {
                    matrix[row][0] = 0;
                    matrix[0][col] = 0;
                }
            }
        }

        // 记住！这里要从后向前遍历 并且看好顺序，是从最后一行开始从左向右
        for (int row = rowLen - 1; row >= 0; row--) {
            for (int col = 1; col < colLen; col++) {
                // 只要行列任意为0，那么自己就是0
                if (matrix[row][0] == 0 || matrix[0][col] == 0) {
                    matrix[row][col] = 0;
                }
            }
            // 如果第一列的行头是0，那么整列就是0
            if (flagRow) matrix[row][0] = 0;
        }
    }
}
