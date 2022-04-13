# 算法日记

## 二分查找相关

### 最基本的查找 → 一般写法

当left 大于 right 证明中间没有任何未处理的数据，这才是跳出。

也就是说判断是 `while (left <= right) {计算逻辑}`

```java
// 时间复杂度：O(logn)
// 空间复杂度：O(1)
public boolean contains(int[] nums, int target) {
    if (nums == null || nums.length == 0) return false;

    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
        // bug ：left + right 会溢出
        // 整数的最大值：2^31 - 1 = 2147483647
        int mid = left + (right -  left) / 2;
        // >>> 无符号右移
        // int mid = (left + right) >>> 1;
        if (target == nums[mid]) {
            return true;
        } else if (target < nums[mid]) {
            right = mid - 1; // 下一次搜索区间：[left...mid - 1]
        } else { // target > nums[mid]
            left = mid + 1; // 下一次搜索区间：[mid + 1...right]
        }
    }
    // left > right ：没有元素
    return false;
}
```

### 最基本的查找 → 递归写法

```java
// 时间复杂度：O(logn)
// 空间复杂度：O(logn)
public boolean containsR(int[] nums, int target) {
    if (nums == null || nums.length == 0) return false;
    return contains(nums, 0, nums.length - 1, target);
}

private boolean contains(int[] nums, int left, int right, int target) {
    if (left > right) return false;

    int mid = left + (right - left) / 2;
    if (nums[mid] == target) return true;

    if (target < nums[mid]) {
        return contains(nums, left, mid - 1, target);
    } else {
        return contains(nums, mid + 1, right, target);
    }
}
```

以下的问题，最好是先看过704这一道leetcode的题目再来解决。

### 问题1：关于整型溢出的问题

因为left和right都属于整型，会有可能溢出的问题。所以需要解决

解决方法1，使用right-left，并且使用了位运算，右移1位就是除以2，但是因为位运算的优先级比+号低，所以要括起来，但是 java 内部现在两个性能都一样的，/ 2 最后

- `int mid = left + ((right - left) >> 1)`
- `int mid = left + ((right - left) >> 1)`
- int mid =  (left + right) >>> 1` **无符号右移**

### 问题2：向上取整问题

其实二分搜索，有个向上取整也有向下取整。至于用哪一个其实是跟你的写法有关的。

[一篇我觉得关于二分法的一些细节说的还可以的文章](https://www.jianshu.com/p/b225949678e0)

> **尝试排除左区间的写法：当搜索区间只剩下两个元素时，应该采用向上取整。**
>
> **「反证法」**：因为![[left , right]](https://math.jianshu.com/math?formula=%5Bleft%20%2C%20right%5D)区间被划分为：![[left , mid - 1]](https://math.jianshu.com/math?formula=%5Bleft%20%2C%20mid%20-%201%5D) 和 ![[mid , right]](https://math.jianshu.com/math?formula=%5Bmid%20%2C%20right%5D)，如果选择向下取整（取前一个中位数，mid 的值等于 left），那么在左区间 **无法** 排除时，会进入`left = mid`的分支。此时，left 和 right 的值没有改变，出现区间不会缩小的情况，进入死循环。
>
> **尝试排除右区间的写法：当搜索区间只剩下两个元素时，应该采用向下取整。**
>
> **「反证法」**：因为![[left , right]](https://math.jianshu.com/math?formula=%5Bleft%20%2C%20right%5D)区间被划分为：![[left , mid]](https://math.jianshu.com/math?formula=%5Bleft%20%2C%20mid%5D) 和 ![[mid + 1 , right]](https://math.jianshu.com/math?formula=%5Bmid%20%2B%201%20%2C%20right%5D)，如果选择向上取整（取后一个中位数，mid 的值等于 right），那么在右区间 **无法** 排除时，会进入`right = mid`的分支。此时，left 和 right 的值没有改变，出现区间不会缩小的情况，进入死循环。

其实上面的这个取整，大部分是跟排除区间的那种二分法写法有关。

![image-20220411230204425](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220411230204425.png)

###  问题3：关于 while(left <= right)

其实这一个是根据你写的算法界限有关系的

```java
// ① 区间内查找目标值的二分法
int left = 0, right = nums.length - 1;
while (left <= right) // 当left > right才会跳出，保证中间没有任何数值了
  
