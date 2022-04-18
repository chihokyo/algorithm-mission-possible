# 算法日记

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

#### 两数相加

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

## [415. 字符串相加](https://leetcode-cn.com/problems/add-strings/)

```
给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和并同样以字符串形式返回。
你不能使用任何內建的用于处理大整数的库（比如 BigInteger）， 也不能直接将输入的字符串转换为整数形式。

示例 1：
输入：num1 = "11", num2 = "123"
输出："134"
示例 2：
输入：num1 = "456", num2 = "77"
输出："533"
示例 3：
输入：num1 = "0", num2 = "0"
输出："0"
 
提示：
1 <= num1.length, num2.length <= 104
num1 和num2 都只包含数字 0-9
num1 和num2 都不包含任何前导零
```

读题可以发现，不能用BigInteger，因为这一题的长度是10的4次方，所以有可能超过长度。

其实这一题和两数相加一模一样！

### 代码实现

```java
class Solution {
    public String addStrings(String num1, String num2) {
        StringBuilder res = new StringBuilder();
        int len1 = num1.length() - 1;
        int len2 = num2.length() - 1;
        int carry = 0;
        // 任意一组数字字符串没有结束
        while (len1 >= 0 || len2 >= 0) {
            // 取小的部分
            int x = len1 < 0 ? 0 : num1.charAt(len1) - '0';
            int y = len2 < 0 ? 0 : num2.charAt(len2) - '0';
            int sum = x + y + carry;
            // 取模那最后一位
            res.append(sum % 10);
            // 取商除去最后一位
            carry = sum / 10;
            len1--;
            len2--;
        }
        if (carry != 0) res.append(carry);
        return res.reverse().toString();
    }
}
```

## [67. 二进制求和](https://leetcode-cn.com/problems/add-binary/)

```
给你两个二进制字符串，返回它们的和（用二进制表示）。
输入为 非空 字符串且只包含数字 1 和 0。

示例 1:
输入: a = "11", b = "1"
输出: "100"
示例 2:
输入: a = "1010", b = "1011"
输出: "10101"
 
提示：
每个字符串仅由字符 '0' 或 '1' 组成。
1 <= a.length, b.length <= 10^4
字符串如果不是 "0" ，就都不含前导零。
```

这一题吧，刚开始一看是有点麻烦的，但其实本质就是把上面那一题也就是415，变成2进制就可以！！

`res.append(sum % 2);` `carry = sum / 10;`

超级easy

### 代码实现

```java
class Solution {
    public String addBinary(String a, String b) {
        StringBuilder res = new StringBuilder();
        int len1 = a.length() - 1;
        int len2 = b.length() - 1;
        int carry = 0;
        while (len1 >= 0 || len2 >= 0) {
            int x = len1 < 0 ? 0 : a.charAt(len1) - '0';
            int y = len2 < 0 ? 0 : b.charAt(len2) - '0';
            int sum = x + y + carry;
            // 这里是从10进制→2进制
            res.append(sum % 2);
            // 这里是从10进制→2进制
            carry = sum / 2;
            len1--;
            len2--;
        }
        if (carry != 0) res.append(carry);
        return res.reverse().toString();
    }
}
```

## [2. 两数相加](https://leetcode-cn.com/problems/add-two-numbers/)

又来了一个两数相加，无语啦！还是链表！

```
给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
请你将两个数相加，并以相同形式返回一个表示和的链表。
你可以假设除了数字 0 之外，这两个数都不会以 0 开头。

示例 1：
输入：l1 = [2,4,3], l2 = [5,6,4]
输出：[7,0,8]
解释：342 + 465 = 807.
示例 2：
输入：l1 = [0], l2 = [0]
输出：[0]
示例 3：
输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
输出：[8,9,9,9,0,0,0,1]
 
提示：
每个链表中的节点数在范围 [1, 100] 内
0 <= Node.val <= 9
题目数据保证列表表示的数字不含前导零
```

### 思路

其实主要是链表的思路吧。

