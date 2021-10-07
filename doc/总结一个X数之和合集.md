# 两数之和的合集

## 题目

- [1. 两数之和](https://leetcode-cn.com/problems/two-sum/)
- [167. 两数之和 II - 输入有序数组](https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/)
- [653. 两数之和 IV - 输入 BST](https://leetcode-cn.com/problems/two-sum-iv-input-is-a-bst/)
- [15. 三数之和](https://leetcode-cn.com/problems/3sum/)
-  [18. 四数之和](https://leetcode-cn.com/problems/4sum/)


## 解题开始                                                

### [1. 两数之和](https://leetcode-cn.com/problems/two-sum/)

#### 暴力解法

- 2个遍历
- 如果`target - nums[i] ==  值` 说明找到了

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        // 特殊情况 如果为空or长度为1 直接就是[0]
        if (nums == null || nums.length == 0) return new int[0];
        // 开始遍历从i开始
        for(int i = 0; i < nums.length; i++) {
            // 从i+1的地方开始
            for(int j = i + 1; j < nums.length; j++) {
                // 看是否相等
                if(target - nums[i] == nums[j]) {
                    return new int[]{i, j};
                }
            }
        }
        // 到最后还是没有符合条件的
        return new int[0];
    }
}
```

#### 二分查找

首先二分查找只能适用于**有序数组**，这一题并不是有序数组。那就让她成为有序数组。

不是有序数组？ → 让她成为有序数组 → 有序数组之后索引丢失了！ → 弄个哈希专门用来存储索引 👇🏻

典型的用空间换时间！但其实空间复杂度n了，时间复杂度nlongn了

![image-20211007230723237](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211007230730.png) 

所以放弃了。接下来继续看哈希表的。

#### 哈希表优化1

其实就是对数据进行预处理，先遍历，放入哈希表里。key是值，value就是索引。

- 特殊情况判断
- 初始化哈希表 遍历 key是值，value就是索引
- 然后再次遍历 看 target - 值 得出的结果是否是哈希表的key，如果有这个key，就看这个key对应的value。这个key对应的value就是**索引**
- 这里最重要的**根据题意**，一个索引不能用2次。
- 时间和空间复杂度都是O（n）

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        // 特殊情况 如果为空or长度为1 直接就是[0]
        if (nums == null || nums.length == 0) return new int[0];
        // 数组预处理
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            // 这里key是数组的值，value是索引
            map.put(nums[i], i);
        }
        // 再次遍历
        for(int i = 0; i < nums.length; i ++) {
            // 找到这个值
            if(map.containsKey(target - nums[i])) {
                // 通过这个值，找到她对应的索引
                int index = map.get(target - nums[i]);
                // 这里必须要判断一下，i是不是index。不然可能是重复的
                if (i != index ) return new int[]{i, index};
            }
        }
        
        return new int[0];
    }
}
```

#### 哈希表优化2

上面其实用了2次循环。这样效率不高。可以把2个循环合成1个。

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        // 特殊情况 如果为空or长度为1 直接就是[0]
        if (nums == null || nums.length == 0) return new int[0];
        // 数组预处理
        HashMap<Integer, Integer> map = new HashMap<>();
        // 开始遍历
        for(int i = 0; i < nums.length; i ++) {
            // 找到这个值
            if(map.containsKey(target - nums[i])) {
                // 通过这个值，找到她对应的索引
                int index = map.get(target - nums[i]);
                // 这里就不用判断了，因为只有put是最后放进去的。这个时候肯定不会把自身放进去
                // if (i != index ) return new int[]{i, index};
                return new int[]{i, index};
            }
            map.put(nums[i], i);
        }
        
        return new int[0];
    }
}
```

### [167. 两数之和 II - 输入有序数组](https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/)

这一题最大的不一样就是。

- index是从1开始
- 输出的是值，而不是index
- 数组本身是有序的

#### 暴力解法

```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        for(int i = 0; i < numbers.length; i++) {
            for(int j = i + 1; j < numbers.length; j++) {
                if(target - numbers[i] == numbers[j]) {
                    return new int[]{++i, ++j};
                }
            }
        }
        return new int[0];
    }
}
```