int left = 0, right = nums.length;
while (left < right) // 因为你永远取不到right 所以当大于才能跳出来
  
// ② 排除区间的二分法
int left = 0, right = nums.length - 1;
while (left < right) // 当left == right才会跳出 这样最后还会剩下一个值
return if (nums[left] == left) left ? : -1;
```

## 查找第一个等于目标元素的下标（有重复元素）

#### 思路

其实主要是多了一个判断

- 前面的值到底是不是目标值，如果是目标值的话就移动right
- ⚠️ 同时一定要注意，特殊情况，就是如果第一个就是，那么你的代码一定要判断！

![image-20220412224743783](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220412224743783.png)

下面直接代码实现

```java
public int firstTarget(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    int left = 0, right = nums.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (target == nums[mid]) {
            // 这里很重要 判断是否为第1个 or 是否前一个不等于
            // 不等于 或者 第一个的话 那么这个值就是答案
            if (mid == 0 || nums[mid - 1] != target) return mid;
                // 否则就继续缩小范围
            else right = mid - 1;
        } else if (target < nums[mid]) {
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }
    return -1;
}
```

> 代码难点一 就是判断前一个
>
> 代码难点二 `mid == 0` 这个case很有可能忘记

## 查找第一个大于等于目标元素的下标

### 思路

这里最重要的在大于和等于的情况下，都要进行一样的逻辑判断。来看前一个是不是！

![image-20220412231054204](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220412231054204.png)

### 代码实现

```java
public int firstGETarget(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    int left = 0, right = nums.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (target == nums[mid]) {
            // 等于的情况下 看一个是否也符合条件 继续大于的话就移动right
            // 否则就是答案
            if (mid == 0 || nums[mid - 1] < target) return mid;
            else return mid - 1;
        } else if (target < nums[mid]) {
            // 等于的情况下 看一个是否也符合条件 继续大于的话就移动right
            // 否则就是答案
            if (mid == 0 || nums[mid - 1] < target) return mid;
            else return mid - 1;
        } else {
            // target > nums[mid]
            left = mid + 1;
        }
    }
    return -1;
}
// 由于上面 == 和 < 逻辑是一样的
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (target <= nums[mid]) {
        // 等于的情况下 看一个是否也符合条件 继续大于的话就移动right
        // 否则就是答案
        if (mid == 0 || nums[mid - 1] < target) return mid;
        else return mid - 1;
    } else {
        // target > nums[mid]
        left = mid + 1;
    }
}
```

## 查找最后一个等于目标元素的下标

### 思路

其实和找第一个等于目标元素的下标是一样的感觉，只不过要注意

- 当找到之后要判断下一个是不是也相等，如果也相等，需要继续右移
- ⚠️要注意如果`nums[nums.length -1]`的情况下别越界了

![1](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220412233732983.png)

### 代码实现

```java
public int lastTarget(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    int left = 0, right = nums.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (target == nums[mid]) {
            // 如果相等的话，要看后一个是否也相等
            // 相等的话要右移缩小范围，不等的话直接就是答案
            // 要注意如果mid已经是最后一个元素的情况下 直接也是答案
            if (mid == nums.length - 1 || nums[mid + 1] != target) return mid;
            else left = mid + 1;
        } else if (target < nums[mid]) {
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }
    return -1;
}
```

## 最后一个小于等于目标元素的下标

### 思路

基本上就是和前面一样直接上代码算了

### 代码实现

```java
public int lastLeTarget(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    int left = 0, right = nums.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (target >= nums[mid]) {
            if (mid == nums.length - 1 || nums[mid + 1] > target) return mid;
            else left = mid + 1;
        } else {
            left = mid + 1;
        }
    }
    return -1;
}
```



## [704. 二分查找](https://leetcode-cn.com/problems/binary-search/)

```
给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
示例 1:
输入: nums = [-1,0,3,5,9,12], target = 9
输出: 4
解释: 9 出现在 nums 中并且下标为 4
示例 2:
输入: nums = [-1,0,3,5,9,12], target = 2
输出: -1
解释: 2 不存在 nums 中因此返回 -1
 
