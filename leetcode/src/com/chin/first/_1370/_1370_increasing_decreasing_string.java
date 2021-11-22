package com.chin.first._1370;

public class _1370_increasing_decreasing_string {
    public String sortString(String s) {
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            counts[c - 'a']++;
        }

        StringBuilder sb = new StringBuilder();
        while (sb.length() < s.length()) {
            // 上升
            for (int i = 0; i < 26; i++) {
                // 次数大于0
                if (counts[i] > 0) {
                    sb.append((char) (i + 'a'));
                    counts[i]--;
                }
            }
            // 下降
            for (int i = 25; i >= 0; i--) {
                if (counts[i] > 0) {
                    sb.append((char) (i + 'a'));
                    counts[i]--;
                }
            }
        }
        return sb.toString();
    }
}