![2022-01-24 15-26-21.2022-01-24 15_27_07](https://raw.githubusercontent.com/chihokyo/image_host/develop/2022-01-24%2015-26-21.2022-01-24%2015_27_07.gif)

主要是需要一个虚拟表头

- 每2个相加，2个链表都走到头才结束
- 其实除了是用了链表，值是val之后，其他就是链表的特性。

### 代码实现

首先来想的话链表不需要什么

- 指针不需要了，因为链表的指针就是当前的节点
- 条件变为null就是over
- 前进后退操作要知道

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 虚拟链表头
        ListNode dummy = new ListNode();
        // 默认指向cur
        ListNode cur = dummy;
        int carry = 0;
        // 只要l1和l2这俩链表没有null
        while (l1 != null || l2 != null) {
            // 判断不为空就取值
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            int sum = x + y + carry;
            // 这里要注意，新建的节点是取模最后1位，并且是指向cur.next
            cur.next = new ListNode(sum % 10);
            cur = cur.next; // 这里新建只有cur要向前进
            carry = sum / 10;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        if (carry != 0) cur.next = new ListNode(carry);
        return dummy.next;
    }
}
```

关于代码实现的 一些问题

为什么最后返回的是`dummy.next`而不是cur，这个我原本有疑问的，但是画图之后发现，cur可是一直向下走的。但是dummy才是一直没变的，dummy.next就是指向链表的数字的第1个位置。

![image-20220124155625472](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220124155625472.png)

这里为什么要用next呢？为什么`cur = cur.next`要向前走呢？

![image-20220124160325258](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220124160325258.png)

至于向前走的话，因为如果只是赋值的话，那么cur本身还是没有变化的，下一次再次赋值的时候指向就还是7那个位置

所以在赋值之后，需要向前走的这一步

```java
cur.next = new ListNode(sum % 10); // 赋值 只是赋值 指针没变 走个屁
cur = cur.next; // 指针向前走
```

## [43. 字符串相乘](https://leetcode-cn.com/problems/multiply-strings/)

```
给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
注意：不能使用任何内置的 BigInteger 库或直接将输入转换为整数。

示例 1:
输入: num1 = "2", num2 = "3"
输出: "6"
示例 2:
输入: num1 = "123", num2 = "456"
输出: "56088"

提示：
1 <= num1.length, num2.length <= 200
num1 和 num2 只能由数字组成。
num1 和 num2 都不包含任何前导零，除了数字0本身。
```

### 思路

就是用的加法的思路，其实就是按照上面相加的思路。

思路一，加法思路

![image-20220201164957308](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220201164957308.png)



思路一的代码实现

```java
public String multiply1(String num1, String num2) {
    // 如果任意数字为0 结果为0
    if (num1.equals("0") || num2.equals("0")) return "0";
    String res = "0";
    int m = num1.length(), n = num2.length();
    // 处理乘数的每一位
    for (int i = n - 1; i >= 0; i--) {
        // 新建临时字符串
        StringBuilder curRes = new StringBuilder();
        // 如果空出来了，需要补0
        for (int j = n - 1; j > i; j--) curRes.append("0");
        // 85*24的话就是从24的4开始处理
        int y = num2.charAt(i) - '0';
        int carry = 0;
        for (int j = m - 1; j >= 0; j--) {
            // 85*24的话就是从85的5开始处理
            int x = num1.charAt(j) - '0';
            // 获得乘积
            int product = x * y + carry;
            // 加入到结果
            curRes.append(product % 10);
            carry = product / 10;
        }
        if (carry != 0) curRes.append(carry);
        // 走到这里其实可以获得当前的结果，和临时的结果。
        // 当前的结果和临时的结果相加，在赋值给res
        res = addStrings(res, curRes.reverse().toString());
    }
    return res;
}

