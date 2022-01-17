#  算法日记

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
- left标识反转的左边界**【left和right是为了反转每一个小块的】**
- right 标识反转的右边界**【left和right是为了反转每一个小块的】**
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

## [8. 字符串转换整数 (atoi)](https://leetcode-cn.com/problems/string-to-integer-atoi/)

```
请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
函数 myAtoi(string s) 的算法如下：
读入字符串并丢弃无用的前导空格
检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
如果整数数超过 32 位有符号整数范围 [−231,  231 − 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −231 的整数应该被固定为 −231 ，大于 231 − 1 的整数应该被固定为 231 − 1 。
返回整数作为最终结果。
注意：
本题中的空白字符只包括空格字符 ' ' 。
除前导空格或数字后的其余字符串外，请勿忽略 任何其他字符。
 

示例 1：
输入：s = "42"
输出：42
解释：加粗的字符串为已经读入的字符，插入符号是当前读取的字符。
第 1 步："42"（当前没有读入字符，因为没有前导空格）
         ^
第 2 步："42"（当前没有读入字符，因为这里不存在 '-' 或者 '+'）
         ^
第 3 步："42"（读入 "42"）
           ^
解析得到整数 42 。
由于 "42" 在范围 [-231, 231 - 1] 内，最终结果为 42 。

示例 2：
输入：s = "   -42"
输出：-42
解释：
第 1 步："   -42"（读入前导空格，但忽视掉）
            ^
第 2 步："   -42"（读入 '-' 字符，所以结果应该是负数）
             ^
第 3 步："   -42"（读入 "42"）
               ^
解析得到整数 -42 。
由于 "-42" 在范围 [-231, 231 - 1] 内，最终结果为 -42 。

示例 3：
输入：s = "4193 with words"
输出：4193
解释：
第 1 步："4193 with words"（当前没有读入字符，因为没有前导空格）
         ^
第 2 步："4193 with words"（当前没有读入字符，因为这里不存在 '-' 或者 '+'）
         ^
第 3 步："4193 with words"（读入 "4193"；由于下一个字符不是一个数字，所以读入停止）
             ^
解析得到整数 4193 。
由于 "4193" 在范围 [-231, 231 - 1] 内，最终结果为 4193 。

示例 4：
输入：s = "words and 987"
输出：0
解释：
第 1 步："words and 987"（当前没有读入字符，因为没有前导空格）
         ^
第 2 步："words and 987"（当前没有读入字符，因为这里不存在 '-' 或者 '+'）
         ^
第 3 步："words and 987"（由于当前字符 'w' 不是一个数字，所以读入停止）
         ^
解析得到整数 0 ，因为没有读入任何数字。
由于 0 在范围 [-231, 231 - 1] 内，最终结果为 0 。

示例 5
输入：s = "-91283472332"
输出：-2147483648
解释：
第 1 步："-91283472332"（当前没有读入字符，因为没有前导空格）
         ^
第 2 步："-91283472332"（读入 '-' 字符，所以结果应该是负数）
          ^
第 3 步："-91283472332"（读入 "91283472332"）
                     ^
解析得到整数 -91283472332 。
由于 -91283472332 小于范围 [-231, 231 - 1] 的下界，最终结果被截断为 -231 = -2147483648 。

提示：
0 <= s.length <= 200
s 由英文字母（大写和小写）、数字（0-9）、' '、'+'、'-' 和 '.' 组成
```



首先在这一题之前先思考，如何将1个字符串转换成整数？

```
"11122" → 11122
```

需要这样转换

```
2就相当于前一位是 0*10+2 = 2
21的话就相当于 2*10*1 = 21
219的话 相当于 21*10*9 = 219
也就是说每一次都相当于是【前一位*10+本身】
```

代码实现

```java
public static void main(String[] args) {
    String str = "-88";
    int i = 0; // 记录位数
    int sign = 1; // 记录正负数
    // 这里的话要看首位是不是±符号
    if (str.charAt(i) == '-' || str.charAt(i) == '+') {
        sign = str.charAt(i) == '-' ? -1 : 1;
        i++;
    }
    int base = 0; // 记录基底的 也就是第一位，第一位的最前面默认是0
    while (i < str.length()) {
        // 如果没有-'0'这个0 出现的就不是数字，而是asc码了
        base = base * 10 + (str.charAt(i) - '0');
        i++; // 下一位
    }
    System.out.println(sign * base);
}
```

然后在开始思考这一题的解法

### 思路

