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

所以直接上代码就是，虽然我这样写了。**但其实我并不是很懂。**

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

## [665. 非递减数列](https://leetcode-cn.com/problems/non-decreasing-array/)

```
给你一个长度为 n 的整数数组，请你判断在 最多 改变 1 个元素的情况下，该数组能否变成一个非递减数列。我们是这样定义一个非递减数列的： 对于数组中任意的 i (0 <= i <= n-2)，总满足 nums[i] <= nums[i + 1]。

示例 1:
输入: nums = [4,2,3]
输出: true
解释: 你可以通过把第一个4变成1来使得它成为一个非递减数列。
示例 2:
输入: nums = [4,2,1]
输出: false
解释: 你不能在只改变一个元素的情况下将其变为非递减数列。
 
提示：
1 <= n <= 10 ^ 4
- 10 ^ 5 <= nums[i] <= 10 ^ 5
```

题意的目的的话。就是能否在改动1个元素的情况下，满足`ums[i] <= nums[i + 1]。`

比如本来是

```
■[4,2,3]
  [1,2,3] OK
  [2,2,3] OK 
  这里都是OK的
■[4,2,1]
这样无论如何都是变不成一个非递减数列的
```

### 思路

重点就是只能改1次。既然既可以，那么选择哪一个呢？

`nums[i - 1] = nums[i]`

`nums[i] = nums[i - 1]`

其实重点是看前2个元素的值！！！

![image-20211128001401011](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211128001401011.png)

根据上面的感觉的话，因为5是大于4的，所以最后需要把5赋值给2，保证[455,4]前面是非递减的，这样如果遇到了递减那么就要判断下是第几次，第2次的话就取消掉。

关于代码的2个难点

- 1 边界判断 → 这个很好理解
- 2 判断如何赋值

![image-20211128003716635](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211128003716635.png)

因为根据的前提条件`i-1`肯定是要大于`i-2`的

如果是下面这种情况的话！！就是要反过来了。

![image-20211128004006284](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211128004006284.png)

### 代码实现

主要是搞懂边界，我在写这些代码的时候搞懂了边界。以后估计会忘记吧。

主要是看指针i在哪里，要对比前面的几位还是后面的几位

```java
// 边界从for(int i =0)
class Solution {
    public boolean checkPossibility(int[] nums) {
        int count = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                count++;
                if (count > 1) return false;
                // 453 → 455 那么就需要 
              	// i-2>=0 是因为不能越界 
                if (i - 2 >= 0 && nums[i - 2] > nums[i]) {
                    // 因为根据题意，此时 [i - 1] 肯定要大于[i - 2]
                    nums[i] = nums[i - 1];
                } else {
                    // 426 → 466 
                    nums[i - 1] = nums[i];
                }
            }
        }
        return true;
    }
}
// 边界从for(int i=0)
class Solution {
    public boolean checkPossibility(int[] nums) {
        int count = 0;
        for (int i = 0; i + 1 < nums.length; i++) {
            if (nums[i + 1] < nums[i]) {
                count++;
                if (count > 1) return false;
                // 453 → 455 那么就需要 
                if (i - 1 >= 0 && nums[i - 1] > nums[i + 1]) {
                    // 因为根据题意，此时 [i - 1] 肯定要大于[i - 2]
                    nums[i + 1] = nums[i];
                } else {
                    // 426 → 466 
                    nums[i] = nums[i + 1];
                }
            }
        }
        return true;
    }
}
```

这里看了老师的一段题解

但其实这一题不判断第二个else也是可以的

```java
class Solution {
    public boolean checkPossibility(int[] nums) {
        int n = nums.length;
        int count = 0;
        for (int i = 0; i + 1 < nums.length; i++) {
            if (nums[i + 1] < nums[i]) {
                count++;
                if (count > 1) return false;
                //453 → 455
                if (i - 1 >= 0 && nums[i + 1] < nums[i - 1]) {
                    nums[i + 1] = nums[i];
                }
            }
        }
        return true;
    }
}
```

如果想要看懂为什么省略的话，试着认真读一下。

其实原理很简单 

- ` nums[i - 1] = nums[i]`  这样不会影响i之后的顺序，因为是把i赋值给前面的一个数值，后面没影响。
-  `nums[i] = nums[i - 1]` 这样就会影响i以及之后的顺序，这样可能造成重复。

```
解释这里为什么是这样修改：
■如果 nums[i] >= nums[i - 2] 时候，修改 nums[i] = nums[i - 1] 的话，会导致：修改 nums[i] 后，会影响 i 及其后面的顺序
■如果只是修改 nums[i - 1] = nums[i] 的话，则不会影响 i 及其后面的顺序
■如果 nums[i] < nums[i - 2] 时候，没办法，只能修改 nums[i] = nums[i - 1]，虽然也会影响 i 及其后面的顺序，但这是不可免的

因为我们的算法是需要保证语义：[0...i] 是非递减的

而且我们还要求尽可能少的修改次数，这样我们就需要尽可能少的影响 i 及其后面的顺序
所以我们只能这样来选择策略了：
■如果 nums[i] < nums[i - 2] 时候修改 nums[i] = nums[i - 1]；
■如果 nums[i] >= nums[i - 2] 时候修改 nums[i - 1] = nums[i]
————————————————————————
▼ 比如：[-1,4,2,3]
■如果 i 指向的是 2，这个时候，nums[i] > nums[i - 2] 的，
■如果修改 nums[i] = nums[i - 1]，那么数组变为 [-1, 4, 4, 3],这样导致 i 及其后面的顺序变了，需要再次修改，才可以使得数组非递增
■如果这个时候修改 nums[i - 1] = nums[i] 的话，那么数组变为 [-1, 2, 2, 3]，这样数组只需一次修改，就变为非递减数列了
这里其实还需要注意一个边界条件，那就是 i < 2 的时候，这个时候也是修改 nums[i - 1] = nums[i] ，目的也是为了尽可能少的影响 i 及其后面的顺序
```



