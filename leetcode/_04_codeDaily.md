# 算法日记

字符串开始咯！！字符串的主要题目有

- 字符串相关直接模拟算法
- 字符串匹配相关
- 字符串模拟相关
- 字符串数字转换相关

## [28. 实现 strStr()](https://leetcode-cn.com/problems/implement-strstr/)

```
实现 strStr() 函数。
给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串出现的第一个位置（下标从 0 开始）。如果不存在，则返回  -1 。

说明：
当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。
对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与 C 语言的 strstr() 以及 Java 的 indexOf() 定义相符。

示例 1：
输入：haystack = "hello", needle = "ll"
输出：2
示例 2：
输入：haystack = "aaaaa", needle = "bba"
输出：-1
示例 3：
输入：haystack = "", needle = ""
输出：0

提示：
0 <= haystack.length, needle.length <= 5 * 104
haystack 和 needle 仅由小写英文字符组成
```

这里的本质就是**字符串的匹配**

就是 模式串 和 主串 的匹配。其实字符串匹配有很多

[字符串匹配方法解说课程](https://ke.qq.com/webcourse/index.html#cid=3323953&term_id=103455149&taid=11041162625464369&vid=5285890814362857970)

```java
// 暴力1 时间O(mn) 空间O(1)
class Solution {
    public int strStr(String haystack, String needle) {
        int n = needle.length();
        if (n == 0)  return 0;
        char first = needle.charAt(0);
        // 字符串 - 模式串 + 1 ，只要遍历到-模式串长度大小那里就可以
        // 这里肯定是取不到的 所以 +1 了
        for (int i = 0; i < haystack.length() - n + 1; i++){
            // 条件1 第一个字符相等
            // 条件2 之后的字符串也相等 左闭右开
            if (haystack.charAt(i) == first && haystack.substring(i, i + n).equals(needle)) {
                return i;
            } 
        }
        return -1;
    }
}

// 暴力2 时间O(mn) 空间O(1)
class Solution {
    public int strStr(String haystack, String needle) {
        // 判定空
        
        int m = haystack.length();
        int n = needle.length();
        if (n == 0) return 0;
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
}
```

## [459. 重复的子字符串](https://leetcode-cn.com/problems/repeated-substring-pattern/)

```
给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。

示例 1:
输入: "abab"
输出: True
解释: 可由子字符串 "ab" 重复两次构成。
示例 2:
输入: "aba"
输出: False
示例 3:
输入: "abcabcabcabc"
输出: True

解释: 可由子字符串 "abc" 重复四次构成。 (或者子字符串 "abcabc" 重复两次构成。)
```

### 思路

这一题的思路吧，就是这样的，使用长度 + 双指针 ，也就是**双指针模拟**。

长度为1的时候是否一样，长度为2的时候是否一样。。。就这样连接下去。

![2021-12-16 23-56-56.2021-12-16 23_57_22](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-12-16%2023-56-56.2021-12-16%2023_57_22.gif)

双指针模拟

### 代码实现

```java
// 这个写法并没有通过  是错误的！！！
// 必须要通过n是len的倍数才可以！！实现
public boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        for (int len = 1; len < n; len++) {
            boolean matched = true; // 标记是否匹配
            int i = 0;
            for (int j = len; j < n; j++) {
                if (s.charAt(len) != s.charAt(j)) {
                    matched = false; // 不匹配
                    i++;
                    break;
                }
            }
            if (matched) return true; // 匹配上了
        }
        return false;
    }
```

但其实这一题是有优化的，比如下面的2点实现

![image-20211217001012214](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211217001012214.png)

代码实现

```java
class Solution {
    // 双指针模拟
    public boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        for (int len = 1; len * 2 <= n; len++) {
            // 因为n必定是len的倍数
            if (n % len == 0) {
                boolean matched = true; // 标记是否匹配
                int i = 0; // 初始化i
                for (int j = len; j < n; j++, i++) {
                    if (s.charAt(i) != s.charAt(j)) {
                        matched = false; // 不匹配
                        break;
                    }
                }
                if (matched) return true; // 匹配上了
            }
        }
        return false;
    }
}
```

### 思路2 旋转数组

大概就是说以分界线为轴心

![2021-12-17 16-24-25.2021-12-17 16_25_00](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-12-17%2016-24-25.2021-12-17%2016_25_00.gif)

下面只是一个思路，需要优化

```java
// 旋转1 超出时间限制
public boolean repeatedSubstringPattern2(String s) {
  // 从len为1开始尝试进行旋转
  for (int len = 1; len * 2 <= s.length(); len++) {
    // 如果旋转之后得到的字符串和现在相同
    if (s.equals(rotate(s.toCharArray(), len))) {
      return true;
    }
  }
  return false;
}


// 反转
public String rotate(char[] chars, int k) {
  int n = chars.length;
  k = k % n;
  reserve(chars, 0, n - 1);
  reserve(chars, 0, k - 1);
  reserve(chars, k, n - 1);
  return String.valueOf(chars);
}

// 翻转
private void reserve(char[] chars, int start, int end) {
  while (start < end) {
    char temp = chars[start];
    chars[start] = chars[end];
    chars[end] = temp;
    start++;
    end--;
  }
}
```

到底怎么优化呢？

主要是降低旋转的次数。那怎么降低呢？那就是把现有的字符串后面再添加一个字符串！

通过这个图可以看出来，acd复制1个在末尾，然后**从后向前就可以不用旋转也能得到相应的字符串**。

![image-20211217180602874](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211217180602874.png)

然后就这样拼接，比如下面这个例子。

![image-20211217180942127](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211217180942127.png)

```
字符串 s 拼接的目的：可以在 s + s 中找到 s 的所有旋转后的字符串
拼接后的字符串的头部是旋转了一圈的字符串，而尾部是没有旋转的字符串，所以需要去掉头部和尾部
从 1 开始匹配的作用就是去掉头部
不等于 s.length 的作用就是去掉尾部
```

```java
class Solution {
    // 字符串匹配法
    public boolean repeatedSubstringPattern(String s) {
        return (s + s).indexOf(s, 1) != s.length();
    }

    // 反转
    public String rotate(char[] chars, int k) {
        int n = chars.length;
        k = k % n;
        reserve(chars, 0, n - 1);
        reserve(chars, 0, k - 1);
        reserve(chars, k, n - 1);
        return String.valueOf(chars);
    }

    // 翻转
    private void reserve(char[] chars, int start, int end) {
        while (start < end) {
            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;
            start++;
            end--;
        }
    }
}
```