这一题其实条件最主要的就是3个

- 前面的空格要去掉
- 前面的正负数要确定
- 必须去掉不是数字的
- 转换成整数之后要判断好极值
  - 2147483647 `Integer.MAX_VALUE / 10 && chars[i] - '0' > 7)`
  - -2147483648 `Integer.MAX_VALUE / 10`

### 代码实现

```java
class Solution {
    public int myAtoi(String s) {
        char[] chars = s.toCharArray();
        int i = 0;

        // 丢弃空格
        while (i < s.length() && s.charAt(i) == ' ') i++;
        if (i == s.length()) return 0; // 特殊情况 全部为空
        // 丢弃+-是否存在
        int sign = 1; // 默认为+
        if (chars[i] == '-' || chars[i] == '+') {
            sign = chars[i] == '-' ? -1 : 1;
            i++;
        }
        
        // 计算结果是否溢出
        int base = 0; // 记录基底的 也就是第一位，第一位的最前面默认是0
        // 必须是数字
        while (i < chars.length && chars[i] >= '0' && chars[i] <= '9') {
            // 检查
            // 2147483647    -2147483648
            // 这一题纠结点在于在base之前判断，还是之后判断 最好是在前面判断
            // ①/10之后溢出 or ②正好等于  2147483647的情况
            if (base > Integer.MAX_VALUE / 10 || (base == Integer.MAX_VALUE / 10 && chars[i] - '0' > 7)) {
                if (sign > 0) return Integer.MAX_VALUE;
                else return Integer.MIN_VALUE;
            }
            // 如果没有-'0'这个0 出现的就不是数字，而是asc码了
            base = base * 10 + chars[i] - '0';
            i++; // 下一位
        }
        return sign * base;
    }
}
```

## [165. 比较版本号](https://leetcode-cn.com/problems/compare-version-numbers/)

```
给你两个版本号 version1 和 version2 ，请你比较它们。
版本号由一个或多个修订号组成，各修订号由一个 '.' 连接。每个修订号由 多位数字 组成，可能包含 前导零 。每个版本号至少包含一个字符。修订号从左到右编号，下标从 0 开始，最左边的修订号下标为 0 ，下一个修订号下标为 1 ，以此类推。例如，2.5.33 和 0.1 都是有效的版本号。
比较版本号时，请按从左到右的顺序依次比较它们的修订号。比较修订号时，只需比较 忽略任何前导零后的整数值 。也就是说，修订号 1 和修订号 001 相等 。如果版本号没有指定某个下标处的修订号，则该修订号视为 0 。例如，版本 1.0 小于版本 1.1 ，因为它们下标为 0 的修订号相同，而下标为 1 的修订号分别为 0 和 1 ，0 < 1 。
返回规则如下：
如果 version1 > version2 返回 1，
如果 version1 < version2 返回 -1，
除此之外返回 0。
 
示例 1：
输入：version1 = "1.01", version2 = "1.001"
输出：0
解释：忽略前导零，"01" 和 "001" 都表示相同的整数 "1"
示例 2：
输入：version1 = "1.0", version2 = "1.0.0"
输出：0
解释：version1 没有指定下标为 2 的修订号，即视为 "0"
示例 3：
输入：version1 = "0.1", version2 = "1.1"
输出：-1
解释：version1 中下标为 0 的修订号是 "0"，version2 中下标为 0 的修订号是 "1" 。0 < 1，所以 version1 < version2
示例 4：
输入：version1 = "1.0.1", version2 = "1"
输出：1
示例 5：
输入：version1 = "7.5.2.4", version2 = "7.5.3"
输出：-1
 
提示：
1 <= version1.length, version2.length <= 500
version1 和 version2 仅包含数字和 '.'
version1 和 version2 都是 有效版本号
version1 和 version2 的所有修订号都可以存储在 32 位整数 中
```

这一题有使用内置函数和不使用内置函数两种，首先说一下内置函数

### 思路

内置函数

![image-20220117131512986](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220117131512986.png)

- 先分隔成数组
- 然后每个进行对比

```java
class Solution {
    public int compareVersion(String version1, String version2) {
        String[] vArr1 = version1.split("\\."); // .需要转译
        String[] vArr2 = version2.split("\\.");
        int len1 = vArr1.length;
        int len2 = vArr2.length;
        int v1, v2;
        // 看2个数组谁更长
        for (int i = 0; i < Math.max(len1, len2); i++) {
            // 当数组长度不一致的时候，为了防止i是越界的，直接三元运算符设置为0
            v1 = i < len1 ? Integer.parseInt(vArr1[i]) : 0;
            v2 = i < len2 ? Integer.parseInt(vArr2[i]) : 0;
            if (v1 != v2) {
                return v1 > v2 ? 1 : -1;
            }
        }
        return 0;
    }
}
```