## [228. 汇总区间](https://leetcode-cn.com/problems/summary-ranges/)

```
给定一个无重复元素的有序整数数组 nums 。
返回 恰好覆盖数组中所有数字 的 最小有序 区间范围列表。也就是说，nums 的每个元素都恰好被某个区间范围所覆盖，并且不存在属于某个范围但不属于 nums 的数字 x 。
列表中的每个区间范围 [a,b] 应该按如下格式输出：

"a->b" ，如果 a != b
"a" ，如果 a == b

示例 1：
输入：nums = [0,1,2,4,5,7]
输出：["0->2","4->5","7"]
解释：区间范围是：
[0,2] --> "0->2"
[4,5] --> "4->5"
[7,7] --> "7"
示例 2：
输入：nums = [0,2,3,4,6,8,9]
输出：["0","2->4","6","8->9"]
解释：区间范围是：
[0,0] --> "0"
[2,4] --> "2->4"
[6,6] --> "6"
[8,9] --> "8->9"
示例 3：
输入：nums = []
输出：[]
示例 4：
输入：nums = [-1]
输出：["-1"]
示例 5：
输入：nums = [0]
输出：["0"]
 
提示：
0 <= nums.length <= 20
-231 <= nums[i] <= 231 - 1
nums 中的所有值都 互不相同
nums 按升序排列
```

### 思路

![image-20211202224028481](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211202224028481.png)

从i开始遍历，相差为1说明就是练习，>=1说明就不是连续。

**这一题主要是定义low和high，初始化2个变量。**

- 开始定义low
- 然后定义high
- 然后看看俩是不是一样的，一样就添加1个字符串就可以了
- 那么重要的是怎么定义low和high呢？

其实low就是每一次的起始值i，那么怎么找high，那就是看每一次相差为1，一直到相差为2位置的那个数组就是high

### 代码

```java
class Solution {
    public List<String> summaryRanges(int[] nums) {
        // 初始化结果集
        List<String> res = new ArrayList<>();
        int i = 0;
        // 第一个循环是遍历每一个数字
        while (i < nums.length) {
            int low = i;
            i++;
            // 第二个循环是找到单独的数组每一个区间
            while (i < nums.length && nums[i] - nums[i - 1] == 1) i++;
            int high = i - 1; // 因为i++了，所以这里计算high要-
            // 构建每一个结果集里面的字符串 sb是对象
            StringBuilder sb = new StringBuilder(Integer.toString(nums[low]));      
            if(low < high) {
                sb.append("->");
                sb.append(Integer.toString(nums[high]));
            }
            // 对象 → 字符串
            res.add(sb.toString()); // 添加字符串到结果集
        }
        return res;
    }
}

// for实现
class Solution {
    public List<String> summaryRanges(int[] nums) {
         List<String> res = new ArrayList<>();
         for (int i = 0; i < nums.length; i++) {
             int low = i;
             while (i + 1 < nums.length && nums[i + 1] - nums[i] == 1) i++;
             int high = i;

            StringBuilder sb = new StringBuilder(Integer.toString(nums[low]));      
            if(low < high) {
                sb.append("->");
                sb.append(Integer.toString(nums[high]));
            }
            // 对象 → 字符串
            res.add(sb.toString()); // 添加字符串到结果集
        }
        return res;
    }
}
```

**难点**

- low和high到底怎么查找
- i++之后要--

## [163. 缺失的区间](https://leetcode-cn.com/problems/missing-ranges/)

这一题貌似是VIP专属。。

![image-20211202230236660](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211202230236660.png)

总之，就是给你一个范围，顺便给你2个数字组成一个范围。从这俩范围里面，看看给的范围里缺失了什么。

### 思路

![image-20211202230618301](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211202230618301.png)

判断标准就是以2为基准点，就是没有缺少的。

但是有一个小bug，比如下面这种，那就是-1的情况下，虽然0-（-1）是等于1被判断是没有缺少。

但事实上是缺少的，缺少了谁呢。缺少了本身的-1，所以需要提前-1

![2021-12-02 23-13-13.2021-12-02 23_13_22](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-12-02%2023-13-13.2021-12-02%2023_13_22.gif)

### 代码

```java
public class _163_missing_ranges {
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> res = new ArrayList<>();
        long pre = lower - 1; // 为了防止溢出 用的long
        for (int i = 0; i < nums.length; i++) {
            // 当前的值
            // nums[i] - pre 为了防止溢出 换成+
            if (nums[i] == pre + 2) {
                // 有缺失区间 正好第一位
                res.add(String.valueOf(pre + 1));
            } else if (nums[i] > pre + 2) {
                // 有缺失区间 直接拼接 （当前的值 - 1）
                res.add((pre + 1) + "->" + (nums[i] - 1));
            }
            pre = nums[i]; // 这里要注意 必须要进行转换到前一个值
        }

        // 走到这里应该就要走到最后了 判断最后一个值
        // 如果给的右闭区间 等于 最后一个值 + 1 那么久缺少这个值
        if (upper == pre + 1) res.add(String.valueOf(pre + 1));
            // 否则就是 + 1 到 右闭区间
        else if (upper > pre + 1) {
            res.add((pre + 1) + "->" + upper);
        }
        return res;
    }
}
```

