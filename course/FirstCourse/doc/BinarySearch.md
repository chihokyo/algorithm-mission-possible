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

这个证明是还有未循环完的，{5}只有一个的情况下，那么正好也是相等的可以进去。

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

## 5. 二分查找法的各种变种

在实际的运用中，我们不一定要找出那个*target*。有可能是查找各个范围内的一些值。所以就有了下面的一些练习。

#### upper

查找大于target的最小索引。比如查找大于60的最小值。 {23,45,66,70,88,90} 这里面就是66。

运用场景：比如像是找零件，合格的基准上到底有多差。

可以全部遍历，也可以用**二分查找法**

分析一下。搜索范围`arr[left, right] ,right = arr.length`。最要注意的就是这个右边界的right，因为这样不是找到找不到。而是肯定会有一个解，那么本身也有可能是解。

![](https://raw.githubusercontent.com/chihokyo/image_host/master/20210811005335.png)

![image-20210811003133204](/Users/chin/Library/Application Support/typora-user-images/image-20210811003133204.png)

**而当left和right重合的时候，说明就是找到了解，循环就可以结束了。**

实际代码如下

```java
/**
     * 寻找大于 > target的最小值的索引
     *
     * @param data   数组
     * @param target 目标
     * @param <E>    泛型
     * @return 返回index
     */
    public static <E extends Comparable<E>> int upper(E[] data, E target) {
        int left = 0;
        int right = data.length; // 这里需要注意！为什么不-1的意思就是说，数组里面最大的一个值，都target小 {1,3,4} 比如target是5 此时返回的就是这个不存在最右边。不是合法索引，但是是合法解。
        // 在data[left, right] 寻找解，这个问题是一定有解的。
        // 为什么不是等于，因为==就已经证明是有解 相当于是解了
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 证明这个比目标等于或者还小，反正就不是大，不是大于就跟题意要求的不符合 那么左边的都不是解
            if (data[mid].compareTo(target) <= 0) {
                // 和下面的right可以一起来看
                left = mid + 1;
            } else {
                // 证明是大于的，那么有可能这个就是解，也有可能她的右边有解。
                // 所以不能-1
                right = mid;
            }
        }

        return left; // 这里return right也是可以的，因为重合的时候证明2个一样的都是解
    }

```

**Q1：为什么right = data.length， 不用 -1了？**

这一问和Q3其实本质是一样的，因为当一个数组里面{1,2,3,4}，而我们的target是5的时候，证明这个数组最大的都要小于target，那么解就应该在4的右边。也就是`arr[arr.length]`，**虽然这是一个不合法的索引，但却是合法的解。**

**Q2：为什么while (left < right)？，而不是<=？**

因为当left和right重叠的时候，证明这个数字其实就是解了。那么既可以`return left`也可以`return right`。

**Q3：为什么right = mid？**

说明这个数字也是大于target的，自己本身也有可能是合法解，那么当然就是=mid，如果是mid+1，那么很有可能就错过了这个合法解。

#### ceil

- 当数组存在元素，则返回最大索引。
- 如果不存在，则返回upper（也就是大于target的最小值索引）

其实这一题仔细分析，就是 {1,2,3,4,**4**,<u>6</u>,6,7} 如果查找的是4 那么答案就，如果查找的是5，那么答案就是6

数学里面有个浮点运算，上取整，下取整的区别。`ceil(5.0) 5 ceil(5.6) 6`

思路分析：其实就是找到upper，然后index - 1 。如果这个index - 1 就等于我们的target，那么就是这个index，否则就是这个upper。

```java
  /**
     * > target 返回最小值索引
     * == target 返回最大值的索引
     *
     * @param data   数组
     * @param target 目标
     * @param <E>    泛型
     * @return 目标的索引
     */
    public static <E extends Comparable<E>> int ceil(E[] data, E target) {
        int u = upper(data, target);
        // 注意点1 容易忽略这个u-1是否是合法的
        if (u - 1 >= 0 && data[u - 1].compareTo(target) == 0) {
            return u - 1;
        }
        return u;
    }
```

**这一题最关键的就是要注意这个范围是不是合法的，第一次写的时候忽略了**

#### lower

小于target的最大值。例如：{34,50,66,99,100} 比如我们要找，查找小于60的最大值。（不及格的最高分是多少，其实答案就是60）

这个可以参考的就是upper，和upper有异曲同工之妙。但是一定要注意的是边界问题。

- 左边界 → 有可能最后我们给的数组没有一个是小于target的，例如我们target是20。那么索引的答案就是-1，**虽然没有-1这个索引，但确实是合法解**！`if(arr[mid] < target) left = mid`

- 右边界 → 如果在所有的数字都小于targe的时候，这个右边界，肯定是解。比如上面target是1000，那么答案就是100。然后`if(arr[mid] >= target) right = mid - 1`

搜索范围 `arr[left, right]`

```java
/**
     * < target的最大值索引
     *
     * @param data   数组
     * @param target 目标
     * @param <E>    泛型
     * @return int 最大值的索引
     */
public static <E extends Comparable<E>> int lower(E[] data, E target) {
    int left = -1;
    int right = data.length - 1;
    while (left < right) {
        int mid = left + (right - left + 1) / 2;
        // 这里就是肯定不是小于的，那么就是大于等于，那么这个mid肯定不是解
        // 大于等于目标，肯定就向左找了。
        if (data[mid].compareTo(target) < 0) {
            right = mid - 1;
        } else {
            // 小于目标，说明这个mid也有可能是解。
            left = mid;
        }
    }

    return left; // 返回right也可以
}
public static void main(String[] args) {
        // 测试lower
        Integer[] arr3 = {1, 1, 3, 3, 5, 5};
        for (int i = 0; i <= 6; i++) {
            System.out.print(BinarySearch.lower(arr3, i) + " ");
        }
        System.out.println();

    }
```

测试上面的lower，发现了死循环。

#### 为什么会有死循环？

可以打印出来left和right会发现。当left和right相邻的时候，搜索空间没有变化会进入死循环。

`int mid = left + (right - left) / 2;`  → 当这里相邻只有1的时候，搜索空间不会缩小，搜索不会变会陷入死循环。结果一直会是left。因为本质上，其实应该是 left + (1/2) ，应该是0.5的差距，但是计算机在计算整型的时候，会自动给吞掉。在右边界缺不会发生这个问题，因为从上面的代码可以看到，`right = mid. - 1`。这个right肯定是一定会有变化的。

解决方案。**当相邻的情况下，取右边界，而不是左边界！**！

```
mid = left + (right - left + 1) / 2 // 保证了相邻情况下会取整
```

#### upper_ceil

查找大于target的最小索引.等于的时候取最大值 → 本质上就是上面的ceil，参考一下。

#### upper_floor

边界判断！

```java
/**
 * 存在元素返回最大索引，不存在返回lower
 * 本质 <= target最大索引
 *
 * @param data   数组
 * @param target 目标
 * @param <E>    泛型
 * @return int 索引
 */
public static <E extends Comparable<E>> int upper_floor(E[] data, E target) {

    int left = -1, right = data.length - 1;
    while (left < right) {
        int mid = left + (right - left + 1) / 2;
        // mid大于等于target 反正是不符合<=
        if (data[mid].compareTo(target) > 0) {
            right = mid - 1;
        } else {
            // 大于等于，说明有可能是解
            left = mid;
        }
    }

    return left;
}
```

#### lower_ceil

求>=target最小值的索引，其实这一题跟upper，很相似，就是多了一个==。写代码的时候建议抛开upper什么的，直接想。

- 左右区间范围是什么
- 判断的依据是什么

```java
public static <E extends Comparable<E>> int lower_ceil(E[] data, E target) {
        // 本质上其实和upper有异曲同工之妙
        int left = 0, right = data.length; // 最右边也可能是合法解
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 最重要的一点，就是这里判断的就是小于0 意思就是说target在右边，也就是大
            // 这时候说明data[mid]的mid索引，包括mid，左边的都是没用的
            // 和上面的思考最大的不同的就是判断=的时候，=这个情况在下面判断了，如果=,也就是说这个mid所在的索引也有可能是正解
            if (data[mid].compareTo(target) < 0) {
                mid = left + 1; // 都是没用的 何必还要，直接+1
            } else {
                right = mid; // 这里说明从>=都是有可能的
            }
        }

        return left;

    }
```

#### lower_floor

使用>=target这个思想。写一个二分查找法。判断是否存在就可以。

如果找到这个值，看和target是否相同，一样返回，不一样就没有。

```java
/**
 * 存在元素返回最小索引，不存在返回lower
 * < target ，返回最大值索引
 * == target，返回最小索引
 *
 * @param data   数组
 * @param target 目标
 * @param <E>    泛型
 * @return int 索引
 */
public static <E extends Comparable<E>> int lower_floor(E[] data, E target) {
    // 这里求的是< target的最大值索引 所以合法解会是【越界】的
    int l = lower(data, target);
    // 注意，因为我们要访问 data[left + 1]，所以，我们要先确保 left + 1 不越界
    if (l + 1 < data.length && data[l + 1].compareTo(target) == 0) {
        return l + 1;
    }
    return l;
}
```

总结一下吧。

| 名字不是重点 | 这里是重点                | 名字不是重点 | 名字不是重点              |
| ------------ | ------------------------- | ------------ | ------------------------- |
| upper        | > target的最小值          | lower        | < target的最大值          |
| upper_ceil   | ==的时候取大，没有取upper | upper_floor  | ==的时候取大，没有取lower |
| lower_ceil   | ==的时候取小，没有取upper | lower_floor  | ==的时候取小，没有取lower |

#### 可以总结的二分查找法模板

```java
// ①左右边界初始很重要
int left = X, right = X;
while (left < right) {
    int mid = left (right - left + X) / 2; // ②上下取整看情况
    if(data[mid].compareTo(target) X 0) { // ③判断大小看情况
        XXXX; // ④缩小哪里的范围
    } else {
        XXXX; // ④缩小哪里的范围
    }
    return left;// or right
}
```

上面的模板还可以实现查找是否存在

**Q：用>= target的最小值实现二分查找法**

使用求解 >= target 的最小值索引的思路，实现 search

在有序数组 data 中判断 target 是否存在，存在则返回相应索引，不存在则返回 -1

```java
/**
 * 在有序数组 data 中判断 target 是否存在，存在则返回相应索引，不存在则返回 -1
 * 其实就是另一种二分法求目标在不在的方式
 *
 * @param data   数组
 * @param target 目标
 * @param <E>    泛型
 * @return int 存在返回index,不在-1
 */
public static <E extends Comparable<E>> int searchBy(E[] data, E target) {
    int left = 0, right = data.length;
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (data[mid].compareTo(target) < 0) {
            left = mid + 1;
        } else {
            right = mid;
        }
    }
    // 注意这个判断
    if (left < data.length && data[left].compareTo(target) == 0) {
        return left;
    }
    return -1;
}
```

### 一些LeetCode系列题目