// 字符串两数相加
private String addStrings(String num1, String num2) {
    StringBuilder res = new StringBuilder();
    int i1 = num1.length() - 1, i2 = num2.length() - 1;
    int carry = 0;
    while (i1 >= 0 || i2 >= 0) {
        int x = i1 >= 0 ? num1.charAt(i1) - '0' : 0;
        int y = i2 >= 0 ? num2.charAt(i2) - '0' : 0;
        int sum = x + y + carry;
        res.append(sum % 10);
        carry = sum / 10;

        i1--;
        i2--;
    }
    if (carry != 0) res.append(carry);
    return res.reverse().toString();
```



思路二，相乘法。

这个方法需要计算最小的情况。除了第一位是1，其他都是0

![image-20220201165956489](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220201165956489.png)

这个是最大的情况，都是9

![image-20220201170039971](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220201170039971.png)

也就是说最大的长度就是m+n

顺便又可以一个规律

![image-20220201170214891](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220201170214891.png)

左移最后可以得出2个规律

- m*n的长度不会大于m+n
- 个位数和进位数的规律，如图。

![image-20220201170451036](/Users/chin/Library/Application Support/typora-user-images/image-20220201170451036.png![](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220201170214891.png)

就这样一步步的向前相乘

### 代码实现

```java
public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";
        int m = num1.length(), n = num2.length();
        // 根据数学推理，长度分别为m和n的数组相乘
        // 数组最大长度不会超过这个的
        int[] res = new int[m + n];
        for (int i = m - 1; i >= 0; i--) {
            int x = num1.charAt(i) - '0';
            for (int j = n - 1; j >= 0; j--) {
                int y = num2.charAt(j) - '0';
                int sum = res[i + j + 1] + x * y;
                res[i + j + 1] = sum % 10;
                res[i + j] += sum / 10;
            }
        }
        // 这个时候res已经是一个数组了，但可能有0
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            // 第一个位置如果是0一定是要舍弃
            if (i == 0 && res[i] == 0) continue;
            sb.append(res[i]);
        }
        return sb.toString();
    }
```

## [204. 计数质数](https://leetcode-cn.com/problems/count-primes/)

```java
统计所有小于非负整数 n 的质数的数量。

示例 1：
输入：n = 10
输出：4
解释：小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
示例 2：
输入：n = 0
输出：0
示例 3：
输入：n = 1
输出：0
  
