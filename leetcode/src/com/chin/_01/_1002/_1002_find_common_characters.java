package com.chin._01._1002;

import java.util.ArrayList;
import java.util.List;

public class _1002_find_common_characters {
    public List<String> commonChars(String[] words) {
        // 初始化最小次数数组
        int[] minFreq = new int[26];
        // 先计算第1个
        for (char c : words[0].toCharArray()) {
            minFreq[c - 'a']++;
        }
        // 从第2个开始计算 时间复杂度这里是数组长度m
        for (int i = 1; i < words.length; i++) {
            int[] freq = new int[26];
            // 时间复杂度这里是数组内的每个字符长度n
            for (char c : words[i].toCharArray()) {
                freq[c - 'a']++;
            }
            for (int j = 0; j < 26; j++) {
                // 选取最小那个
                minFreq[j] = Math.min(minFreq[j], freq[j]);
            }
        }
        // 走到这里就已经是结果集key是字符，value是次数
        List<String> res = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < minFreq[i]; j++) {
                res.add(String.valueOf((char) (i + 'a')));
            }
        }
        return res;
    }
}