提示：
你可以假设 nums 中的所有元素是不重复的。
n 将在 [1, 10000]之间。
nums 的每个元素都将在 [-9999, 9999]之间。
```

### 思路1

第一种常规的思路

表示的是不断在某个区间内查找符合条件的元素

```java
// 第1种思路，表示的是不断在某个区间内查找元素
public int search(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    int left = 0, right = nums.length - 1;
    // left 等于 right的意思 相当于只有1个，或者只剩1个的情况
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] > target) {
            // 目标值更小
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }
    return -1;
}
```

### 思路2

在循环体内排除一定不存在目标元素的区间

```java
// 第2种思路，在循环体内排除一定不存在目标元素的区间
// 不用判断某个值是否等于mid 而是排除区间
// 如果大于mid 说明左边的空间全部是可以删除掉的
public int search2(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    int left = 0, right = nums.length - 1;
    // 这里注意是没有==的情况的，因为==的情况证明left和right相遇
    // 证明只有1个，只有1个的时候是需要我们自己来判断大小的
    // 只有1个的时候是应该结束循环的
    while (left < right) {
        int mid = left + (right - left) / 2;
        // 目标值更大 说明在右边那个区间进行查找
        if (target > nums[mid]) {
            left = mid + 1;
        } else {
            // 因为这里的情况是<= 说明有可能是本身
            // 这里是不能取值到+1的
            right = mid;
        }
    }
    // 走到这里说明left==right了，一定是这样的
    // 并且这里只剩下一个元素了没有处理了，这个答案是对的话就是不是就false
    if (nums[left] == target) return left; // 这里写right也可以
    return -1;
}
```

然后这里写一个如果 `target < nums[mid] `

![image-20220404212501290](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220404212501290.png)

因为会出现一个向上取整的问题，所以这一个要这样写

```java
class Solution {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        int left = 0, right = nums.length - 1;
        while (left < right) {
            // 注意这里 因为会有向上取整的问题
            int mid = left + (right - left + 1) / 2;
            // 改成了小于
            if (target < nums[mid]) {
                // 这里也是
                right = mid - 1;
            } else {
                // 这里也是
                left = mid;
            }
        }
        if (nums[left] == target) return left;
        return -1;
    }
}
```

### 重要补充

这里的思路1和思路2，要仔细看看有什么不同。

思路1是在原先的基础上，循环不变量，在一个区间内再次进行搜索。感觉有点像递归？

思路2 的意思是一种范围缩小。不断进行范围缩小。最后只会剩下一个元素。

这俩在写代码的时候主要有以下几个不一样的地方！你会发现思路2里不进行查找，只会进行比较！！！

-  `while (left <= right) {}` 最后只剩下一个，一直递归到最后
-  `while (left < right) {}` 这里既然是取得范围，相等的话就不是一个范围了
- `if (nums[left] == target) return left;`这个在思路2会进行判断，因为排除了所有的不可能，最后只剩下1个！！只要去看看这1个是不是就可以了。

## [34. 在排序数组中查找元素的第一个和最后一个位置](https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

```
给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
如果数组中不存在目标值 target，返回 [-1, -1]。
进阶：
你可以设计并实现时间复杂度为 O(log n) 的算法解决此问题吗？
 
示例 1：
输入：nums = [5,7,7,8,8,10], target = 8
输出：[3,4]
示例 2：
输入：nums = [5,7,7,8,8,10], target = 6
输出：[-1,-1]
示例 3：
输入：nums = [], target = 0
输出：[-1,-1]
 
提示：
0 <= nums.length <= 105
-109 <= nums[i] <= 109
nums 是一个非递减数组
-109 <= target <= 109
```

### 思路1

这一题可以先用一种思路，那就是前后两个指针，一个从前向后找，一个从后向前找。

找到的这个就是区间！

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            // 找不到就向前走
            while (left < right && nums[left] < target) {
                left++;
            }
	          // 找不到就向后退
            while (left < right && nums[right] > target) {
                right--;
            }
            // 相等直接返回
            if (nums[left] == target && nums[right] == target) {
                return new int[]{left, right};
            } else {
                // 不等的话证明无解，直接跳出
                break;
            }
        }

        return new int[]{-1, -1};
    }
}
```