提示：
0 <= n <= 5 * 106
```

### 思路

首先这一题最简单的就是每一个数字判断是不是为【质数】，是的count+1就可以了

![image-20220203144757125](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220203144757125.png)![](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220201170214891-20220203160745732.png)

```java
// 解法1 暴力解法 2个循环O(n^2)，数据量大不行
    public int countPrimes1(int n) {
        int res = 0;
        for (int i = 2; i < n; i++) {
            res += isPrime(n) ? 1 : 0;
        }
        return res;
    }

    // 判断一个数字x是不是指数
    private boolean isPrime(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
```

下面是一个埃拉托斯特尼筛法的思路 简称埃氏筛

### 埃氏筛

![](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220201170214891-20220203160745732-20220203160753707.png)

这里的思路就是新建一个长度为n的数组。数组里面存储布尔值。

如果2是质数，那么2×2，3×2，4×2...肯定不是质数。

以此类推，这一题主要还是去看一下埃氏筛估计啥都了解了。

```java
// 埃氏筛 O(nlog(log n))
public int countPrimes2(int n) {
  int res = 0;
  // 初始值全部都是false，也就是说初始值全部都是合数
  boolean[] notPrimes = new boolean[n];
  for (int i = 2; i < n; i++) {
    // 这里第一次也就是notPrimes[2]的时候，肯定是false的，因为2是质数

    // 如果这里notPrimes[i]是true 就是合数 那么直接跳过
    if (notPrimes[i]) continue;
    res++; // 走到这里肯定是质数 那么结果+1
    // 如果 i 是质数，那么 2i、3i、4i.... 肯定不是质数
    // 说明：这里 i 最好是从 i + i 开始，因为 i 有可能是质数
    // 其实 i 从 i 开始也没啥问题，因为 i 在上面已经判断过了
    // 但是，这样就违背了 notPrimes 数组的含义了，所以这里修改为 i + i
    for (int j = i + i; j < n; j += i) {
      notPrimes[j] = true; // ∵ 2是质数 ∴ 2*2,2*3,2*4...全部都是合数
    }
  }
  return res;
}
```

## [233. 数字 1 的个数](https://leetcode-cn.com/problems/number-of-digit-one/)

```
给定一个整数 n，计算所有小于等于 n 的非负整数中数字 1 出现的个数。

示例 1：
输入：n = 13
输出：6
示例 2：
输入：n = 0
输出：0
 
提示：
0 <= n <= 109
```

这一题的意思就是说，找出现1的个数比如下面

![image-20220203153007996](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220203153007996.png)

#### 思路

这一题首先是数据量比较大，肯定是不能用暴力解法的。当然暴力解法能写出来也可以了。

```java
class Solution {
    public int countDigitOne(int n) {
        int res = 0;
        // 这里要从1开始 因为根据题意 就是1开始
        for (int i = 1; i <= n; i++) {
            // 数字 → 字符
            String s = String.valueOf(i);
            int oneRes = 0; // 临时计算每一个字符串有没有
            // 字符 → 字符串数组
            for (char c : s.toCharArray()) {
                // 是否为1
                if (c == '1') oneRes++;
            }
            res += oneRes;
        }
        return res;
    }
}
```

所以这一题的结论就是使用数学推理

![image-20220203154442902](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220203154442902.png)

![image-20220203154548390](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220203154548390.png)

### 代码实现

```java
// 数学推算 太难 不看 复杂度：O(log n)
public int countDigitOne(int n) {
  int res = 0;
  for (long i = 1; i <= n; i *= 10) {
    // i 应该是长整型，要不然会溢出
    long divider = i * 10;
    // (n/10)*1 + min(max((n%10 - 1 + 1), 0), 1)
    // (n/100)*10 + min(max((n%100 - 10 + 1), 0), 10)
    // (n/1000)*100 + min(max((n%1000 - 100 + 1), 0), 100)
    res += (n / divider) * i + Math.min(Math.max(n % divider - i + 1, 0L), i);
  }
  return res;
}
```

## [1232. 缀点成线](https://leetcode-cn.com/problems/check-if-it-is-a-straight-line/)

```
给定一个数组 coordinates ，其中 coordinates[i] = [x, y] ， [x, y] 表示横坐标为 x、纵坐标为 y 的点。请你来判断，这些点是否在该坐标系中属于同一条直线上。
输入：coordinates = [[1,1],[2,2],[3,4],[4,5],[5,6],[7,7]]
输出：false

提示：
2 <= coordinates.length <= 1000
coordinates[i].length == 2
-10^4 <= coordinates[i][0], coordinates[i][1] <= 10^4
coordinates 中不含重复的点

```

### 思路

其实就是算斜率，算出前2个的斜率，从第3个开始如果不符合这个斜率公式了，就out！

![image-20220203155547314](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220203155547314.png)

下面直接写代码就可以了，不是特别难！

需要注意的就是**除法的问题**  一样的

```
 deltaY / deltaX == deltaYi / deltaXi
 deltaY * deltaXi != deltaX * deltaYi
```



### 代码实现

```java
class Solution {
    public boolean checkStraightLine(int[][] coordinates) {
        // 这里其实就是点了4个点 这样能计算斜率
        int x0 = coordinates[0][0];
        int y0 = coordinates[0][1];
        int deltaY = coordinates[1][1] - y0;
        int deltaX = coordinates[1][0] - x0;
        for (int i = 2; i < coordinates.length; i++) {
            int deltaYi = coordinates[i][1] - y0;
            int deltaXi = coordinates[i][0] - x0;
            // 这里其实有个巧 因为计算斜率用的除法，但是这样会遇到整除除不尽 产生误差
            // 所以除法，改成交叉乘法
            // deltaY / deltaX == deltaYi / deltaXi
            // 斜率不对了 就直接false
            if (deltaY * deltaXi != deltaX * deltaYi) return false;
        }
        return true;
    }
}
```

