package com.chin._01._125;

public class _125_valid_palindrome {
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
            // 如果此时left依然小于right，经过上面的处理有可能不等
            if (left < right) {
                // 忽略大小写之后，不等false
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                // 继续向前走
                left++;
                right--;
            }
        }
        // 全部走完没事，就是true
        return true;
    }
}
