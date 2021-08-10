# 二分法查找

## 1. 复杂度

首先要明确一点，二分排序法的条件。**必须是有序的！**如果不是有序的，就无法进行二分法查找。所以排序也是二分查找法的**前置条件**。

二分法本身的复杂度就是*O(logn)*级别，如果计算排序时间，那么就是这个级别*O(nlogn)*。但是由于排序一次之后，在多次查找的时候，分担分析，可以无限次使用。

二分查找法的思想在1946年，但是没有bug二分法在1962年才有。因为`mid = (left + right) / 2`会有整型溢出的问题，所以使用 `mid = left + (right - left) / 2`

## 2. 使用递归法

首先使用递归法进行查找，这个二分法特别好理解。唯一比较难理解的就是条件。

**Q1：为什么是left>right  而不是 >=？**

这个我在实际测试的时候发现当数组**只有1个元素**并且**目标元素就是这一元素**的时候`{2},target=2`。这个时候**>=**的话，直接出来的就是一个错误的结果。LeetCode有一题就是直接用二分法查找的可以做一下试试。

下面是正常在IDEA里写的算法，使用于泛型。**递归的时候，最不能忘记的就是中止条件。**

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

### [LeetCode 704. 二分查找](https://leetcode-cn.com/problems/binary-search/)

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

**Q1：为什么mid要写在里面？**

第一次我写的时候 `int mid = left + (right - left) / 2;`写在了外面，是超时了。如果你写在了外面，那么这个mid就是固定的，因为她并不像left和right一样，在while里面被重新赋值了。如果写在了外面，那么就会是固定的。

**Q2：为什么循环条件是 while (left <=  right)?**

这个我也有思考过，为什么要有等于。就是要考虑有且只有1个多情况吧。{5} → 5 这样的时候，初始情况下left和right都是0。如果这个时候没有==的条件判断的话，那么就会出现-1这种情况。

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

要注意的就是`  while(left <= right)`

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

### [LeetCode 非递归实现 215. 数组中的第K个最大元素](https://leetcode-cn.com/problems/kth-largest-element-in-an-array/)

上一次用的递归的方法实现了这一个算法，现在就对比一下就可以了。

要注意就是非递归的循环的话，就一定要注意**while**里面循环条件的书写。

```java
// 递归
public int findKthLargest(int[] nums, int k) {
    Random rnd = new Random();
    return selectK(nums, 0, nums.length - 1, nums.length - k, rnd);
}

private int selectK(int[] nums, int left, int right, int k, Random rnd){
    int p = partition(nums, left, right, rnd);
    if(k == p) return nums[p];
    // 这里的判断最后调用的是自身
    if(k > p) {
        return selectK(nums, p + 1, right, k, rnd);
    } else {
        return selectK(nums, left, p - 1, k, rnd);
    }
}
// 非递归
public int findKthLargest(int[] nums, int k) {
    Random rnd = new Random();
    int len = nums.length;
    int target = len - k;
    int left = 0, right = len - 1;
    while(true) {
        int p = partition(nums, left, right, rnd);
        if (p == target) return nums[p];
        // 这里的判断用的是左右区间范围
        if(p > target) {
            right = p - 1;
        } else {
            left = p + 1;
        }
    }
}
```

## 4. 循环法→循环不变量修改

上一题循环不变量的使用是用了`data[left, right]` ，如果循环不变量的范围是`data[left, right)`。右边是开区间怎么办呢？

- 右区间的边界无需-1 → `right = len`
- while内的循环条件无需考虑== → `while(left <= right)`
- `right`缩小范围临界点  → `right = mid`

### [ 比如LeetCode实现二分法](https://leetcode-cn.com/problems/binary-search/)

```java
class Solution {
    public int search(int[] nums, int target) {
        // 循环法
        // 循环不变量 nums[left,right)
        // right变成了开区间
        int left = 0, right = nums.length;
        // 因为永远是取不到right的，所以也就必要考虑==的情况
        while(left < right) {
            int mid = left + (right - left) / 2;
            if(nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                // 这里的right是开区间的话同理
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        // 一圈啥都没有
        return -1;
    }
}
```

### 比如[SelectK](https://leetcode-cn.com/problems/kth-largest-element-in-an-array/)

非递归的实现

`arr[left+1,i-1]<=v;arr[j+1,right]` → `arr[left+1,i-1]<=v;arr[j+1,right)`

↑ 上面的要注意

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        Random rnd = new Random();
        // 循环不变量 nums[left, right] 变成了 nums[left, right])
        int len = nums.length;
        // 因为右边界是开的
        int left = 0, right = len;
        int target = len - k;
        while(true) {
            int p = partition(nums, left, right, rnd);
            if(p == target) {
                return nums[p];
            } else if (p > target) {
                // 目标更小，在左边
                // 右边界是开的
                right = p;
            } else {
                left = p + 1;
            }
        }
    }

    private int partition(int[] nums, int left, int right, Random rnd) {
        // 传进来的right是开的，所以没必要+1了 生成 arr[l, r) 之间的随机索引
        // 现在因为 r 是开区间，所以 arr[l, r) 中的区间长度是 r - l
        int p = left + rnd.nextInt(right - left);
        swap(nums, left, p);
        // arr[l+1...i-1] <= v; arr[j+1...r) >= v
        // 注意：注释中，arr[j+1...r) 后面也变成了开区间
        // 所以，j 的初始值变成了 r - 1，而不再是 r。
        // 当 j = r - 1 的时候，arr[j + 1, r) 为 arr[r, r)，是一个空区间
        int i = left + 1, j = right - 1;
        while(true) {
            while(i <= j && nums[i] < nums[left]) {
                i++;
            }
            while(j >= i && nums[j] > nums[left]) {
                j--;
            }

            if(i >= j) break;
            swap(nums, i, j);
            i++;
            j--;
        }
        swap(nums, left, j);
        return j;
    }
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

递归的实现

- 关于**右边界**的一切都需要做出改变。

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        Random rnd = new Random();
        int len = nums.length;
        int target = len - k;
        return selectK(nums, 0, len, target, rnd);
    }

    private int selectK(int[] nums, int left, int right, int target, Random rnd){
        int p = partition(nums, left, right, rnd);
        if (p == target) return nums[p];
        if (p > target) {
            // right这个区间是开区间了，以前是p-1，现在直接p
            return selectK(nums, left, p, target, rnd);
        } else {
            return selectK(nums, p + 1, right, target, rnd);
        }
    }


    private int partition(int[] nums, int left, int right, Random rnd) {
        // 传进来的right是开的，所以没必要+1了 生成 arr[l, r) 之间的随机索引
        // 现在因为 r 是开区间，所以 arr[l, r) 中的区间长度是 r - l
        int p = left + rnd.nextInt(right - left);
        swap(nums, left, p);
        // arr[l+1...i-1] <= v; arr[j+1...r) >= v
        // 注意：注释中，arr[j+1...r) 后面也变成了开区间
        // 所以，j 的初始值变成了 r - 1，而不再是 r。
        // 当 j = r - 1 的时候，arr[j + 1, r) 为 arr[r, r)，是一个空区间
        int i = left + 1, j = right - 1;
        while(true) {
            while(i <= j && nums[i] < nums[left]) {
                i++;
            }
            while(j >= i && nums[j] > nums[left]) {
                j--;
            }

            if(i >= j) break;
            swap(nums, i, j);
            i++;
            j--;
        }
        swap(nums, left, j);
        return j;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

