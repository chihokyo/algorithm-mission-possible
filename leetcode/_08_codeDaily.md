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

