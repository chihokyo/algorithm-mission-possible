package com.chin._08._69;

public class _69_sqrtx {
    // 暴力求解 超时了
    public int mySqrt(int x) {
        // 不写0是因为0也有可能是答案
        int ans = -1;
        for (int k = 0; k <= x; k++) {
            // 这里一定要long，不然k*k之后可能会整型溢出
            if ((long) k * k <= x) {
                ans = x;
            }
        }
        return ans;
    }

    // 二分法 写法1
    public int mySqrt2(int x) {
        int left = 0, right = x;
        while (left < right) {
            // 取mid
            int mid = left + right >> 1;
            // 看看这个mid的值到底是不是小于
            if ((long) mid * mid < x) {
                // 小于直接到右边找
                left = mid + 1;
            } else {
                // >= 去左边找
                right = mid;
            }
        }
        // 最后只剩下一个,判断结果。这里有一个问题就是left-1 因为
        // 跳出循环的时候left是mid+1，返回的时候自然要减1
        return (long) left * left <= x ? left : left - 1;
    }

    // 二分法 写法2
    public int mySqrt3(int x) {
        int left = 0, right = x, res = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long) mid * mid <= x) {
                // 这里的mid有可能是答案，但是可能右边还有
                res = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        // 这里就是走完了全部的数字！
        return res;
    }
}