### 思路2

就是把这一问题拆分成2个小问题

- 符合条件的第1个目标值的索引
- 符合条件的最后1个目标值的索引

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new int[]{-1, -1};
        // 找到第1个
        int firstTargetIndex = searchFirstTarget(nums, target);
        // 说明没找到
        if (firstTargetIndex == -1) return new int[]{-1, -1};

        // 找到最后1个
        int lastTargetIndex = searchLastTarget(nums, target);
        // 拼接在一起
        return new int[]{firstTargetIndex, lastTargetIndex};
    }
		
	  // 查找第1个符合条件的索引
    private int searchFirstTarget(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target == nums[mid]) {
                // 如果为第1个元素 or 如果前面一个直接小
                if (mid == 0 || nums[mid - 1] < target) return mid;
                    // 如果前面一个还是等于 说明right还要缩小
                else right = mid - 1;
            } else if (target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
		// 查找最后1个符合条件的索引
    private int searchLastTarget(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target == nums[mid]) {
                // 如果已经为最后1个元素 or 如果后面一个更大
                if (mid == nums.length - 1 || nums[mid + 1] > target) return mid;
                    // 如果前面一个还是等于 说明right还要缩小
                else left = mid + 1;
            } else if (target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
```

### 思路3 

在循环体中排除一定不存在的目标

```
1,3,4,6,8,8,9,10
比如要找8，那肯定先排除左边，在排除右边
1,3,4,6←没了→9,10
```

![image-20220404225000124](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220404225000124.png)

代码如下

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new int[]{-1, -1};
        // 找到第1个
        int firstTargetIndex = searchFirstTarget2(nums, target);
        // 说明没找到
        if (firstTargetIndex == -1) return new int[]{-1, -1};

        // 找到最后1个
        int lastTargetIndex = searchLastTarget2(nums, target);
        // 拼接在一起
        return new int[]{firstTargetIndex, lastTargetIndex};
    }

    private int searchFirstTarget2(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 说明左边的都要被抛弃
            if (target > nums[mid]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        if (nums[left] == target) return left;
        return -1;
    }

    private int searchLastTarget2(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            // 说明右边的都要被抛弃
            if (target < nums[mid]) {
                right = mid - 1;
            } else {
                // 说明右边都要被抛弃
                left = mid;
            }
        }
        if (nums[left] == target) return left;
        return -1;
    }
}
```

## [35. 搜索插入位置](https://leetcode-cn.com/problems/search-insert-position/)

```
给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
请必须使用时间复杂度为 O(log n) 的算法。

示例 1:
输入: nums = [1,3,5,6], target = 5
输出: 2
示例 2:
输入: nums = [1,3,5,6], target = 2
输出: 1
示例 3:
输入: nums = [1,3,5,6], target = 7
输出: 4
 
提示:
1 <= nums.length <= 104
-104 <= nums[i] <= 104
nums 为 无重复元素 的 升序 排列数组
-104 <= target <= 104
```

### 思路1

- 最原始的解法就是线性扫描（复杂度n 不符合题意）

- 扫描到==target的就返回当前index的前一个index
- 如果比最小的都小，那么就是0
- 如果比最大的都大，那么就是数组长度

线性扫描的思路，复杂度n

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        // 从前到后开始遍历
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) return i; 
        }
        // [1,2,3,4] target:5 这种情况
        return nums.length;
    }
}
```

### 思路2

这一题本质就是找到第一个大于等于 target 的元素 index

- 现在循环体内找符合条件的（正常的二分查找
- 看看前面的是否是小于，如果小就是直接返回
- 如果前面还是大的，说明需要重新进行再来一遍

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        // 比最后一个最大的还要大，直接发个到最后
        if (target > nums[nums.length - 1]) return nums.length;
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target <= nums[mid]) {
                // 小于等于 说明可能是答案，但是还要看前一个是不是小
                // 小才是答案
                // mid - 1 有可能过界 所以考虑一下
                if (mid == 0 || nums[mid - 1] < target) return mid;
                // 如果前面还要大，那就继续缩小右边
                else right = mid - 1;
            } else {
                // 这里说明 target>nums[mid] 肯定
                left = mid + 1;
            }
        }
        // 没有直接返回负一
        return -1;
    }
}
```

