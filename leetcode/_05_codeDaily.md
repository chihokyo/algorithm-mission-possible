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

## [989. 数组形式的整数加法](https://leetcode-cn.com/problems/add-to-array-form-of-integer/)

```
对于非负整数 X 而言，X 的数组形式是每位数字按从左到右的顺序形成的数组。例如，如果 X = 1231，那么其数组形式为 [1,2,3,1]。
给定非负整数 X 的数组形式 A，返回整数 X+K 的数组形式。

示例 1：
输入：A = [1,2,0,0], K = 34
输出：[1,2,3,4]
解释：1200 + 34 = 1234
示例 2：
输入：A = [2,7,4], K = 181
输出：[4,5,5]
解释：274 + 181 = 455
示例 3：
输入：A = [2,1,5], K = 806
输出：[1,0,2,1]
解释：215 + 806 = 1021
示例 4：
输入：A = [9,9,9,9,9,9,9,9,9,9], K = 1
输出：[1,0,0,0,0,0,0,0,0,0,0]
解释：9999999999 + 1 = 10000000000

提示：
1 <= A.length <= 10000
0 <= A[i] <= 9
0 <= K <= 10000
如果 A.length > 1，那么 A[0] != 0
```

### 思路

首先先做一个2个整数数组的加法

```java
public List<Integer> addTwoNum(int[] nums1, int[] nums2) {
    List<Integer> res = new ArrayList<>();
    int len1 = nums1.length - 1;
    int len2 = nums2.length - 1;
    int carry = 0; // 默认为0
    while (len1 >= 0 || len2 >= 0) {
        int x = len1 < 0 ? 0 : nums1[len1];
        int y = len2 < 0 ? 0 : nums2[len2];
        int sum = x + y + carry;
        // sum % 10 相当于要最后个位
        res.add(sum % 10);
        carry = sum / 10; // 取出来进位
        len1--;
        len2--;
    }
    // 要先做判断不能为0，不然反转会回来会多个0
    // 不能忘记添加最后1个进位 比如 222 + 899 = (1)121
    if (carry != 0) res.add(carry);
    Collections.reverse(res); // 添加是从前面添加的
    return res;
}
```

![image-20220120222334960](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220120222334960.png)

不同点就是

- 结束条件不同
- k取模 获取最后1位
- k取商 去掉最后1位

### 代码实现

```java
public List<Integer> addToArrayForm(int[] num, int k) {
    List<Integer> res = new ArrayList<>();
    int len = num.length - 1;
    int carry = 0;
    // 遍历条件 这里一旦数组index没有 并且 K等于0就跳出来了
    while (len >= 0 || k != 0) {
        // 不能这样简单相加 int sum = num[len] + k % 10 + carry;
        int x = len < 0 ? 0 : num[len]; // 因为x和y中间可能任一一方提前结束
        int y = k == 0 ? 0 : k % 10;
        int sum = x + y + carry;
        res.add(sum % 10); // 只能取最后1位
        carry = sum / 10;  // 计算carry
        len--;
        k = k / 10; // 每次都要去掉最后1位 取商 这里的目的是为了逐一去掉个位 1886→188→18→1
    }
    // 这里！！重要，没有的话就会造成 888 + 666 = 554（1554的1 会丢掉）
    if (carry != 0) res.add(carry);
    Collections.reverse(res);
    return res;
}
```

这里要注意的地方有

- 结束条件`k != 0` 因为一直取模的话，最后就是0。等于0，说明取模完了。
- carry记得要计算` carry = sum / 10`

## [66. 加一](https://leetcode-cn.com/problems/plus-one/)

```
给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
你可以假设除了整数 0 之外，这个整数不会以零开头。

示例 1：
输入：digits = [1,2,3]
输出：[1,2,4]
解释：输入数组表示数字 123。
示例 2：
输入：digits = [4,3,2,1]
输出：[4,3,2,2]
解释：输入数组表示数字 4321。
示例 3：
输入：digits = [0]
输出：[1]

提示：
1 <= digits.length <= 100
0 <= digits[i] <= 9
```

### 思路

其实这一题总共就是为了判断三种情况

- 1234 这种在正常不过
- 1889 这种在倒数第2位的时候出现了一个9，那么9+1=10 这个时候要变成0，然后会再继续循环1次，就变成上面那种情况
- 1999 如果这个时候全部循环了还没返回，就需要新建一个数组，长度+1

### 代码实现

这是实现1

```java
class Solution {
    public int[] plusOne(int[] digits) {
        // 三种情况
        // 1. 1654
        // 2. 2889
        // 3. 9999
        // 开始遍历
        for(int i = digits.length - 1; i >= 0 ;i--) {
            // 最后一个加起来
            digits[i]++;
            // 如果等于10 那么当前的数字就是10
            if(digits[i] == 10) {
                // 注意这里不能直接return 因为循环还要继续 不然 2889 就不可能是2900 而是2890
                digits[i] = 0;
            // 如果不等于10 那么相当于直接 1654这种情况 直接返回
            } else {
                return digits;
            }
        }
        // 如果全部循环结束之后并没有返回 那就是9999的情况 变成10000
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }
}
```

下面是实现2，其实思路和上面一模一样。只是在判断当前数字是不是10的时候，用的是取模。因为任何跟个位数跟10取模，除了10以外全部是本身，10取模10是0

```java
class Solution {
    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i]++;
            // 用10取模来判定
            // ∵任何跟个位数跟10取模，除了10以外全部是本身，10取模10是0
            digits[i] = digits[i] % 10;
            if (digits[i] != 0) return digits;
        }
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }
}
```

