#  算法日记

数学开始咯！！字符串的主要题目有

- 数学直接模拟
- 基本运算 加减乘除
- 数字转换，反转
- 数学性质，质数，曲线，直线

## [7. 整数反转](https://leetcode-cn.com/problems/reverse-integer/)

```
给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
假设环境不允许存储 64 位整数（有符号或无符号）。
 
示例 1：
输入：x = 123
输出：321
示例 2：
输入：x = -123
输出：-321
示例 3：
输入：x = 120
输出：21
示例 4：
输入：x = 0
输出：0
 
提示：
-231 <= x <= 231 - 1
```

这一题最大的难点就在于整数溢出问题，如果直接利用数字→字符串，字符串拼接反转，就会发生整数溢出的问题。

### 思路

所以思路就改变从后向前开始

先说一下**①字符串拼接的思路（会溢出）**

- 数字→字符串 int → string
- 字符串→字符串数组 string → charArray
- 检查±不然向前走
- 利用数组反转字符串
- 溢出

说下②数学方法解决

![image-20220118141629341](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220118141629341.png)

![image-20220118141816877](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220118141816877.png)

最后一个个直到x为0【也就是x取余之后是0，商0】，就能翻转过来了！

### 代码实现

字符串拼接（会溢出）

```java
// ∵使用了64了
public int reverseNo(int x) {
    String xStr = String.valueOf(x);
    char[] xChars = xStr.toCharArray();
    int left = 0, right = xChars.length - 1;
    if (xChars[left] < '0' || xChars[left] > '9') left++;
    while (left < right) {
        char temp = xChars[left];
        xChars[left] = xChars[right];
        xChars[right] = temp;
        left++;
        right--;
    }
    // 走到这里应该已经获得了翻转的
    // 因为有可能要溢出，所以要先long起来 int支持到32 long支持到64
    // 题目要求不能使用64 所以以下都是不可行的
    long res = Long.parseLong(new String(xChars));
    if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) return 0;
    // 强转成int
    return (int) res;
}
```

溢出之前判断

```java
// 溢出之前判断
public int reverse1(int x) {
    int res = 0;
    while (x != 0) {
        int pop = x % 10;
        x = x / 10;
        int newRes = res * 10 + pop;
        // 判断是否溢出 新res退回如果是旧res，说明没有
        // 因为无论上溢出，下溢出 都得不到跟原来一样的数字
        if ((newRes - pop) / 10 != res) return 0;
        res = newRes;
    }
    return res;
}
```

溢出之后判断

```java
// 溢出之后判断
public int reverse2(int x) {
    int res = 0; // 结果
    while (x != 0) { // 只要不为0 就一直取余
        int pop = x % 10; // 取模求个位数
        x = x / 10;
        // MAX_VALUE = 2^31 - 1 = 2147483647
        if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
        // MIN_VALUE = -2^31 = -2147483648
        if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
        res = res * 10 + pop; // 反转得到数字
    }
    return res;
}
```

long强制转换成int之后是否丢失来判断

```java
// 评论区看到的
public int reverse3(int x) {
    long res = 0;
    while (x != 0) {
        // 弹出的 个位数 = x % 10;
        res = res * 10 + x % 10;
        x = x / 10;
    }
    // 强行转换成int之后看看是否相等
    return (int) res == res ? (int) res : 0;
}
```

## [9. 回文数](https://leetcode-cn.com/problems/palindrome-number/)

```
给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。例如，121 是回文，而 123 不是。

示例 1：
输入：x = 121
输出：true
示例 2：
输入：x = -121
输出：false
解释：从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
示例 3：
输入：x = 10
输出：false
解释：从右向左读, 为 01 。因此它不是一个回文数。
示例 4：
输入：x = -101
输出：false

提示：
-231 <= x <= 231 - 1

进阶：你能不将整数转为字符串来解决这个问题吗？
```

### 思路

我的思路就是从前向后遍历，从后向前遍历都是一样的。这样对撞指针就可以了，从前到后1个个对比，不一样就退出。

```java
public boolean isPalindrome2(int x) {
    // 0的时候肯定是ok
    if (x == 0) return true;

    // 如果是负数 或者个位数是0也不行
    if (x < 0 || x % 10 == 0) return false;
    int res = 0;
    int xx = x; // ∵x在下面会被不停的修改 所以需要暂存一下
    while (x != 0) {
        int pop = x % 10;
        x = x / 10;
        // 反转整数的时候，可能出现溢出
        // MAX_VALUE = 2^31 - 1 = 2147483647
        if (res > Integer.MAX_VALUE / 10
                || (res == Integer.MAX_VALUE / 10 && pop > 7)) return false;
        // MIN_VALUE = -2^31 = -2147483648
        if (res < Integer.MIN_VALUE / 10
                || (res == Integer.MIN_VALUE / 10 && pop < -8)) return false;
        res = res * 10 + pop;
    }
    return xx == res; // 为什么用xx?答案在上面
}
```

下面也是一个整数转换成字符串的方法，用的是对撞指针

```java
// int → string
public boolean isPalindrome1(int x) {
    String xStr = String.valueOf(x);
    int left = 0, right = xStr.length() - 1;
    while (left < right) {
        if (xStr.charAt(left) != xStr.charAt(right)) return false;
        left++;
        right--;
    }
    return true;
}
```

最后这个无需转换的思路用的是

![image-20220118183230402](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220118183230402.png)

上面是偶数，那么奇数呢？

其实意思就是奇数的时候，最后一个无所谓，去掉就行。

```
当数字长度为奇数时，我们可以通过 res/10 去除处于个位的数字。
例如，当输入为 12321 时，在 while 循环的末尾我们可以得到 x = 12，res = 123，
由于处于中位的数字不影响回文（它总是与自己相等），所以我们可以简单地将其去除。
return res == x || x == res / 10;
```

### 代码实现

```java
public boolean isPalindrome3(int x) {
    // 0的时候肯定是ok
    if (x == 0) return true;
    // 如果是负数 或者个位数是0也不行
    if (x < 0 || x % 10 == 0) return false;
    int res = 0;
    // 这里为什么是res小于x
    // 因为按照数字大小比3位数肯定比2位数大，只要res比x小，说明一直没有比到一半
    // 等于(偶数)or大于(奇数) 都说明已经比完了
    while (res < x) {
        res = res * 10 + (x % 10);
        x = x / 10;
    }
    // 偶数的情况下相等，奇数的情况下去掉最后最后1位个位
    return res == x || x == res / 10;
}
```

代码要注意的地方

- res < x 终止条件是只要大于x就终止
- ` res == x || x == res / 10;` 这里对比的是res去掉最后1位，而不是x哦