### 思路3

不断缩小范围

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
              // mid的值更大 缩小右边
                right = mid - 1;
            } else if (nums[mid] < target) {
              // mid的值更小 缩小左边
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }
}
```

## [278. 第一个错误的版本](https://leetcode-cn.com/problems/first-bad-version/)

```
你是产品经理，目前正在带领一个团队开发新的产品。不幸的是，你的产品的最新版本没有通过质量检测。由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。
假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
你可以通过调用 bool isBadVersion(version) 接口来判断版本号 version 是否在单元测试中出错。实现一个函数来查找第一个错误的版本。你应该尽量减少对调用 API 的次数。

示例 1：
输入：n = 5, bad = 4
输出：4
解释：
调用 isBadVersion(3) -> false 
调用 isBadVersion(5) -> true 
调用 isBadVersion(4) -> true
所以，4 是第一个错误的版本。
示例 2：
输入：n = 1, bad = 1
输出：1

提示：
1 <= bad <= n <= 231 - 1
```

### 思路1

这一题的本质其实就是 **在一个升序的数组中，找到第一个等于6的位置。**

首先是一个在循环体内找的方法。

```java
/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int left = 1, right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 如果mid这个是坏版本，那么就一定是这个吗
            // 不一定，因为前面可能还是，所以还要判断一下
            if (isBadVersion(mid)) {
                if (mid == 1 || !isBadVersion(mid - 1)) return mid;
                // 如果还是坏版本，就继续缩小
                else right = mid - 1;
            } else {
                // 到这里说明肯定不是坏版本，需要向右找
                left = mid + 1;
            }
        } 
        return -1;
    }
}
```

### 思路2

排除不必要的区间

```java
/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int left = 1, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 坏版本，说明自己是or自己前面也有可能是
            if (isBadVersion(mid)) {
                right = mid;
            } else {
                // 那肯定是前面了
                left = mid + 1;
            }
        }
        // 走完了还不是，就最后了。
      	// return right;也是对的
        return left;
    }
}
```

## [33. 搜索旋转排序数组](https://leetcode-cn.com/problems/search-in-rotated-sorted-array/)

```
整数数组 nums 按升序排列，数组中的值 互不相同 。

在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。

示例 1：
输入：nums = [4,5,6,7,0,1,2], target = 0
输出：4
示例 2：
输入：nums = [4,5,6,7,0,1,2], target = 3
输出：-1
示例 3：
输入：nums = [1], target = 0
输出：-1

提示：
1 <= nums.length <= 5000
-10^4 <= nums[i] <= 10^4
nums 中的每个值都 独一无二
题目数据保证 nums 在预先未知的某个下标上进行了旋转
-10^4 <= target <= 10^4
```

### 思路

首先读懂题目很重要，这一题的题意没读懂的话就不行

- 数组整体是部分有序的，前面部分有序，后面也部分有序
- 前面的有序数组肯定是大于后面的有序数组的，（因为本来都是有序的，只是旋转过去的。
- `nums[left] <= nums[mid]` 前面是有序的
- `nums[left] > nums[mid]` 后面是有序的

![image-20220410220525044](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220410220525044.png)

首先看第一种情况，也就是在

`nums[left] <= nums[mid]`的时候，那么查找元素的过程

![image-20220410221701919](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220410221701919.png)

那么接下来在看另一种情况

`nums[left] > nums[mid]` 的时候，查找4和13的情况

![image-20220410221955689](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220410221955689.png)

也就当旋转数组，肯定是有一段区间是有序的。那么就先看是不是在有序的那段的，如果不是，就是在另外一边。

将数组一分为二，其中一定有一个是有序的，另一个可能是有序，也能是部分有序。此时有序部分用二分法查找。无序部分再一分为二，其中一个一定有序，另一个可能有序，可能无序。就这样循环

### 代码实现

```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 找到了 直接就可以返回
            if (target == nums[mid]) return mid;
            // 这样说明左边肯定是有序的，left mid 范围内肯定有序
            if (nums[left] <= nums[mid]) {
                // 左边有序的话，就要看看target在不在[left,mid]之间
                // 等于nums[mid]就不用判断了，因为已经判断了
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // 右边，mid right 范围内肯定有序
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}
```

这段代码最重要的就是两个判断内部还分别判断了。

- `if (nums[left] <= nums[mid])`这个是为了判断到底哪部分是有序的，true就是左边有序
  - 如果是true，说明左边有序，然后看看继续内部在看看target在哪里
  - `if (target >= nums[left] && target < nums[mid])` 大于等于左，小于右，肯定在有序区域，然后进行判断，接下来就是二分法基本操作了。
- `if (nums[left] <= nums[mid])`这个是为了判断到底哪部分是有序的，false就是右边有序
  - 然后接下来也是二分法基本操作

## [153. 寻找旋转排序数组中的最小值](https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/)

```
已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
若旋转 7 次，则可以得到 [0,1,2,4,5,6,7]
注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。

