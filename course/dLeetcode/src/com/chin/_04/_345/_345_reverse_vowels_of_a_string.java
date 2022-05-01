package com.chin._04._345;

public class _345_reverse_vowels_of_a_string {
    public String reverseVowels(String s) {
        // 字符串 → 字符数组
        char[] chars = s.toCharArray();
        int n = s.length();
        // 双指针
        int left = 0, right = n - 1;
        while (left < right) {
            // 如果不是元音 才向前走
            while (left < right && !checkVowel(chars[left])) left++;
            while (left < right && !checkVowel(chars[right])) right--;
            // 判断不是原因之后才向前
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return String.valueOf(chars);
    }

    public String reverseVowels2(String s) {
        // 字符串 → 字符数组
        char[] chars = s.toCharArray();
        int n = s.length();
        // 双指针
        int left = 0, right = n - 1;
        for (; left < right; left++, right--) {
            for (; left < right && !checkVowel(chars[left]); left++) {
            }
            for (; left < right && !checkVowel(chars[right]); right--) {
            }
            // 判断不是原因之后才向前
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
        }
        return String.valueOf(chars);
    }

    private boolean checkVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'
                || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }
}
