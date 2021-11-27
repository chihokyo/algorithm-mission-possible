# 算法日记

第二天的。继续加油吧。

## 一维数组

![image-20211126234306525](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211126234306525.png)

说了这么多，直接上题目吧。比如什么是山脉数组。

## [941. 有效的山脉数组](https://leetcode-cn.com/problems/valid-mountain-array/)

题目描述

```
给定一个整数数组 arr，如果它是有效的山脉数组就返回 true，否则返回 false。
让我们回顾一下，如果 A 满足下述条件，那么它是一个山脉数组：

arr.length >= 3
在 0 < i < arr.length - 1 条件下，存在 i 使得：
arr[0] < arr[1] < ... arr[i-1] < arr[i]
arr[i] > arr[i+1] > ... > arr[arr.length - 1]

示例 1：
输入：arr = [2,1]
输出：false
示例 2：
输入：arr = [3,5,5]
输出：false
示例 3：
输入：arr = [0,3,2,1]
输出：true

提示：
1 <= arr.length <= 104
0 <= arr[i] <= 104
```

![img](https://assets.leetcode.com/uploads/2019/10/20/hint_valid_mountain_array.png)

如上的一个数组，就是一直向上，**到达顶点之后就一定只能向下**，上上下下那种都不行！

这一题的数据规模在 `1 <= arr.length <= 10^4` 所以O(n^2) 也是可以的。

### 思路

- 其实本质就是找到顶点。

- 每两个比较，刚开始找到顶点之前，一直都要前一个比后一个小。

- 找到顶点之后，前一个一定要比后一个大，否则就不是山脉数组。

![2021-11-26 23-49-14.2021-11-26 23_50_01](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-11-26%2023-49-14.2021-11-26%2023_50_01.gif)

代码错误实现（没考虑边界情况）

```java
package com.chin._02._941;

public class _941_valid_mountain_array {
    public boolean validMountainArray(int[] arr) {
        int n = arr.length;
        int i = 0;
        // 1.找最高点 → 这个时候i指向的值就是最高点
        while (i < n && arr[i] < arr[i + 1]) i++;
        // 2.检查最高点之后是否递减
        while (i < n && arr[i] > arr[i + 1]) i++;
        // 3.如果i指向最后1个元素，说明到头一直递减 那么就是true
        return i == n - 1;
    }
}

```

但是这样做是有问题的，比如下面这种一直递增的，或者是一直递减的。

![image-20211127010156338](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211127010156338.png)

所以需要在最高点的时候进行判断，既不是在**第一个位置**，也不是在**最后一个位置**

最后的代码实现就是

### 代码实现

```java
class Solution {
    public boolean validMountainArray(int[] arr) {
        int n = arr.length;
        int i = 0;
        // 寻找最高点，一直向前走。
        // i = n - 1 的话，数组会越界
        while (i < n - 1 && arr[i] < arr[i + 1]) i++;
        // 此时得到的i就是最高点，但是不能在第一个or最后一个，否则就没有起伏不符合山脉数组
        if (i == 0 || i == n - 1) return false;
        // 从最高点向后走
        while (i < n - 1 && arr[i] > arr[i + 1]) i++;
        // 如果此时i走到最后恰好是最后一个元素，说明true
        return i == n - 1;

    }
}
```

双指针写的代码

```java
// left < n - 1
class Solution {
    public boolean validMountainArray(int[] arr) {
        int n = arr.length;
        int left = 0;
        int right = n - 1;
        // 边界条件要注意
        while (left < n - 1 && arr[left] < arr[left + 1]) left++;
        while (right > 0 && arr[right] < arr[right - 1]) right--;
        // [0,3,2,1] 错误的 return left == right;
        // [0,1,2,3,4,5,6,7,8,9] 错误 return left > 0 && right < n && left == right;
        return left > 0 && right < n - 1 && left == right;

    }
}

// left + 1 < n
class Solution {
    public boolean validMountainArray(int[] arr) {
        int n = arr.length;
        int left = 0;
        int right = n - 1;
        // 边界条件要注意
        while (left + 1 < n && arr[left] < arr[left + 1]) left++;
        while (right > 0 && arr[right] < arr[right - 1]) right--;
        // [0,3,2,1] 错误的 return left == right;
        // [0,1,2,3,4,5,6,7,8,9] 错误 return left > 0 && right < n && left == right;
        return left > 0 && right < n - 1 && left == right;
    }
}
```

这里写代码最重要的就是2个边界问题

- `while (left + 1 < n && arr[left] < arr[left + 1]) left++;` 这里的left 一定要在范围之内

  为什么呢。因为left是指针，如果一直递增的情况下。**left不能是最后一个！！** 但left + 1 可以是最后一个！

- `return left > 0 && right < n - 1 && left == right;` right 也必须小于 n - 1 。

  和上面的情况一样，如果从右向左走的话，right就是最后一个，说明一直递增，这样也不行。

那为什么递减的情况也不可以呢？

`[5,4,3,2,1]` 这个时候left肯定是5（index=0），right也是5（index=0），所以left必须大于0

```java
return left > 0 && right < n - 1 && left == right;
// left > 0 防止递减
// right < n - 1 防止递增
```

## [189. 轮转数组](https://leetcode-cn.com/problems/rotate-array/)

```
给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。

示例 1:
输入: nums = [1,2,3,4,5,6,7], k = 3
输出: [5,6,7,1,2,3,4]
解释:
向右轮转 1 步: [7,1,2,3,4,5,6]
向右轮转 2 步: [6,7,1,2,3,4,5]
向右轮转 3 步: [5,6,7,1,2,3,4]
示例 2:
输入：nums = [-1,-100,3,99], k = 2
输出：[3,99,-1,-100]
解释: 
向右轮转 1 步: [99,-1,-100,3]
向右轮转 2 步: [3,99,-1,-100]
 
提示：
1 <= nums.length <= 105
-231 <= nums[i] <= 231 - 1
0 <= k <= 105

进阶：
尽可能想出更多的解决方案，至少有 三种 不同的方法可以解决这个问题。
你可以使用空间复杂度为 O(1) 的 原地 算法解决这个问题吗？
```

这一题，需要用到3种不同的方法来解决。

**其实就是以一个数字为轴心来旋转而已。**

### 思路

#### 方法一 额外数组

![2021-11-27 01-52-18.2021-11-27 01_52_49](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-11-27%2001-52-18.2021-11-27%2001_52_49.gif)

- 新建一个数组
- 取模之后拷贝（取模应该是最难理解的，可以用一个数组模拟一下）
- 然后新数组需要再次拷贝到原数组

```java
// 取模
class Solution {
    // 方法1：额外数组
    public void rotate(int[] nums, int k) {
        int len = nums.length;
        // 新建临时数组
        int[] newArr = new int[len];
        for (int i = 0; i < len; i++) {
            // 求index 取模就可以完成翻转
            int index = (i + k) % len;
            // 临时[index] = 数组[i]
            newArr[index] = nums[i];
        }
        // 这里题目要求结果是原数组，所以还要完全复制到原数组
        System.arraycopy(newArr, 0, nums, 0, len);
    }
}

// 超时
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
      	// ②总共走k步
        for (int i = 0; i < k; i++) {
          	// 这里是因为最前面会空一个，所以和 nums[0] = temp; 呼应填补上去
            int temp = nums[n - 1];
          	// ①每次都是向前走1步
            for (int j = n - 1; j > 0; j--) {
                nums[j] = nums[j - 1];
            }
            nums[0] = temp;
        }
    }
}
```

> 时间复杂度： O(n)，其中 n 为数组的长度。
>
> 空间复杂度： O(n)。

#### 方法二 环状替换（数学有点难理解？）

这个思路貌似用的是一种很数学的求证方法。

> 循环的次数等于数组长度len和k的公约数。

```
我们先来看一个例子：
nums = [1, 2, 3, 4, 5, 6, 7, 8, 9]
k = 6
从索引 0 开始，每次走 k 步，遍历元素顺序：0 -> 6 -> 3 -> 0 (走了 3 圈，遍历了 4 个元素)
从索引 1 开始，每次走 k 步，遍历元素顺序：1 -> 4 -> 7 -> 1 (走了 3 圈，遍历了 4 个元素)
从索引 2 开始，每次走 k 步，遍历元素顺序：2 -> 8 -> 5 -> 2 (走了 3 圈，遍历了 4 个元素)

从索引为 0 的位置开始，每次走 k 步，最终会回到索引为 0 的位置
这个中间可能走了好几圈数组，假设走了 a 圈，又假设这个过程总共遍历的 b 个元素

那么 a * n = b * k 的，那么 b = (a * n) / k，也就是说一次遍历，可以遍历到的元素个数是 (a * n) / k
那么现在有 n 个元素，需要遍历的次数是：n / b = n / ((a * n) / k) = (n * k) / (a * n)
这里只有 a 的值是未知的，那么 a 的值是多大呢？

首先 a 肯定是 n 的倍数（因为 a 是好几圈的数组长度），所以 a * n 的话也肯定是 n 的倍数
又因为 a * n = b * k，所以 a * n 又是 k 的倍数
也就是说 a * n 是 n 和 k 的公倍数，

又因为每次遍历的时候，在第一次回到起点时就结束，因此 a 的值要尽可能的小
所以 a * n 是 n 和 k 的【最小】公倍数，记为：a * n = lcm(n, k)

所以遍历的次数 count = n / b = n / ((a * n) / k) = (n * k) / (a * n) = (n * k) / lcm(n, k)
也就是 n * k 除以 n 和 k 的最小公倍数，结果就是 n 和 k 的最大公约数
比如 n = 9, k = 6，那么 lcm(9, 6) = 18，那么 (9 * 6) / lcm(9, 6) = 3 ，即遍历次数为 3，也就是上面例子的遍历次数
再比如 n = 5, k = 3，那么 lcm(5, 3) = 15，那么 (5 * 3) / lcm(5, 3) = 1 ，即遍历次数为 1，即一次性就可以循环遍历所有元素
比如：nums = [1, 2, 3, 4, 5]  k = 3
遍历的顺序：0 -> 2 -> 5 -> 3 -> 0，一遍就可以遍历完
```

所以直接上代码就是，虽然我这样写了**。但其实我并不是很懂。**

```java
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        int gcdNum = gcd(n, k);
        for (int start = 0; start < gcdNum; start++) {
            int cur = start;
            int pre = nums[start];
            // 循环替换
            do {
                int next = (cur + k) % n;
                int tmp = nums[next];
                nums[next] = pre;
                pre = tmp;
                cur = next;
            } while (start != cur);
        }
    }

    // 计算x和y的最大公约数
    // 比如 6 和 2 的最大公约数是 2。 10 和 4 的最大公约数是 2
    public int gcd(int x, int y) {
        return y > 0 ? gcd(y, x % y) : x;
    }
}
```

> 时间复杂度： O(n)，其中 n 为数组的长度。每个元素只会被遍历一次。
> 空间复杂度： O(1)。我们只需常数空间存放若干变量。

#### 方案三 旋转数组

这一题的思路很好理解。

其实就是翻转了3次。

![image-20211127233404672](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211127233404672.png)

代码实现

```java
// 时间复杂度：O(n) 空间复杂度：O(1)
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n; // k = k % n;
        reserve(nums, 0, n - 1);
        reserve(nums, 0, k - 1);
        reserve(nums, k, n - 1);
    }

    // 闭着眼都要会写的如何翻转一个数组的方法！！
    private void reserve (int[] nums, int start, int end) {
        // 不写等号，是因为相等的时候就是同一个值
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}
```

> 时间复杂度：O(n)，其中 n为数组的长度。每个元素被翻转两次，一共 n 个元素.因此总时间复杂度为O(2n)=O(n)。
> 空间复杂度：O(1)。

还有就是 

```
k = k % n; 这个问题，其实不写这个也是可以的，但是当k超过数组长度的时候，你会发现本质上和结果上都是一样的，但是会多计算几次。所以先计算几次取模，这样可以提高效率。
```