给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。

示例 1：
输入：nums = [3,4,5,1,2]
输出：1
解释：原数组为 [1,2,3,4,5] ，旋转 3 次得到输入数组。
示例 2：
输入：nums = [4,5,6,7,0,1,2]
输出：0
解释：原数组为 [0,1,2,4,5,6,7] ，旋转 4 次得到输入数组。
示例 3：
输入：nums = [11,13,15,17]
输出：11
解释：原数组为 [11,13,15,17] ，旋转 4 次得到输入数组。
 
提示：
n == nums.length
1 <= n <= 5000
-5000 <= nums[i] <= 5000
nums 中的所有整数 互不相同
nums 原来是一个升序排序的数组，并进行了 1 至 n 次旋转
```

### 思路1

暴力解法

- 默认第一个就是最小值，然后一个个对比。遇到更小的就退出。

```java
/**
 * 暴力解法 遍历数组 找到最小值
 * 时间复杂度 O(n)
 * 空间复杂度 O(1)
 *
 * @param nums 数组
 * @return int 最小值
 */
public int findMin(int[] nums) {
    int minVal = nums[0];
    for (int i = 1; i < nums.length; i++) {
        // 找到小的就交换
        minVal = Math.min(minVal, nums[i]);
    }
    return minVal;
}
```

暴力解法2 提前终止（速度并没有快

```java
/**
 * 暴力解法 遍历数组 提前终止，只要找到前一位小的就是最小
 * 时间复杂度 O(n)
 * 空间复杂度 O(1)
 *
 * @param nums 数组
 * @return int 最小值
 */
public int findMin2(int[] nums) {
    for (int i = 1; i < nums.length; i++) {
        // 因为按照厂里后一个应该比前一个大
        // 如果后一个比前一个小了，说明是拐点
        if (nums[i] < nums[i - 1]) {
            return nums[i];
        }
    }
    // 一直都是大的，说明最小的就是第一个
    return nums[0];
}
```

### 思路2 二分法

主要利用的既然是升序，那么就对比中间值mid和最右right，按理说升序mid应该小于right，如果大于了，说明最小值（旋转拐点） 就在mid和right之间，然后就可以用二分法找到了。

```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 本来升序，那么mid对应的值，肯定是大于right
            // 小于的话，说明这里出现了一个旋转拐点
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                // 说明答案有可能是mid和mid之前的
                right = mid;
            }
        }
        // 只剩下一个最后一个，肯定就是最小的了
        return nums[left];
    }
}
```

## [154. 寻找旋转排序数组中的最小值 II](https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array-ii/)

```
已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,4,4,5,6,7] 在变化后可能得到：
若旋转 4 次，则可以得到 [4,5,6,7,0,1,4]
若旋转 7 次，则可以得到 [0,1,4,4,5,6,7]
注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。

给你一个可能存在 重复 元素值的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。

你必须尽可能减少整个过程的操作步骤。

示例 1：
输入：nums = [1,3,5]
输出：1
示例 2：
输入：nums = [2,2,2,0,1]
输出：0

提示：
n == nums.length
1 <= n <= 5000
-5000 <= nums[i] <= 5000
nums 原来是一个升序排序的数组，并进行了 1 至 n 次旋转

