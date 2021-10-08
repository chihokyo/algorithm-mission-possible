## 两数之和的合集

## 题目

- [1. 两数之和](https://leetcode-cn.com/problems/two-sum/)
- [167. 两数之和 II - 输入有序数组](https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/)
- [653. 两数之和 IV - 输入 BST](https://leetcode-cn.com/problems/two-sum-iv-input-is-a-bst/)
- [15. 三数之和](https://leetcode-cn.com/problems/3sum/)
-  [18. 四数之和](https://leetcode-cn.com/problems/4sum/)


## 解题开始                                                

### [【1】1. 两数之和](https://leetcode-cn.com/problems/two-sum/)

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

![image-20211008151111126](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211008151112.png)

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

## [【2】167. 两数之和 II - 输入有序数组](https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/)

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

#### 二分搜索法

既然是有序，那么就一定可以使用二分法来解决问题。这一题怎么解决呢。

可以先固定第一个值，然后 `target - 第一个值 = 要找的值`，要找的那个值就是会用二分搜索法看看能不能找到就可以了。

- 第一次循环，固定值
- 要找的值调用二分搜索法
- 找到了就直接记录下索引就好

时间复杂度：`O(nlogn)` → 因为线性是n，二分搜索是logn。

空间复杂度：`O(1)` → 没有开辟多余空间

```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        for(int i = 0; i < numbers.length; i++) {
            // 这里的左边界记得+1，因为numbers[i]自身不在范围内
            int x = binarySearch(numbers, i + 1, numbers.length - 1, target - numbers[i]);
            if( x != -1){
                return new int[]{i + 1, x + 1};
            }
        }
        return new int[0];
    }

    // 二分查找法 数组 左边界 右边界 目标值
    private int binarySearch(int[] numbers, int left, int right, int target) {
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if(numbers[mid] == target) {
                return mid;
            } else if (numbers[mid] > target) { // 目标更小
                right = mid - 1;
            } else { // 目标更大
                left = mid + 1;
            }
        }
        return -1;
    }
}
```

#### 双指针

可以看到上面的的速度还是有点慢，`O(nlogn) `。那么还有什么方法呢。那就是双指针。双指针基本上时间和空间都可以同时↓

![image-20211008152017914](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211008152019.png)

先写代码实现，在写注意点。

```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int left = 0; // 指针1 左边界
        int right = numbers.length - 1; // 指针2 右边界
        // 这里为什么是<而不是<=  因为这里如果left=right 是不符合题意的，自身没办法用2次
        while(left < right) {
            int sumValue = numbers[left] + numbers[right];
            if(sumValue == target) {
                return new int[]{left + 1, right + 1};
            } else if (sumValue > target) { // 目标更小 右边--
                right--;
            } else { // 目标更大 左边++
                left++;
            }
        }
        return new int[0];
    }
}
```

**Q:为什么是left < right 而不是left <= right?**

因为如果==的话，就可以发现左右边界重叠。这个时候即使符合条件也就说明是自身用了2次。不符合题意。比如target是6 数组，[1,2,3]。结果的索引不可能是[2,2]！

## [【3】653. 两数之和 IV - 输入 BST](https://leetcode-cn.com/problems/two-sum-iv-input-is-a-bst/)

首先这一题的题意就是和上面不同的就是有俩

- 数组结构是一个二叉树
- 输出的要求是Boolean。就是true或者false就可以。

#### 双指针

首先根据二叉查找树的特性，左边都是小，右边都是大。可以使用**中序遍历**，让它成为一个**排序的一维数组**。

- 初始化数组 
- 中序遍历→ 排序数组
- 和上面的【2】一样，使用双指针缩小范围。
- 返回结果

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean findTarget(TreeNode root, int k) {
        if(root == null) return false; // 极值判断
        List<Integer> nums = new ArrayList<>(); // 新建数组集合
        inOrder(root, nums); // 开始中序遍历
        // 到这里就得到了一个一维的数组集合
        int left = 0;
        int right = nums.size() - 1;
        while(left < right) {
            int sumVal = nums.get(left) + nums.get(right);
            if (sumVal == k) {
                return true;
            } else if (sumVal > k) { // 目标更小
                right--;
            } else {
                left++;
            }
        }
        return false;
    }

    private void inOrder(TreeNode node, List<Integer> nums) {
        if (node == null) return;
        inOrder(node.left, nums);
        nums.add(node.val); // 中序遍历，添加
        inOrder(node.right, nums);
    }
}
```

#### BST哈希查找递归

这个可能刚开始会难以理解到底怎么实现的，差不多就应该是这样的。

寻找小树，看有没有能匹配的。比如固定5，target - 固定的5 = 你要找的值

如果找到了就你要找的就直接true，找不到就直接放在set的集合里。留着下一次找。

比如下面这个小举例。`[5,3,8]，目标是13` 这个从5开始找，此时集合还是空的，什么都没。所以添加到set里。然后从左子树开始找，此时没找到 10（13-3=10），所以从右子树可以找。找到了8（13-5=8）！

![image-20211008143838268](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211008143840.png)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    // 如果这个递归解法理解起来很困难。可以用[5, 3, 8];这样一个小树做类比
    public boolean findTarget(TreeNode root, int k) {
        if(root == null) return false;
        // 根节点 目标 哈希集合
        return findRecur(root, k, new HashSet<>());
    }
    // 递归查找目标
    private boolean findRecur(TreeNode node, int k, HashSet<Integer> set) {
        if(node == null) return false; // 递归终止条件
        if(set.contains(k - node.val)) return true; // 如果包含了直接就是true
        set.add(node.val); // 不包含就添加到集合了
        // 然后递归调用左右子树
        return findRecur(node.left, k, set) || findRecur(node.right, k, set); 
    }
}
```

## [【4】15. 三数之和](https://leetcode-cn.com/problems/3sum/)

这一题和上面的不同就是

- 三数加起来是0
- 无序的
- 返回是一个包含**数组结果**的集合

这一题首先如果用暴力的解法，会发现超时了！！因为循环一次是n，两次就是n<sup>2</sup>。那么三次就是n<sup>3</sup>。这样的效率实在是太低了。但其实暴力解法也是有个坑的。

#### 暴力解法 ❌

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        // 极值判断，不为null，且要大于3个
        if(nums == null || nums.length < 3) return new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>(); // 新建一个结果集[[],[]...]
        for(int i = 0; i < nums.length - 3; i++) {
            for(int j = i + 1; j < nums.length - 3; j++) {
                for(int k = j + 1; k < nums.length - 3; k++) {
                    if(nums[i] + nums[j] + nums[k] == 0) {
                        // 数组 → 数组集合
                        res.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    }
                }
            }
        }
        return res;
    }
}
```

这里的问题有个坑就是，不能保证你的结果集res里面的所有数组是没有重复的！！所以需要去重。

所以正确的暴力解法应该是新建一个Set结果集，但是这个时候又出现个问题。就是顺序有可能是不一样的。

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        // 极值判断，不为null，且要大于3个
        if(nums == null || nums.length < 3) return new ArrayList<>();
        // List<List<Integer>> res = new ArrayList<>(); 错误的！ 新建一个结果集[[],[]...]
        Set<List<Integer>> res = new HashSet<>();
        Arrays.sort(nums); // 要排序，不然会有重复！O(nlogn) 
        for(int i = 0; i < nums.length; i++) {
            for(int j = i + 1; j < nums.length; j++) {
                for(int k = j + 1; k < nums.length; k++) {
                    if(nums[i] + nums[j] + nums[k] == 0) {
                        // 数组 → 数组集合
                        res.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    }
                }
            }
        }
        return new ArrayList<>(res);
    }
}
```

上面的代码虽然是正确的，但是超过了时间限制。

#### 哈希Set + 双指针

**三数之和如何转换成两数之和？**

数字1 + 数字2 + 数字3 ==  0

↓

数字2 + 数字3 = （-数字1）

这样就OK了！！！

![image-20211008161101165](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211008161102.png)

这样就成了一个双数之和。

注意2个点

- target是负数的-i
- 左边界是i+1 → 因为i不算的

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        if(nums == null || nums.length < 3) return new ArrayList<>();

        Set<List<Integer>> res = new HashSet<>(); // 初始化Set结果集
        Arrays.sort(nums); // 排序 O(nlogn)
        // 这里有2个循环 O(n^2)
        for(int i = 0; i < nums.length; i++) {
            int target = -nums[i];
            int left = i + 1, right = nums.length - 1;
            while(left < right) {
                if (nums[left] + nums[right] == target) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right])); // 加入到了
                    // 可能还有答案要继续
                    left++;
                    right--;
                } else if (nums[left] + nums[right] > target) { // target更小
                    right--;
                } else {
                    left++;    
                }
            }
        }
        return new ArrayList<>(res);
    }
}
```

#### List + 双指针 +去重

上面的用的是Set这样就自然去重了，如果我们自己去重呢。

主要有三点，i去重，left去重，right去重！

![image-20211008162926048](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211008162927.png)

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        if(nums == null || nums.length < 3) return new ArrayList<>();

        List<List<Integer>> res = new ArrayList<>(); // 初始化List结果集
        Arrays.sort(nums); // 排序 O(nlogn)
        // 这里有2个循环 O(n^2)
        for(int i = 0; i < nums.length; i++) {
            if(i > 0 && nums[i] == nums[i - 1]) continue; // 这里也要去重 发现重复的直接跳过
            int target = -nums[i];
            int left = i + 1, right = nums.length - 1;
            while(left < right) {
                if (nums[left] + nums[right] == target) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right])); // 加入到了
                    // 去重操作
                    // 如果前后数值一样，那么就继续缩小范围
                    while(left < right && nums[left] == nums[++left]);
                    while(left < right && nums[right] == nums[--right]);
                } else if (nums[left] + nums[right] > target) { // target更小
                    right--;
                } else {
                    left++;    
                }
            }
        }
        return res;
    }
}
```

