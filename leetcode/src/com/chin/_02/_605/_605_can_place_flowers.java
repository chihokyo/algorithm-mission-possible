package com.chin._02._605;

public class _605_can_place_flowers {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int i = 0;
        // 当所有花坛的话遍历完or花种完了 停止循环
        while (i < flowerbed.length && n > 0) {
            // 当前已经种花了，那起码要到i+2才能种花
            if (flowerbed[i] == 1) {
                i += 2;
            } else if (i == flowerbed.length - 1 || flowerbed[i + 1] == 0) {
                // 走到这里说明
                // i没有种花。如果i是最后一个，或者i和i+1都没有种花
                // 说明i可以种上花了 所以n--。并且下一个i必须是i+2
                n--;
                // 至此，至少需要到 i + 2 的地方才能种花
                i += 2;
            } else {
                // 走到这里说明
                // i 处没有种花，但是 i + 1 处种花了
                // 那么这个时候，至少需要到 i + 3 的地方才能种花
                i += 3;
            }
        }
        // 当n减为0时，说明可以种入n朵花，则可以直接退出遍历返回true
        // 如果遍历结束n没有减到0，说明最多种入的花的数量小于n，则返回false。
        return n <= 0;
    }
}