进阶：这道题与 寻找旋转排序数组中的最小值 类似，但 nums 可能包含重复元素。允许重复会影响算法的时间复杂度吗？会如何影响，为什么？
```

这一题和153 最大的区别就是，有重复值！！

```
4 4 0 4 4 4 4 4
这种情况下，如果还是按照上面的二分法，你会发现你会错过最小值！
```

### 思路

其实只是多考虑了一个情况

`nums[mid] == nums[right] ` 这种情况下必须一个个向前看

就是上面本来是使用的这个

```java
if (nums[mid] > nums[right]) {
  left = mid + 1;
} else {
  // 这里包含两种情况
  // nums[mid] < nums[right] → 这里OK
  // nums[mid] == nums[right] →  等于的情况下 不可以直接移动，还要看前一个是否小于，1个个移动
  right = mid;
}
```

### 代码实现

```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                // 多了这一种情况来判断重复，就是一步步向前比较，而不是直接移动到right
                right--;
            }
        }
        return nums[left];
    }
}
```

## [852. 山脉数组的峰顶索引](https://leetcode-cn.com/problems/peak-index-in-a-mountain-array/)

```
符合下列属性的数组 arr 称为 山脉数组 ：
arr.length >= 3
存在 i（0 < i < arr.length - 1）使得：
arr[0] < arr[1] < ... arr[i-1] < arr[i]
arr[i] > arr[i+1] > ... > arr[arr.length - 1]
给你由整数组成的山脉数组 arr ，返回任何满足 arr[0] < arr[1] < ... arr[i - 1] < arr[i] > arr[i + 1] > ... > arr[arr.length - 1] 的下标 i 。
 

示例 1：
输入：arr = [0,1,0]
输出：1
示例 2：
输入：arr = [0,2,1,0]
输出：1
示例 3：
输入：arr = [0,10,5,2]
输出：1
示例 4：
输入：arr = [3,4,5,1]
输出：2
示例 5：
输入：arr = [24,69,100,99,79,78,67,36,26,19]
输出：2
 
提示：
3 <= arr.length <= 104
0 <= arr[i] <= 106
题目数据保证 arr 是一个山脉数组

进阶：很容易想到时间复杂度 O(n) 的解决方案，你可以设计一个 O(log(n)) 的解决方案吗？
```

### 

什么是山脉数组？

- 数组长度大于3
- [1,2,3,7,4,2] 依次递增，然后到顶，依次递减。这样就是山脉数组。

### 思路1 暴力

首先这一题是可以暴力解法的，那就是遍历。

![image-20220413230815919](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220413230815919.png)

```java
/**
 * 暴力解法
 *
 * @param arr 数组
 * @return int 峰顶
 */
public int peakIndexInMountainArray(int[] arr) {
    // 遍历起来
    for (int i = 0; i < arr.length - 1; i++) {
        // 一直向上，前一个大于后一个，终止
        if (arr[i] > arr[i + 1]) return i;
    }
    // 一直向上的话，那么峰顶就是最后一个index
    return arr.length - 1;
}
```

### 思路2 二分查找

- 其实本质就是`nums[mid]和nums[mid+1]`进行比较

![image-20220413231858986](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220413231858986.png)

```java
/**
 * 二分查找
 *
 * @param arr 数组
 * @return int 峰顶
 */
public int peakIndexInMountainArray2(int[] arr) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        int mid = left + (right - left) / 2;
        // 中间值 < 中间值+1 说明在走上坡路 左边肯定都不是 排除
        if (arr[mid] < arr[mid + 1]) {
            left = mid + 1;
        } else {
            // 已经在走下坡路，自身可能是峰顶
            right = mid;
        }
    }
    // 最后只剩下一个，那肯定是的 right也对
    return left;
}
```

## [1095. 山脉数组中查找目标值](https://leetcode-cn.com/problems/find-in-mountain-array/)

```
（这是一个 交互式问题 ）
给你一个 山脉数组 mountainArr，请你返回能够使得 mountainArr.get(index) 等于 target 最小 的下标 index 值。
如果不存在这样的下标 index，就请返回 -1。

何为山脉数组？如果数组 A 是一个山脉数组的话，那它满足如下条件：

