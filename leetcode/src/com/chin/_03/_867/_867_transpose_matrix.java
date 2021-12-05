package com.chin._03._867;

public class _867_transpose_matrix {
    public int[][] transpose(int[][] matrix) {
        int row = matrix.length; // 多少行 row
        int col = matrix[0].length; // 多少列 col
        int[][] res = new int[col][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // [i][j],[i][j+1],[i][j+2].. 进行转换
                res[j][i] = matrix[i][j];
            }
        }
        return res;
    }
}
