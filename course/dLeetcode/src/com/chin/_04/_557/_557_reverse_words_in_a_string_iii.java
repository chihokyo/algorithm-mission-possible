package com.chin._04._557;

public class _557_reverse_words_in_a_string_iii {
    // 反转写在里面
    public String reverseWords(String s) {
        char[] chars = s.toCharArray();
        int n = s.length();
        int left = 0;
        while (left < n) {
            // 如果left不为空
            if (chars[left] != ' ') {
                // 找到right
                int right = left;
                // 只要小于长度 && 并且不为空 就向前走
                while (right + 1 < n && chars[right + 1] != ' ') right++;
                int start = left;
                int end = right;
                while (start < end) {
                    char temp = chars[start];
                    chars[start] = chars[end];
                    chars[end] = temp;
                }
                left = right + 1;
            } else {
                // 否则向前走
                left++;
            }
        }
        return new String(chars);
    }

    // 反转写在外面
    public String reverseWords2(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        int left = 0;
        while (left < n) {
            if (chars[left] != ' ') {
                int right = left;
                while (right + 1 < n && chars[right + 1] != ' ') right++;
                // 这个时候left right都已经固定了
                // 左右反转
                reserve(chars, left, right);
                left = right + 1;
            } else {
                left++;
            }
        }
        return new String(chars);
    }

    // 反转
    private void reserve(char[] chars, int start, int end) {
        char temp;
        while (start < end) {
            temp = chars[start];
            chars[start++] = chars[end];
            chars[end--] = temp;
        }
    }

}
