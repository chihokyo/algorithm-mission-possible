package com.chin._04._165;

public class _165_compare_version_numbers {
    // 使用内置函数
    public int compareVersion(String version1, String version2) {
        String[] vArr1 = version1.split("\\."); // .需要转译
        String[] vArr2 = version2.split("\\."); // .需要转译
        int len1 = vArr1.length;
        int len2 = vArr2.length;

        int v1, v2;
        for (int i = 0; i < Math.max(len1, len2); i++) {
            // 这里为什么需要三元运算符对比呢 因为如果当数组1和数组2长度不相等的时候
            // 必定有一个是取不到值的，越界的，这个时候设置为0就行
            v1 = i < len1 ? Integer.parseInt(vArr1[i]) : 0;
            v2 = i < len2 ? Integer.parseInt(vArr2[i]) : 0;
            if (v1 != v2) {
                return v1 > v2 ? 1 : -1;
            }
        }
        return 0;
    }

    // 不使用内置函数
    public int compareVersion1(String version1, String version2) {
        int i1 = 0, i2 = 0; // 2个数组的指针，从0开始向前走
        while (i1 < version1.length() || i2 < version2.length()) {
            int v1 = 0, v2 = 0; // 这里是记录每个点数
            while (i1 < version1.length() && version1.charAt(i1) != '.') {
                v1 = v1 * 10 + (version1.charAt(i1) - '0');
                i1++;
            }
            while (i2 < version2.length() && version2.charAt(i2) != '.') {
                v2 = v2 * 10 + (version2.charAt(i2) - '0');
                i2++;
            }
            if (v1 != v2) {
                return v1 > v2 ? 1 : -1;
            }
            i1++;
            i2++;
        }
        return 0;
    }
}
