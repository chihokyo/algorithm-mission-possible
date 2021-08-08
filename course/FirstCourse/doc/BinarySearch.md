# 二分法查找

## 1. 复杂度

首先要明确一点，二分排序法的条件。**必须是有序的！**如果不是有序的，就无法进行二分法查找。所以排序也是二分查找法的**前置条件**。

二分法本身的复杂度就是*O(logn)*级别，如果计算排序时间，那么就是这个级别*O(nlogn)*。但是由于排序一次之后，在多次查找的时候，分担分析，可以无限次使用。

二分查找法的思想在1946年，但是没有bug二分法在1962年才有。因为`mid = (left + right) / 2`会有整型溢出的问题，所以使用 `mid = left + (right - left) / 2`

## 2. 使用递归法

首先使用递归法进行查找，这个二分法特别好理解。唯一比较难理解的就是条件。

Q1：为什么是left**>**right  而不是 **>=**？

这个我在实际测试的时候发现当数组只有1个元素并且目标元素就是这一元素的时候`{2},target=2`。这个时候**>=**的话，直接出来的就是一个错误的结果。LeetCode有一题就是直接用二分法查找的可以做一下试试。

下面是正常在IDEA里写的算法，使用于泛型。

```java
public class BinarySearch {
    private BinarySearch() {

    }

    public static <E extends Comparable<E>> int search(E[] data, E target) {
        return search(data, 0, data.length - 1, target);
    }

    /**
     * 递归实现二分法
     *
     * @param data   数组
     * @param left   左区间
     * @param right  有区间
     * @param target 目标
     * @param <E>    泛型（任何类型都可以）
     * @return int 返回目标所在的index
     */
    private static <E extends Comparable<E>> int search(E[] data, int left, int right, E target) {
        // 这里不能是= 因为在只有1的时候[5],5,这个时候直接就-1了！
        if (left > right) return -1;
        // 防止栈溢出的取中
        int mid = left + (right - left) / 2;
        if (data[mid].compareTo(target) == 0) {
            return mid;
        }
        if (data[mid].compareTo(target) < 0) {
            return search(data, mid + 1, right, target);
        }
        return search(data, left, mid - 1, target);
    }

    public static void main(String[] args) {
        Integer[] nums = {0, 1, 3, 4, 5, 9, 12, 13};
        Integer[] nums2 = {5};
        int result = BinarySearch.search(nums, 9);
        int result2 = BinarySearch.search(nums2, 5);
        System.out.println(result);
        System.out.println(result2);
    }
}

```

### LeetCode题目

```
给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。

示例 1:输入: nums = [-1,0,3,5,9,12], target = 9
输出: 4
解释: 9 出现在 nums 中并且下标为 4

示例 2:输入: nums = [-1,0,3,5,9,12], target = 2
输出: -1
解释: 2 不存在 nums 中因此返回 -1
```

解法

```java
class Solution {
    public int search(int[] nums, int target) {
        return search(nums, 0, nums.length - 1, target);
    }
    private int search(int[] nums, int left, int right, int target) {
        // 这里不能是= 因为在只有1的时候[5],5,这个时候直接就-1了！
        if (left > right) return -1;
        // 防止溢出
        int mid = left + (right - left) / 2;
        if(nums[mid] == target) return mid;
        // mid所在的值大于目标，说明目标在前面
        if(nums[mid] > target) {
            return search(nums, left, mid - 1, target);
        } else {
            return search(nums, mid + 1, right, target);
        }
    }
}
```

## 3. 循环法

非递归的方法，用的是循环缩小范围法。（修改边界）。引进了循环不变量。

`data[left, right]` 内找 target

```java
/**
 * 非递归实现二分法
 * @param data 数组
 * @param target 目标
 * @param <E> 返回泛型
 * @return int 返回目标所在的index
 */
public static <E extends Comparable<E>> int search(E[] data, E target) {
    int left = 0, right = data.length - 1;
    // 说明还有未循环完的，和递归正好想法的条件
    // 循环不变量 data[left, right] 内找target
    while (left <=  right) {
        int mid = left + (right - left) / 2;
        if (data[mid].compareTo(target) == 0) return mid;
        // 小于0 target的值大于mid。想后找。
        if (data[mid].compareTo(target) < 0) {
            left = mid + 1;
        } else {
            // target的值小于mid，向前找。
            right = mid - 1;
        }
    }
    // 全部循环完毕也没有
    return -1;
}
// 直接进行调用
public static void main(String[] args) {
    Integer[] nums3 = {0, 1, 3, 4, 5, 9, 12, 13};
    Integer[] nums4 = {5};
    int result3 = BinarySearch.search(nums, 9);
    int result4 = BinarySearch.search(nums2, 5);
    System.out.println(result3);
    System.out.println(result4);
}
```

这样的话那一道LeetCode的题目解答就是

```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if(nums[mid] == target) return mid;
            if(nums[mid] > target) {
                // mid还要比target大 向前继续找
                right = mid - 1;
            } else {
                // 向后找
                left = mid + 1;
            }
        }       
        return -1; 
    }
}
```

