# 算法日记

## 二分查找相关

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