如果是不使用内置函数呢？

就需要1个个字符进行处理

![image-20220117134009565](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220117134009565.png)

- 先找到数字用v1，v2记录一下每个数组的数字
- 碰到.就进行对比，对比完之后v1，v2要清零
- 继续接下来进行对比

### 代码实现

```java
class Solution {
    public int compareVersion(String version1, String version2) {
        // 指针记录2个数组向前走
        int i1  = 0, i2 = 0;
        // i1,i2无论是谁没走到头都要向前走
        while(i1 < version1.length() || i2 < version2.length()) {
            int v1 = 0, v2 = 0; // 这里是记录每个数字是多少
            while(i1 < version1.length() && version1.charAt(i1) != '.') {
                v1 = v1 * 10 + (version1.charAt(i1) - '0');
                i1++;
            }
            while(i2 < version2.length() && version2.charAt(i2) != '.') {
                v2 = v2 * 10 + (version2.charAt(i2) - '0');
                i2++;
            }
            if (v1 != v2) {
                return v1 > v2 ? 1 : -1; 
            }
            // 别忘记向前走
            i1++;
            i2++;
        }
        return 0;
    }
}
```

## [12. 整数转罗马数字](https://leetcode-cn.com/problems/integer-to-roman/)

这一题首先要知道什么是罗马数字！！

```
罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
给你一个整数，将其转为罗马数字。
 
示例 1:
输入: num = 3
输出: "III"
示例 2:
输入: num = 4
输出: "IV"
示例 3:
输入: num = 9
输出: "IX"
示例 4:
输入: num = 58
输出: "LVIII"
解释: L = 50, V = 5, III = 3.
示例 5:
输入: num = 1994
输出: "MCMXCIV"
解释: M = 1000, CM = 900, XC = 90, IV = 4.
 

提示：
1 <= num <= 3999
```

### 思路

这题就是要知道罗马字母是怎么来的，尤其是知道4和9这俩

![image-20220117165520479](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220117165520479.png)

可以看出来只要碰到4和9就是特殊待遇

然后就是大数字一定在左边，小数字一定要右边。

![image-20220117165805744](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220117165805744.png)

所以就按照上面的规律，从后向前。

- 从后向前遍历，找到第一个小于等于自己的，减掉。
- 接下来继续向前进行遍历，找到又是第一个**小于等于目前自己**的，减掉。
- 以此类推

### 代码实现

```java
class Solution {
    public String intToRoman(int num) {
        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romans = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder res = new StringBuilder();
        int index = 0;
        // 13是数组的长度
        while (index < 13) {
             // 这里用的是while而不是if 是因为 有可能-去之后的值正好等于当前有的值 也就是nums里的值
            while (num >= nums[index]) {
                res.append(romans[index]);
                num -= nums[index];
            }
            // 这里位置一定要写对 不要写在上一层里
            index++;
        }
        return res.toString();
    }
}
```

为什么不用哈希表？ 初始化太麻烦

```java
Map<Integer,String> map = new HashMap<>();
map.put(1,"I");
map.put(4,"IV");
map.put(5,"V");
map.put(9,"IX");
map.put(10,"X");
map.put(40,"XL");
map.put(50,"L");
map.put(90,"XC");
map.put(100,"C");
map.put(400,"CD");
map.put(500,"D");
map.put(900,"CM");
map.put(1000,"M");
// or
Map<Integer,String> map = new HashMap<>(){{
  put(1,"I");
  put(4,"IV");
  put(5,"V");
  put(9,"IX");
  put(10,"X");
  put(40,"XL");
  put(50,"L");
  put(90,"XC");
  put(100,"C");
  put(400,"CD");
  put(500,"D");
  put(900,"CM");
  put(1000,"M");
}}
```

## [13. 罗马数字转整数](https://leetcode-cn.com/problems/roman-to-integer/)

