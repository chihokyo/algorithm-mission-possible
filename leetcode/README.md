# 每日打卡

打卡只为自己记录点点滴滴。

| 日期      | 题目 | 心得 |
| --------- | ---- | ---- |
| 2021.9.30 |      |      |
|           |      |      |
|           |      |      |

## 2021.9.30

### [剑指 Offer 53 - I. 在排序数组中查找数字 I](https://leetcode-cn.com/problems/zai-pai-xu-shu-zu-zhong-cha-zhao-shu-zi-lcof/)

Java暴力穷举

```java
class Solution {
    public int search(int[] nums, int target) {
        // 初始化次数
        int count = 0;
        // 开始遍历
        for(int num : nums) {
            // 遇到相同的次数增加1
            if(num == target) {
                count++;
            }
        }
        return count;
    }
}
```

但是既然看到了是在排序数组中查找的话，那么还是首先要想到**二分法**

其实本质就是这样的。寻找2个边界之后**相减** - 1

比如下面8重复有3个，左边界是1，右边界是5，结果就是 `5 - 1 - 1 = 3`

![Picture1.png](https://pic.leetcode-cn.com/b4521d9ba346cad9e382017d1abd1db2304b4521d4f2d839c32d0ecff17a9c0d-Picture1.png)

```java
```

