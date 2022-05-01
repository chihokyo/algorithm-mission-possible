package com.chin._04._58;

public class _58_length_of_last_word {
    // 从右向左
    public int lengthOfLastWord(String s) {
        // 指针1 指向最后一个
        int end = s.length() - 1;
        while (end >= 0 && s.charAt(end) == ' ') end--;
        if (end < 0) return 0;
        // 到这里了就应该得到了第一个不为空的指针所处的位置
        int start = end; // 然后赋值给第2个指针
        while (start >= 0 && s.charAt(start) != ' ') start--;
        // 前指针 - 后指针
        return start - end;
    }

    // 从左向右
    public int lengthOfLastWord2(String s) {
        int res = 0;
        int start = 0, end = 0;
        while (end < s.length()) {
            // 从左向右 只要为空就向前走
            if (s.charAt(start) == ' ') {
                start++;
                end++;
            } else {
                // 不为空了 那么end不为空就向前走
                while (end < s.length() && s.charAt(end) != ' ') end++;
                // 到这里应该end和start都是确定的
                res = end - start;
                while (end < s.length() && s.charAt(end) == ' ') end++;
                if (end < s.length() && s.charAt(end) != ' ') {
                    start = end;
                }

            }
        }
        return res;
    }
}