```
罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
例如， 罗马数字 2 写做 II ，即为两个并列的 1 。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
给定一个罗马数字，将其转换成整数。

示例 1:
输入: s = "III"
输出: 3
示例 2:
输入: s = "IV"
输出: 4
示例 3:
输入: s = "IX"
输出: 9
示例 4:
输入: s = "LVIII"
输出: 58
解释: L = 50, V= 5, III = 3.
示例 5:
输入: s = "MCMXCIV"
输出: 1994
解释: M = 1000, CM = 900, XC = 90, IV = 4.
 

提示：
1 <= s.length <= 15
s 仅含字符 ('I', 'V', 'X', 'L', 'C', 'D', 'M')
题目数据保证 s 是一个有效的罗马数字，且表示整数在范围 [1, 3999] 内
题目所给测试用例皆符合罗马数字书写规则，不会出现跨位等情况。
IL 和 IM 这样的例子并不符合题目要求，49 应该写作 XLIX，999 应该写作 CMXCIX 。
关于罗马数字的详尽书写规则，可以参考 罗马数字 - Mathematics 。
```

### 思路

- 设置指针，每2个进行比较
- 左边大于右边，累加
- 右边大于左边，说明是有情况（4,9系列）减掉！
- 注意就是pre和cur，直到cur走完之后！

![image-20220117173246529](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220117173246529.png)

- 注意大小的判断时候`if (pre >= cur)` 这里一定要有等号的
- 俩俩向前走的写法` pre = cur`
- 注意最后不能忘记还会缺少个 `res += pre`

### 代码实现

```java
class Solution {
    public int romanToInt(String s) {
        int res = 0;
        int pre = getNum(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            int cur = getNum(s.charAt(i));
            // 这里一定是要大于等于，而不能是大于 因为等于的情况下也是要累加的
            if (pre >= cur) {
                res += pre;
            } else {
                res -= pre;
            }
            pre = cur; // 这里不能忘记每2个向前走
        }
        res += pre; // 因为cur是判断走完整个字符串的标准，所以cur走到头之后还会剩下pre
        return res;
    }

    private int getNum(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }
}
```

## [38. 外观数列](https://leetcode-cn.com/problems/count-and-say/)

```
给定一个正整数 n ，输出外观数列的第 n 项。
「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。
你可以将其视作是由递归公式定义的数字字符串序列：
countAndSay(1) = "1"
countAndSay(n) 是对 countAndSay(n-1) 的描述，然后转换成另一个数字字符串。
前五项如下：

1.     1
2.     11
3.     21
4.     1211
5.     111221
第一项是数字 1 
描述前一项，这个数是 1 即 “ 一 个 1 ”，记作 "11"
描述前一项，这个数是 11 即 “ 二 个 1 ” ，记作 "21"
描述前一项，这个数是 21 即 “ 一 个 2 + 一 个 1 ” ，记作 "1211"
描述前一项，这个数是 1211 即 “ 一 个 1 + 一 个 2 + 二 个 1 ” ，记作 "111221"
要 描述 一个数字字符串，首先要将字符串分割为 最小 数量的组，每个组都由连续的最多 相同字符 组成。然后对于每个组，先描述字符的数量，然后描述字符，形成一个描述组。要将描述转换为数字字符串，先将每组中的字符数量用数字替换，再将所有描述组连接起来。

示例 1：
输入：n = 1
输出："1"
解释：这是一个基本样例。
示例 2：
输入：n = 4
输出："1211"
解释：
countAndSay(1) = "1"
countAndSay(2) = 读 "1" = 一 个 1 = "11"
countAndSay(3) = 读 "11" = 二 个 1 = "21"
countAndSay(4) = 读 "21" = 一 个 2 + 一 个 1 = "12" + "11" = "1211"

提示：
1 <= n <= 30
```

### 思路

这一题就是直接模拟

说实话就是，直接模拟的思路就是按照你的想法写出代码。除了多写之外并无其他用法。

![image-20220117175304335](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220117175304335.png)

- 其实就是记录一个pre，一个cur
- 每次都遍历pre，然后统计。统计的结果添加到cur
  - 遍历这里要分2种情况 情况1是等于pre的值 那么就直接增加次数
  - 如果不等于的话，就先添加到cur，然后在走向下一个，同时count要为1
- 最后cur转换成字符串就可以

还是看代码实现比较科学。

这个代码实现是这样开始的

![image-20220117234548258](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220117234548258.png)

### 代码实现

```java
class Solution {
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
```

上面的代码实现，我以前一直不懂这里可以直接new吗？cur这样不就改变了吗？

```java
pre = cur; // ∵在new之前，cur已经赋值给了pre，其实这里主要的遍历对象就是pre了
cur = new StringBuilder(); 
```

