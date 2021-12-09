package com.chin._03._36;

public class _36_valid_sudoku {
    public boolean isValidSudoku(char[][] board) {
        boolean[][] rowUsed = new boolean[9][9];
        boolean[][] colUsed = new boolean[9][9];
        boolean[][] boxUsed = new boolean[9][9];

        // 初始化1个九宫格
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] != '.') {
                    // 寻找相应的位置
                    int num = board[row][col] - '1';
                    if (rowUsed[row][num]) return false;
                    else rowUsed[row][num] = true;

                    if (colUsed[col][num]) return false;
                    else colUsed[col][num] = true;

                    int boxIndex = row / 3 + (col / 3) * 3;
                    if (boxUsed[boxIndex][num]) return false;
                    else boxUsed[boxIndex][num] = true;
                }
            }
        }

        return true;
    }
}
