package com.chin._04._541;

public class _541_reverse_string_ii {
    public String reverseStr(String s, int k) {
        char[] charArr = s.toCharArray();
        // start每次都要移动2*k
        for (int start = 0; start < s.length(); start += k * 2) {
            int left = start;
            // 这里的右边界要看到底在不在数组的长度之内
            // 如果left+k-1 也就是right在的坐标和长度比，要取最小的值
            int right = Math.min(left + k - 1, s.length() - 1);
            while (left < right) {
                char temp = charArr[left];
                charArr[left] = charArr[right];
                charArr[right] = temp;
                left++;
                right--;
            }
        }

        return new String(charArr);
    }
}
