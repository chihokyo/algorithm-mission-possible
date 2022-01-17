package com.chin._04._38;

public class _38_count_and_say {
    public String countAndSay(int n) {
        StringBuilder cur = new StringBuilder("1"); // 初始化cur就是1 这里是题目已知的
        StringBuilder pre; // 初始化pre拼接字符串用来统一上一个
        // 遍历整个n，需要n次 不能取到i=n
        for (int i = 1; i < n; i++) {
            pre = cur; // 从第2个开始
            cur = new StringBuilder(); // 这里是用来统计每一行的
            char say = pre.charAt(0); // 第1个字符 也是要对比的字符
            int count = 1; // 次数1 ∵已经是有1个字符了
            // 这里是遍历上一行
            for (int j = 1; j < pre.length(); j++) {
                // 相等的话 就添加次数
                if (pre.charAt(j) == say) {
                    count++;
                } else {
                    // 不等的话就拼接字符串 → 向前走 → 重置次数为1
                    cur.append(count).append(say);
                    say = pre.charAt(j);
                    count = 1;
                }
            }
            // 这里不能忘记最后1次
            cur.append(count).append(say);
        }
        return cur.toString();
    }
}