首先，A.length >= 3
其次，在 0 < i < A.length - 1 条件下，存在 i 使得：
A[0] < A[1] < ... A[i-1] < A[i]
A[i] > A[i+1] > ... > A[A.length - 1]

你将 不能直接访问该山脉数组，必须通过 MountainArray 接口来获取数据：
MountainArray.get(k) - 会返回数组中索引为k 的元素（下标从 0 开始）
MountainArray.length() - 会返回该数组的长度

注意：
对 MountainArray.get 发起超过 100 次调用的提交将被视为错误答案。此外，任何试图规避判题系统的解决方案都将会导致比赛资格被取消。

为了帮助大家更好地理解交互式问题，我们准备了一个样例 “答案”：https://leetcode-cn.com/playground/RKhe3ave，请注意这 不是一个正确答案。
示例 1：
输入：array = [1,2,3,4,5,3,1], target = 3
输出：2
解释：3 在数组中出现了两次，下标分别为 2 和 5，我们返回最小的下标 2。
示例 2：
输入：array = [0,1,2,4,2,1], target = 3
输出：-1
解释：3 在数组中没有出现，返回 -1。

提示：
3 <= mountain_arr.length() <= 10000
0 <= target <= 10^9
0 <= mountain_arr.get(index) <= 10^9
```

这一题首先要读懂题目

![image-20220413232647422](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220413232647422.png)

其实这一题暴力遍历也可以，但还是用二分。

### 思路 二分查找

- 找到哪里到哪里升序，哪里是哪里是降序。找到峰顶。
- 然后先对前面进行二分，找不到，在找后面二分

![image-20220413232937271](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20220413232937271.png)

```java
// 先写个框架
class Solution {
    public int findInMountainArray(int target, MountainArray mountainArr) {
        // 1. 找到峰顶的index
        int peakIndex = searchPeakIndex(mountainArr);
        // 2. 先对前半部分使用二分法进行查找
        int index = binarySearchFrontPart(mountainArr, 0, peakIndex, target);
        if (index != -1) {
            return index;
        }
        // 3. 找不到再对后半部分进行查找
        return binarySearchLatterPart(mountainArr, peakIndex, mountainArr.length() - 1, target);
    }
}
```

然后下面是完整的代码

```java
/**
 * // This is MountainArray's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface MountainArray {
 *     public int get(int index) {}
 *     public int length() {}
 * }
 */
 
class Solution {

    public int findInMountainArray(int target, MountainArray mountainArr) {
        // 1. 找到峰顶的index
        int peakIndex = searchPeakIndex(mountainArr);
        // 2. 先对前半部分使用二分法进行查找
        int index = binarySearchFrontPart(mountainArr, 0, peakIndex, target);
        if (index != -1) {
            return index;
        }
        // 3. 找不到再对后半部分进行查找
        return binarySearchLatterPart(mountainArr, peakIndex, mountainArr.length() - 1, target);
    }

    // 找到峰顶的index
    private int searchPeakIndex(MountainArray mountainArr){
        int left = 0 ,right = mountainArr.length() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (mountainArr.get(mid) < mountainArr.get(mid + 1)) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    // 先对前半部分使用二分法进行查找
    private int binarySearchFrontPart(MountainArray mountainArr, int left, int peakIndex, int target) {
        while (left < peakIndex) {
            int mid = left + (peakIndex - left) / 2;
            if (target > mountainArr.get(mid)) {
                left = mid + 1;
            } else {
                peakIndex = mid;
            }
        }

        if (mountainArr.get(left) == target) return left;
        return -1;
    }

    private int binarySearchLatterPart(MountainArray mountainArr, int peakIndex, int right, int target) {
        while (peakIndex < right) {
            int mid = peakIndex + (right - peakIndex) / 2;
            // 这里第一次写错了，因为忘记后面的是降序的！！！一定不能记错
            // if (target > mountainArr.get(mid))
            if (target < mountainArr.get(mid)) {
                peakIndex = mid + 1;
            } else {
                right = mid;
            }
        }

        if (mountainArr.get(peakIndex) == target) return peakIndex;
        return -1;
    }
}
```

写这个代码有2个需要注意的地方

- 升序和降序要分别进行写代码。
- 前半部分是升序，可以按照以往的二分进行写。但是后面是降序，容易把大小号搞混。
