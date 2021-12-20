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

## [344. 反转字符串](https://leetcode-cn.com/problems/reverse-string/)

```
编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。

示例 1：
输入：s = ["h","e","l","l","o"]
输出：["o","l","l","e","h"]
示例 2：
输入：s = ["H","a","n","n","a","h"]
输出：["h","a","n","n","a","H"]
 
提示：
1 <= s.length <= 105
s[i] 都是 ASCII 码表中的可打印字符
```

这一题其实早就做过了 ，详情看day1

## [345. 反转字符串中的元音字母](https://leetcode-cn.com/problems/reverse-vowels-of-a-string/)

```
给你一个字符串 s ，仅反转字符串中的所有元音字母，并返回结果字符串。
元音字母包括 'a'、'e'、'i'、'o'、'u'，且可能以大小写两种形式出现。

示例 1：
输入：s = "hello"
输出："holle"
示例 2：
输入：s = "leetcode"
输出："leotcede"

提示：
1 <= s.length <= 3 * 105
s 由 可打印的 ASCII 字符组成
```

这一题，注意大小写都可以。

### 思路

![2021-12-18 00-12-50.2021-12-18 00_13_22](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-12-18%2000-12-50.2021-12-18%2000_13_22.gif)

- 这一题其实就是从前开始看，从后开始看，遇到元音就交换。
- 比如，找到第1个元音字母和最后1个元音字母交换。
- 比如，找到第2个元音字母和倒数第2个元音字母交换。
- ....以此类推

### 代码实现

双指针

```java
// for
class Solution {
    public String reverseVowels(String s) {
       // 字符串 → 字符数组
        char[] chars = s.toCharArray();
        int n = s.length();
        // 双指针
        int left = 0, right = n - 1;
        for (; left < right; left++, right--) {
            for (;left < right && !checkVowel(chars[left]);left++){}
            for (;left < right && !checkVowel(chars[right]);right--){}
            // 判断不是原因之后才向前
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
        }
         return String.valueOf(chars);
    }
    // 判断是否为元音
    private boolean checkVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'
                || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }
}

// while
class Solution {
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
    // 判断是否为元音
    private boolean checkVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'
                || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }
}
```

## 1119 删除字符串中所有的元音字母

![image-20211220161335772](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211220161335772.png)

这一题需要开会员才能写，但是很简单，就随便实现一下了。

```java
public class _1119_remove_vowels_from_a_string {
    public String removeVowels(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (!isVowel(c)) sb.append(c);
        }
        return sb.toString();
    }

    private boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}
```

## [541. 反转字符串 II](https://leetcode-cn.com/problems/reverse-string-ii/)

```
给定一个字符串 s 和一个整数 k，从字符串开头算起，每计数至 2k 个字符，就反转这 2k 字符中的前 k 个字符。
如果剩余字符少于 k 个，则将剩余字符全部反转。
如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。
 
示例 1：
输入：s = "abcdefg", k = 2
输出："bacdfeg"
示例 2：
输入：s = "abcd", k = 2
输出："bacd"

提示：
1 <= s.length <= 104
s 仅由小写英文组成
1 <= k <= 104
```

这一题看这个图片应该就是有思路的

### 思路

![2021-12-20 16-21-21.2021-12-20 16_21_32](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-12-20%2016-21-21.2021-12-20%2016_21_32.gif)

就是每2k，然后反转前k个的意思

这个时候需要3个指针进行遍历

- start 标识每一个k的起始点
- left标识反转的左边界
- right 标识反转的右边界
- 然后进行遍历

![2021-12-20 16-22-12.2021-12-20 16_22_33](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-12-20%2016-22-12.2021-12-20%2016_22_33.gif)

这一题主要的问题是如果start已经超过字符串长度的时候left和right在哪里，该如何反转的问题

```java
int left = start;
int right = Math(left + k - 1, s.length() - 1); // 取最小的那个
// 例如上面那个动图，start已经在最后一个u前面了
// 这个时候right肯定超过了字符串的最大长度，所以也只能取u的位置
```

![image-20211220163130625](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211220163130625.png)

所以代码很容易就写了

### 代码实现

```java
class Solution {
    public String reverseStr(String s, int k) {
        char[] strArr = s.toCharArray();
        // 每次增加k的长度
        for (int start = 0; start < s.length(); start += k * 2) {
            int left = start;
            // 右边界要取最小的那边
            int right = Math.min(left + k - 1, s.length() - 1);
            while (left < right) {
                char temp = strArr[left];
                strArr[left] = strArr[right];
                strArr[right] = temp;
                left++;
                right--;
            }
        }
        return new String(strArr);
    }
}
```

## [557. 反转字符串中的单词 III](https://leetcode-cn.com/problems/reverse-words-in-a-string-iii/)

```
给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。

示例：
输入："Let's take LeetCode contest"
输出："s'teL ekat edoCteeL tsetnoc"

提示：
在字符串中，每个单词由单个空格分隔，并且字符串中不会有任何额外的空格。
```

思路的话很清晰

### 思路

![image-20211220165843381](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211220165843381.png)

```java
// 写在里面
class Solution {
    public String reverseWords(String s) {
        char[] chars = s.toCharArray();
        int n = s.length();
        int left = 0;
        while (left < n) {
            if (chars[left] != ' ') {
                int right = left;
                while(right + 1 < n && chars[right + 1] != ' ') right++;
                // 就是这里！！这里需要在进行定义左右指针
                // 保证在赋值的时候left和right保持独立
                int start = left;
                int end = right;
                while (start < end) {
                    char temp = chars[start];
                    chars[start++] = chars[end];
                    chars[end--] = temp;
                }

                left = right + 1;
            } else {
                left++;
            }
        }
        return new String(chars);
    }
}

// 写在外面
class Solution {
    public String reverseWords(String s) {
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

    private void reserve(char[] chars, int start, int end) {
        char temp;
        while (start < end) {
            temp = chars[start];
            chars[start++] = chars[end];
            chars[end--] = temp;
        }
    }

}
```

这一题在写代码的时候遇到一个很难的问题，就是反转函数写在里面还是外面的问题。

这个问题涉及 值传递，引用传递，这个问题不搞清楚就很难难。。。

## [58. 最后一个单词的长度](https://leetcode-cn.com/problems/length-of-last-word/)

```
给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中最后一个单词的长度。
单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。

示例 1：
输入：s = "Hello World"
输出：5
示例 2：
输入：s = "   fly me   to   the moon  "
输出：4
示例 3：
输入：s = "luffy is still joyboy"
输出：6

提示：
1 <= s.length <= 104
s 仅有英文字母和空格 ' ' 组成
s 中至少存在一个单词
```

### 思路

这一题有2种解法，从左向右，从右向左。

从左向右，一看就不是最优解！！都求最后一个单词的长度了，怎么可能是从左向右。

- 从最后一个开始遍历，遇到不为空就记住 变量1
- 然后定义另一个，然后开始找，遇到不为空就记住 变量2
- 变量2 - 变量1 就是结果

### 代码实现

```java
// 从右到左
class Solution {
    public int lengthOfLastWord(String s) {
        // 指针1 指向最后一个
        int end = s.length() - 1;
        while (end >= 0 && s.charAt(end) == ' ') end--;
        if (end < 0)  return 0;
        // 到这里了就应该得到了第一个不为空的指针所处的位置
        int start = end; // 然后赋值给第2个指针
        while (start >= 0 && s.charAt(start) != ' ') start--;
        // 前指针 - 后指针
        return end - start;
    }
}

// 从左到右
class Solution {
    public int lengthOfLastWord(String s) {
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
```

