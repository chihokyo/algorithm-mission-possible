package com.chin._02._135;

import javax.print.attribute.standard.PrinterMakeAndModel;
import java.util.Arrays;

public class _135_candy {
    // 暴力解法 O(n^2)
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);
        // 初始化hasChanged是true
        boolean hasChanged = true;
        // 只要还是true
        while (hasChanged) {
            // 先改变一下状态 默认是false
            hasChanged = false;
            for (int i = 0; i < n; i++) {
                // i不是最后1个，并且i小孩分数大于右边小孩分数，且i小孩糖果要小于等于右边小孩
                if (i != n - 1 && ratings[i] > ratings[i + 1] && candies[i] <= candies[i + 1]) {
                    // 当前i小孩需要右小孩的糖果+1
                    candies[i] = candies[i + 1] + 1;
                    hasChanged = true;
                }
                // i不是第1个，并且i小孩分数比左边要大，且i小孩糖果要小于等于右边小孩
                if (i != 0 && ratings[i] > ratings[i - 1] && candies[i] <= candies[i - 1]) {
                    // 当前i小孩需要左小孩的糖果+1
                    candies[i] = candies[i - 1] + 1;
                    hasChanged = true;
                }
            }
        }

        int sum = 0;
        for (int candy : candies) sum += candy;
        return sum;
    }

    // 两组数组 + 两次遍历
    public int candy2(int[] ratings) {
        int n = ratings.length;
        int[] l2r = new int[n];
        int[] r2l = new int[n];
        Arrays.fill(l2r, 1); // 初始化从前到后
        Arrays.fill(r2l, 1); // 初始化从后到前

        // 从前到后
        for (int i = 0; i < n; i++) {
            // [4,5]，5大于4的话 [1,1]→[1,2]
            if (i != 0 && ratings[i] > ratings[i - 1]) {
                l2r[i] = l2r[i - 1] + 1;
            }
        }
        int sum = 0;
        // 从后到前
        for (int i = n - 1; i >= 0; i--) {
            // [2,3] [i+1,i] , 所以2要变成3+1也就是4 [4,3]
            if (i != n - 1 && ratings[i] > ratings[i + 1]) {
                r2l[i] = r2l[i + 1] + 1;
            }
            // 这个时候其实当前数组谁最大已经都确定了，这个时候直接取大的就行
            sum = sum + Math.max(l2r[i], r2l[i]);
        }
        return sum;
    }

    // 【优化后】两组数组 + 两次遍历
    public int candy3(int[] ratings) {
        int n = ratings.length;
        int[] l2r = new int[n];
        Arrays.fill(l2r, 1); // 初始化从前到后

        // 从前到后
        for (int i = 0; i < n; i++) {
            // [4,5]，5大于4的话 [1,1]→[1,2]
            if (i != 0 && ratings[i] > ratings[i - 1]) {
                l2r[i] = l2r[i - 1] + 1;
            }
        }
        int sum = 0;
        int right = 0; // 默认是0
        // 从后到前
        for (int i = n - 1; i >= 0; i--) {
            if (i != n - 1 && ratings[i] > ratings[i + 1]) {
                // 后面的大于前面 right++, 小于的话直接赋值成1就行
                right++;
            } else {
                right = 1;
            }
            sum += Math.max(l2r[i], right);
        }
        return sum;
    }
}
