package com.chin._04._28;

public class _28_implement_strstr {
    // BM 暴力1
    // 时间复杂度O(mn)
    // 空间复杂度O(1)
    public int strStrBM1(String haystack, String needle) {
        // 判定空
        if (haystack == null || needle == null) return -1;
        int m = haystack.length();
        int n = needle.length();
        // 【模式串】 大于 字符串 那肯定没有
        if (n > m) return -1;
        // 1. 从i开始匹配后面的字符串，如果相等，就一直匹配
        for (int i = 0; i < m - n + 1; i++) {
            // 2.这里k在每次开始的时候要一直等于i
            int k = i;
            // 3.这里开始遍历【模式串】
            for (int j = 0; j < n; j++) {
                // 如果当前k所在的字符 == j所在的模式串字符
                if (haystack.charAt(k) == needle.charAt(j)) {
                    // 这里需要k++，原理其实也需要j++，但是在for里写了
                    k++;
                    // 4.如果此时j已经到了最后一个字符，说明找到了，直接返回
                    if (j == n - 1) return i;
                } else {
                    // 否则就是break
                    // break既能跳出当前循环，还可以让i++，因为for里有i++
                    break;
                }
            }
        }
        return -1;
    }

    // BM 暴力2
    // 时间复杂度O(mn)
    // 空间复杂度O(1)
    public int strStrBM2(String haystack, String needle) {
        if (haystack == null || needle == null) return -1;
        int m = haystack.length();
        int n = needle.length();
        if (n > m) return -1;
        char first = needle.charAt(0);
        for (int i = 0; i < m; i++) {
            if (haystack.charAt(i) != first) {
                // while (i < m && haystack.charAt(i) != first) i++;
                while (++i < m && haystack.charAt(i) != first) ;
            }
            // 走到这里其实就找到了第一个i
            // 这里一定要判断是否是超过了m，因为可以遍历到最后都没有
            if (i < m) {
                // 这里要从首字母的下一个，也就是第2个开始
                // 这样写也行
                // int j = 1;
                // for (; j < n && k < m; j++, k++)
                int k = i + 1;
                // j也要从第2个开始
                for (int j = 1; j < n && k < m; j++, k++) {
                    if (needle.charAt(j) == haystack.charAt(k)) {
                        // 如果j是模式化字符串最后1个的话，就说明匹配到了
                        if (j == n - 1) return i;
                        continue;
                    }
                }
            }

        }
        return -1;
    }

    // RK算法1
    // 时间复杂度O( (m-n) * n  ) 因为hashCode这个算法是遍历
    // 空间复杂度O(m-n)
    public int strStrRK1(String haystack, String needle) {
        if (haystack == null || needle == null) return -1;
        int m = haystack.length();
        int n = needle.length();
        if (n > m) return -1;
        // 1 计算主串中m-n+1个子串的哈希值
        int[] hashCodes = new int[m - n + 1];
        // 2 计算模式串的哈希值
        for (int i = 0; i < m - n + 1; i++) {
            hashCodes[i] = calHashCode1(haystack.substring(i, i + n));
        }
        // 3 寻找所有哈希值看看有没有重复
        int hashCode = calHashCode1(needle);
        for (int i = 0; i < hashCodes.length; i++) {
            if (hashCode == hashCodes[i]) return i;
        }

        return -1;
    }

    // 获取字符串hash值
    private int calHashCode1(String str) {
        return str.hashCode(); // O(n)
    }

    // RK算法2
    // 时间复杂度O(m-n) 因为hashCode是原创的
    // 空间复杂度O(m-n)
    public int strStrRK2(String haystack, String needle) {
        if (haystack == null || needle == null) return -1;
        int m = haystack.length();
        int n = needle.length();
        if (n > m) return -1;
        // 1 计算主串中m-n+1个子串的哈希值
        int[] hashCodes = new int[m - n + 1];
        // 1-1 额外计算第1个
        hashCodes[0] = calFirstHashCode(haystack.substring(0, n));
        // 2 计算模式串的哈希值
        for (int i = 1; i < m - n + 1; i++) { // O(m-n)
            hashCodes[i] = calHashCode2(haystack, i, hashCodes, n);
        }
        // 3 寻找所有哈希值看看有没有重复(因为只有1个)
        int hashCode = calFirstHashCode(needle);
        for (int i = 0; i < m - n + 1; i++) { // O(m-n)
            if (hashCode == hashCodes[i]) {
                // 解决哈希冲突
                // 在这里+上可以解决哈希冲突
                // 设置1个k用来遍历2个字符串
                int k = i;
                for (int j = 0; j < n; j++) {
                    // 如果只要不相等就退出
                    if (haystack.charAt(k) != needle.charAt(j)) {
                        break;
                    }
                    // 否则k向前走
                    k++;
                    if (j == n - 1) return i;
                }
            }
        }

        return -1;
    }

    // 原创获取字符串hash值 O(1)
    private int calHashCode2(String str, int i, int[] hashCodes, int n) {
        return hashCodes[i - 1] - (str.charAt(i - 1) - 'a')
                + (str.charAt(i + n - 1) - 'a');
    }

    // 额外计算第一个 O(n)
    private int calFirstHashCode(String str) {
        int len = str.length();
        int hashCode = 0;
        for (int i = 0; i < len; i++) {
            hashCode += (str.charAt(len - i - 1) - 'a');
        }
        return hashCode;
    }


    // 时间复杂度：O(m - n)
    // 空间复杂度：O(m - n)
    public int strStrRK3(String mainStr, String pattern) {
        if (mainStr == null || pattern == null) return -1;

        int m = mainStr.length();
        int n = pattern.length();
        if (m < n) return -1;

        // 1. 计算主串中 m - n + 1 个子串的哈希值
        int[] hashCodes = new int[m - n + 1];
        // 计算第一个子串的 hash 值
        hashCodes[0] = calFirstSubStrHashCode(mainStr.substring(0, n));
        for (int i = 1; i < m - n + 1; i++) {
            // 根据前一个子串的 hash 值计算下一个子串的 hash 值
            hashCodes[i] = calHashCode(mainStr, i, hashCodes, n);
        }

        // 2. 计算模式串的哈希值
        int hashCode = calFirstSubStrHashCode(pattern);

        // 3. 在所有子串哈希值中，寻找是否有模式串的哈希值
        for (int i = 0; i < m - n + 1; i++) {
            if (hashCode == hashCodes[i]) {
                return i;
            }
        }

        return -1;
    }

    private int calHashCode(String mainStr, int i, int[] hashCodes, int n) {
        return hashCodes[i - 1] * 26 - (mainStr.charAt(i - 1) - 'a') * (int) Math.pow(26, n)
                + (mainStr.charAt(i + n - 1) - 'a');
    }

    // abc => 0 * 26^2 + 1 * 26 + 2 = 28
    private int calFirstSubStrHashCode(String str) { // O(n)
        int n = str.length();

        int hashCode = 0;
        for (int i = 0; i < n; i++) {
            hashCode += (int) Math.pow(26, i) * (str.charAt(n - i - 1) - 'a');
        }

        return hashCode;
    }

    public static void main(String[] args) {
        _28_implement_strstr demo = new _28_implement_strstr();
        String mainS = "iloveyou";
        String patternS = "ve";
        System.out.println(demo.strStrBM1(mainS, patternS));
        System.out.println(demo.strStrBM2(mainS, patternS));
        System.out.println(demo.strStrRK1(mainS, patternS));
        System.out.println(demo.strStrRK2(mainS, patternS));
        System.out.println(demo.strStrRK3(mainS, patternS));
    }
}