写法里需要注意的是

```java
while(left < right && nums[left] == nums[++left]);
```

这个就相当于

```java
while (left < right) {
    // 不管前后相不相等，left 都要往前走
    left++;
    if (nums[left - 1] != nums[left]) break;
}
```

++left就是先自身然后在判断！！

## 【5】[18. 四数之和](https://leetcode-cn.com/problems/4sum/)

这一题和上面的三数之和的最大区别就是！！！固定2个~其他的全部一样

![image-20211008164407714](https://raw.githubusercontent.com/chihokyo/image_host/develop/20211008164413.png)

#### List双指针

```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if(nums == null || nums.length < 4) return new ArrayList<>(); // 极值判断
        Arrays.sort(nums); // 排序
        List<List<Integer>> res = new ArrayList<>(); // 初始化结果数组
        // i肯定取不到后3个，不然也就不满足4个元素了
        for (int i = 0; i < nums.length - 3; i++) {
            // 去重操作
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            // j肯定取不到后2个，不然也就不满足4个元素了
            for (int j = i + 1; j < nums.length - 2; j++) {
                // 去重操作
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                int partSum = nums[i] + nums[j];
                int left = j + 1;
                int right = nums.length - 1;

                while (left < right) {
                    int sum = nums[left] + nums[right] + partSum;
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while(left < right && nums[left] == nums[++left]);
                        while(left < right && nums[right] == nums[--right]);
                    } else if (sum > target) { // 目标更小
                        right--;
                    } else { // 目标更大
                        left++;
                    }
                }
            }
        }
        return res;
    }
}
```

## 总结

暂时需要梳理的很多，

- 暴力 → 有序 → 有序之后可以二分法 or 可以双指针
- 去重既可以用Set数据结构，也可以自我判断重复！
