package com.chin._01._11;

public class _11_container_with_most_water {
    // 暴力解法 超时
    public int maxArea(int[] height) {
        // 柱子个数
        int n = height.length;
        int res = 0;
        // 固定1根柱子i
        for (int i = 0; i < n; i++) {
            // 然后接下来继续看下一根柱子
            for (int j = i + 1; j < n; j++) {
                int tempArea = Math.min(height[i], height[j]) * (j - i);
                res = Math.max(tempArea, res);
            }
        }
        return res;
    }

    // 对撞指针
    public int maxArea1(int[] height) {
        int res = 0;
        int left = 0;
        int right = height.length - 1;
        while(left < right) {
            // 取得面积
            int area = Math.min(height[left], height[right]) * (right - left);
            res = Math.max(res,area);
            // 在面积固定的时候，宽度固定的时候
            if (height[left] <= height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return res;
    }
}
