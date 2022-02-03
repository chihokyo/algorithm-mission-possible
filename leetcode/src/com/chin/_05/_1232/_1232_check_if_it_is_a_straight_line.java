package com.chin._05._1232;

public class _1232_check_if_it_is_a_straight_line {

    public boolean checkStraightLine(int[][] coordinates) {
        // 这里其实就是点了4个点 这样能计算斜率
        int x0 = coordinates[0][0];
        int y0 = coordinates[0][1];
        int deltaY = coordinates[1][1] - y0;
        int deltaX = coordinates[1][0] - x0;
        for (int i = 2; i < coordinates.length; i++) {
            int deltaYi = coordinates[i][1] - y0;
            int deltaXi = coordinates[i][0] - x0;
            // 这里其实有个巧 因为计算斜率用的除法，但是这样会遇到整除除不尽 产生误差
            // 所以除法，改成交叉乘法
            // deltaY / deltaX == deltaYi / deltaXi
            // 斜率不对了 就直接false
            if (deltaY * deltaXi != deltaX * deltaYi) return false;
        }
        return true;
    }
}
