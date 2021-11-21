# 算法日记

第一天开始写。现在开始写。反正早晚也会鸽掉，持续不下去的说。

## 数组

这里如果统一数组内元素出现的个数。要注意这里的索引就是

arr[ 对应的元素值 - 1] 

- 注意这里只能应对数组的范围很小的情况，不然很大的话，根本也没那么多空间。

![image-20211119153443372](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119153443372.png)

```java
// 方法1 使用Map
private static Map<Integer, Integer> countArray1(int[] arr) {
  // key 代表元素 value 代表次数
  Map<Integer, Integer> countMap = new HashMap<>();
  // 遍历数组
  for (int num: arr) {
    // 是否含有这个元素，没有就加入到结果集，有就count+1
    // 有
    if (countMap.containsKey(num)) {
      int count = countMap.get(num);
      countMap.put(num, count + 1);
    } else {
      // 没有
      countMap.put(num, 1);
    }
  }
  return countMap;
}

// 方法2 使用静态数组
private static int[] countArray2(int[] arr) {
    // 这里就是按照数组的大小 默认就是6
    int[] countMap = new int[6];
    for (int num: arr) {
        int index = num - 1;
        countMap[num]++;
    }
    return countMap;
}
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

#### 思路

![image-20211119154117506](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119154117506.png)

于是就要解决。**如何不适用新的空间来进行计数呢**？

使用下面的负数方法思路

![image-20211119155241371](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119155241371.png)

![image-20211119155438214](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119155438214.png)

继续向前走，又会发现-3的绝对值3，所对应的索引-2的值是-2，也是一个负数。这个时候也加入结果集。最后的结果

*res = [2, 3]* 这样的空间复杂度也就是O(1)

#### 代码实现

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

这里用的是加倍方法，遇到就加倍。比较难理解的是稍微有点绕着圈。

#### 思路

![image-20211119165333213](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119165333213.png)

继续接着向下走

![image-20211119165535461](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119165535461.png)

接着向前走，即使第二次出现之后也继续+，那怎么确保最后找到重复2次的呢？

最后数字只要是大于2倍长度的，因为1次+,2次+2n

![image-20211119165721955](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211119165721955.png)

所以最后的重要的就是

- 遇到了 + n（长度）
- 最后判断是否重复就是2n

#### 代码实现

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

## [LeetCode - 448. 找到所有数组中消失的数字](https://leetcode-cn.com/problems/find-all-numbers-disappeared-in-an-array/)

```
给你一个含 n 个整数的数组 nums ，其中 nums[i] 在区间 [1, n] 内。请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，并以数组的形式返回结果。

示例 1：
输入：nums = [4,3,2,7,8,2,3,1]
输出：[5,6]
示例 2：
输入：nums = [1,1]
输出：[2]

提示：
n == nums.length
1 <= n <= 105
1 <= nums[i] <= n
进阶：你能在不使用额外空间且时间复杂度为 O(n) 的情况下解决这个问题吗? 你可以假定返回的数组不算在额外空间内。
```

这一题和上面那一题思路很像，但是无法用负数的那个思路，因为这不是寻找重复，而是寻找消失，如果还按照负数那种思路的话，2次出现，那就是2次变成负数，负负得正。

但是却可以使用+n翻倍的思路

![image-20211120153752504](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211120153752504.png)

按照加倍的思路写下来之后

![image-20211120154939992](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211120154939992.png)

上面就是一组，index代表是的值 - 1对应的index

```java
class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        // 思路就是用的加倍，<=n就是符合条件的
        int len = nums.length;
        for (int i = 0; i < len; i++) {
          	// 找index，index就是值-1 取模
            int index = (nums[i] - 1) % len;
	          // index所对应的值要+len
            nums[index] += len;
        }
        // 一轮过后，基本已经加倍完成
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            if (nums[i] >= 1 && nums[i] <= len) {
                // 说明是符合条件的，那么这里的i其实就是值-1之后的
	              // 所以结果需要+1
                res.add(i + 1);
            }
        }
        return res;
    }
}
```

## 字符串

字符串本质上就是一个数组。其实就是字符类型的数组。感觉上就是

*Array[Char]  = String*

其实char和int也可以转换的。

```java
char char1 = 'p';
int int1 = char;
System.out.println("ASCII value of "+char1+" -->"+int1); 
// ASCII value of p -->112
```

所以统计一下下面字符出现的次数的话，就可以这样来写。

![image-20211120155604349](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211120155604349.png)