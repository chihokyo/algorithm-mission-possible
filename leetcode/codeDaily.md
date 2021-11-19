# 算法日记

第一天开始写。现在开始写。反正早晚也会鸽掉，持续不下去的说。

## 数组

这里如果统一数组内元素出现的个数。要注意这里的索引就是

arr[ 对应的元素值 - 1] 

- 注意这里只能应对数组的范围很小的情况，不然很大的话，根本也没那么多空间。

![image-20211119153443372](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119153443372.png)

```java
```

## [LeetCode - 422 数组中重复的数据](https://leetcode-cn.com/problems/find-all-duplicates-in-an-array/)

```
给定一个整数数组 a，其中1 ≤ a[i] ≤ n （n为数组长度）, 其中有些元素出现两次而其他元素出现一次。
找到所有出现两次的元素。
你可以不用到任何额外空间并在O(n)时间复杂度内解决这个问题吗？

示例：
输入:
[4,3,2,7,8,2,3,1]
输出:
[2,3]
```

因为给了范围，所以可以直接对每一个数组进行计数（上面的铺垫），这样以后为2的就可以返回。但是就不符合题目的不需要额外空间了。

### 解法1 负数

![image-20211119154117506](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119154117506.png)

于是就要解决。**如何不适用新的空间来进行计数呢**？

使用下面的负数方法、

![image-20211119155241371](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119155241371.png)

![image-20211119155438214](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119155438214.png)

继续向前走，又会发现-3的绝对值3，所对应的索引-2的值是-2，也是一个负数。这个时候也加入结果集。最后的结果

*res = [2, 3]* 这样的空间复杂度也就是O(1)

代码实现

*Java&JavaScript&Python3*

```java
class Solution {
    public List<Integer> findDuplicates(int[] nums) {
       List<Integer> res = new ArrayList<>(); // 结果集不算额外空间
        for (int i = 0; i < nums.length; i++) {
            // 这里必须取绝对值，有可能索引为负数
            int index = Math.abs(nums[i]) - 1;
            // 说明已经出现过了1次
            if (nums[index] < 0) {
                // 这里也必须取绝对值，不然索引是负数
                // 原本的值添加到结果集
                res.add(Math.abs(nums[i]));
            } else {
                // 说明没有出现过，反转为负数
                // 需要把索引对应的值翻转
                nums[index] = -nums[index];
            }
        }
        return res;
    }
}
```

```javascript
/**
 * @param {number[]} nums
 * @return {number[]}
 */
var findDuplicates = function(nums) {
    const res = [];
    for(let i = 0; i < nums.length; i++) {
        const index = Math.abs(nums[i]) - 1
        if (nums[index] < 0) {
            res.push(Math.abs(nums[i]));
        } else {
            nums[index] = -nums[index];
        }
    }
    return res;
};
```

```python
class Solution:
    def findDuplicates(self, nums: List[int]) -> List[int]:
        res = []
        for x in nums:
            index = abs(x) - 1
            if nums[index] < 0:
                res.append(abs(x))
            else:
                nums[index] = -nums[index]
        return res
```

### 解法2 还可以n倍

![image-20211119165333213](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119165333213.png)

继续接着向下走

![image-20211119165535461](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119165535461.png)

接着向前走，即使第二次出现之后也继续+，那怎么确保最后找到重复2次的呢？

最后数字只要是大于2倍长度的，因为1次+,2次+2n

![image-20211119165721955](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119165721955.png)

所以最后的重要的就是

- 遇到了 + n（长度）
- 最后判断是否重复就是2n

```java
class Solution {
    public List<Integer> findDuplicates(int[] nums) {
       int len = nums.length;
        // 第一次遍历 重复的加倍
        for (int i = 0; i < len; i++) {
            // 由于这里可能是已经加倍过的，为了找到原来的index 需要这样计算
            // 注意这里的值要-1，因为初始化的时候index比实际的值要-1，取模当然也要-1
            int index = (nums[i] - 1) % len;
            nums[index] += len;
        }
        // 第二次遍历 找到结果
        List<Integer> res = new ArrayList<>();
      	// 这个时候的获得数组，index是值-1，value是加倍or不加倍，判断用的
        for (int i = 0; i < len; i++) {
            // 高于总长度2倍的，说明加了2次，也就是重复了2次
	          // 加入结果集的是值，而不是索引 
            // ∵ 但这个时候的索引其实就是值↓
            // ∴ 索引+1=值
            if (nums[i] > 2 * len) res.add(i + 1);
        }
        return res;
    }
}
```

