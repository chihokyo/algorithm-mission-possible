# 算法日记

第一天开始写。现在开始写。反正早晚也会鸽掉，持续不下去的说。

解决了以下几个问题

```
数组
字符串（本质上也是数组）
双指针
快慢指针
对撞指针
前缀和&积
```

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

根据上面写的，看题。

## [LeetCode -1002. 查找共用字符](https://leetcode-cn.com/problems/find-common-characters/)



```
给你一个字符串数组 words ，请你找出所有在 words 的每个字符串中都出现的共用字符（ 包括重复字符），并以数组形式返回。你可以按 任意顺序 返回答案。

示例 1：
输入：words = ["bella","label","roller"]
输出：["e","l","l"]
示例 2：
输入：words = ["cool","lock","cook"]
输出：["c","o"]

提示：
1 <= words.length <= 100
1 <= words[i].length <= 100
words[i] 由小写英文字母组成
```

### 思路

![image-20211122011353879](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211122011353879.png)

数组长度是m，每一个字符长度是n，那么就是100*100=10000，所以这个时间复杂度是可以的

那么接下来的思路就是

- 首先统计一下第一个字符串分别出现的次数
- 以第一个字符串各个字母出现的次数统计接下来的结果
- 最后取得每个字符**最小出现次数**

![image-20211122012303723](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211122012303723.png)

### 代码实现

```java
class Solution {
    public List<String> commonChars(String[] words) {
        // 求第一个字符串的出现次数 初始化数组
        int[] minFreq = new int[26];
        for (char c : words[0].toCharArray()) {
            minFreq[c - 'a']++;
        }
        // 求接下来字符串出现的次数
        // 从第2个开始，所以i是1，然后长度
        for (int i = 1; i < words.length; i++) {
            // 这里初始化一个统计次数数组，用来跟最小次数数组对比
            int[] freq = new int[26];
            // words[i]这里还是一个String需要转换成数组
            for(char c : words[i].toCharArray()) {
                // 统计次数
                freq[c - 'a']++;
            }
            // 走到这里应该有2个数组了，1个记录最小的，1个记录当前字符串的，因为要取得最小的，所以要对比
            for (int j = 0; j < 26; j++) {
                minFreq[j] = Math.min(minFreq[j], freq[j]);
            }
        }
        // 走到这里应该会获取一个长度为26的最小次数数组，但是题目要的是输出的结果集是String的list key是a,b,c,d,e，v是次数
        
        List<String> res = new ArrayList<>();
        // 第一层循环26次，代表就是循环的key
        for (int i = 0; i < 26; i++) {
            // 第二层循环，其实是循环得到的值，要循环几次
            // 比如a:2,b:5,c:0 说明a要循环2次，下面这个循环就是这个意义
            // 要注意这里条件minFreq[i]，不是minFreq[j]，j是次数，i才是要输出的字符
            for(int j = 0; j < minFreq[i]; j++) {
                //  i + 'a' 1，2，3 → ASCII码
                // (char)(i + 'a') ASCII码 → 字符 ※数字转字符需要括起来
                // String.valueOf((char)(i + 'a'))　字符(char) → 字符串<String>
                res.add(String.valueOf((char)(i + 'a')));
            }
        }
        return res;
    }
}
```

看到答案里有一个Python一行流

```python
class Solution:
    def commonChars(self, A: List[str]) -> List[str]:
        return reduce(lambda x, y: x&y, map(Counter, A)).elements()
```

## [LeetCode -1370. 上升下降字符串](https://leetcode-cn.com/problems/increasing-decreasing-string/)

这一题，读题是最难的。

```
给你一个字符串 s ，请你根据下面的算法重新构造字符串：

从 s 中选出 最小 的字符，将它 接在 结果字符串的后面。
从 s 剩余字符中选出 最小 的字符，且该字符比上一个添加的字符大，将它 接在 结果字符串后面。
重复步骤 2 ，直到你没法从 s 中选择字符。
从 s 中选出 最大 的字符，将它 接在 结果字符串的后面。
从 s 剩余字符中选出 最大 的字符，且该字符比上一个添加的字符小，将它 接在 结果字符串后面。
重复步骤 5 ，直到你没法从 s 中选择字符。
重复步骤 1 到 6 ，直到 s 中所有字符都已经被选过。
在任何一步中，如果最小或者最大字符不止一个 ，你可以选择其中任意一个，并将其添加到结果字符串。
请你返回将 s 中字符重新排序后的 结果字符串 。
示例 1：
输入：s = "aaaabbbbcccc"
输出："abccbaabccba"
解释：第一轮的步骤 1，2，3 后，结果字符串为 result = "abc"
第一轮的步骤 4，5，6 后，结果字符串为 result = "abccba"
第一轮结束，现在 s = "aabbcc" ，我们再次回到步骤 1
第二轮的步骤 1，2，3 后，结果字符串为 result = "abccbaabc"
第二轮的步骤 4，5，6 后，结果字符串为 result = "abccbaabccba"
示例 2：
输入：s = "rat"
输出："art"
解释：单词 "rat" 在上述算法重排序以后变成 "art"
示例 3：
输入：s = "leetcode"
输出："cdelotee"
示例 4：
输入：s = "ggggggg"
输出："ggggggg"
示例 5：
输入：s = "spo"
输出："ops"

提示：
1 <= s.length <= 500
s 只包含小写英文字母。
```

### 思路

最重要的就是要搞懂！！

- 上升
- 下降

![image-20211122152531312](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211122152531312.png)

### 代码实现

```java
class Solution {
    public String sortString(String s) {
        // 新建一个计数数组 key是字母，value是次数
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            counts[c - 'a']++;
        }
        // 走到这里已经计算完毕

        // 结果集
        StringBuilder sb = new StringBuilder();
        // sb 结果集如果一直比s 字符串要小
        // 说明s里的字符串并没有完全转移到sb里
        while (sb.length() < s.length()) {
            // 上升
            for (int i = 0; i < 26; i++) {
                // 如果次数大于0
                if(counts[i] > 0) {
                    sb.append((char)(i + 'a'));
                    counts[i]--;
                }
            }
            // 下降
            for (int i = 25; i >= 0; i--) {
                if(counts[i] > 0) {
                    sb.append((char)(i + 'a'));
                    counts[i]--;
                }
            }
        }
        // 走到这个时候s里应该全部完成
        // sb目前是一个StringBuilder类 StringBuilder → 字符串
        return sb.toString();
    }
}
```

## 双指针

## 【普通快慢指针】

正常情况下1根指针就可以

![image-20211122155329251](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211122155329251.png)

像下面的双指针就各司其职，**快慢指针**

![image-20211122155630450](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211122155630450.png)

还有一种场景，**左右对撞指针**。

![image-20211122155741523](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211122155741523.png)

## [LeetCode - 283. 移动零](https://leetcode-cn.com/problems/move-zeroes/)

```
给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。

示例:
输入: [0,1,0,3,12]
输出: [1,3,12,0,0]

说明:
必须在原数组上操作，不能拷贝额外的数组。
尽量减少操作次数。
```

### 思路

![image-20211122160202557](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211122160202557.png)

其实首先有一个遍历大法，开辟1份临时一样的空间。

如果不为0，就拷贝到里面。如果为0就补充进来。最后把临时数组复制到原来的数组。完成。

但是这样问题1 浪费了额外空间 2 遍历了2次，操作次数多。都不符合题意。

> 所以就有了**快慢指针**。
>
> 快指针用来遍历每一个元素。慢指针用来记录非零元素当前该处于的位置。

![2021-11-22 16-10-50.2021-11-22 16_13_57](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-11-22%2016-10-50.2021-11-22%2016_13_57.gif)

### 代码实现

**初版**

```java
class Solution {
    public void moveZeroes(int[] nums) {
        if (nums.length == 0) return;
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                int temp = nums[fast];
                nums[fast] = nums[slow];
                nums[slow] = temp;

                slow++;
            }
        }
    }
}
```

**优化1**

因为如果这样的情况的话，数组内没有0,比如*[5,8,2,7]*的情况下，那么*fast*,*slow*始终是一致的，那么就会造成无用的判断。

```java
class Solution {
    public void moveZeroes(int[] nums) {
        if (nums.length == 0) return;
        // 初始化慢指针 用来记录0之前的所有元素所在的位置
        int slow = 0;
        // 初始化快指针，并且进行遍历
        for (int fast = 0; fast < nums.length; fast++) {
            // 如果快指针所对应的元素不是0
            if (nums[fast] != 0) {
                // 如果快慢指针不同，才交换。
                if (slow != fast) {
                    int temp = nums[fast];
                    nums[fast] = nums[slow];
                    nums[slow] = temp;
                }
                slow++;
            }
        }
    }
}
// 如果不用for循环，下面的fast其实还可以用while进行循环 

class Solution {
    public void moveZeroes(int[] nums) {
        if (nums.length == 0) return;
        // 初始化慢指针 用来记录0之前的所有元素所在的位置
        int slow = 0;
        // ① 初始化fast
        int fast = 0;
        // ② 如果长度比她小
        while (fast < nums.length) {
            if(nums[fast] != 0) {
                if (slow != fast) {
                    int temp = nums[fast];
                    nums[fast] = nums[slow];
                    nums[slow] = temp;
                }
                slow++;
            }
            // ③这里就是fast要++
            fast++;
        }
        
    }
}
```

**优化2**

上面的优化1其实也没多少优化，主要是这个优化2，采用的不一样的思路。那就是没必要进行交换，直接赋值最好。因为交换进行的对比操作，要比直接进行覆盖的赋值操作效率更高。

![2021-11-22 16-30-45.2021-11-22 16_32_55](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-11-22%2016-30-45.2021-11-22%2016_32_55.gif)

本质上就是，从第一个开始遍历，遇到0就向前走，不是0就复制过去。就这么简单。

```java
class Solution {
    public void moveZeroes(int[] nums) {
        if (nums.length == 0) return;
        // 初始化慢指针 用来记录0之前的所有元素所在的位置
        int slow = 0;
        int fast = 0;
        while (fast < nums.length) {
            if(nums[fast] != 0) {
                if (slow != fast) {
                    nums[slow] = nums[fast];
                }
                slow++;
            }
            fast++;
        }
        for (int i = slow; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}

// 不加上比较。性能还更好，说明赋值语句比比较语句执行快
class Solution {
    public void moveZeroes(int[] nums) {
        if (nums.length == 0) return;
        // 初始化慢指针 用来记录0之前的所有元素所在的位置
        int slow = 0;
        int fast = 0;
        while (fast < nums.length) {
            if(nums[fast] != 0) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        for (int i = slow; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
```

## [LeetCode - 26. 删除有序数组中的重复项](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/)

```
给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。

说明:
为什么返回数值是整数，但输出的答案是数组呢?
请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
你可以想象内部操作如下:

// nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
int len = removeDuplicates(nums);

// 在函数里修改输入数组对于调用者是可见的。
// 根据你的函数返回的长度, 它会打印出数组中 该长度范围内 的所有元素。
for (int i = 0; i < len; i++) {
    print(nums[i]);
}
 
示例 1：
输入：nums = [1,1,2]
输出：2, nums = [1,2]
解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
示例 2：
输入：nums = [0,0,1,1,1,2,2,3,3,4]
输出：5, nums = [0,1,2,3,4]
解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。
 
提示：
0 <= nums.length <= 3 * 104
-104 <= nums[i] <= 104
nums 已按升序排列
```

### 思路

因为数据规模大于10的四次方了，所以不能用复杂度是2次方以上的。

![image-20211123201025505](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211123201025505.png)

首先定义快慢指针的位置。

slow在0号位置。fast在1号位置。为什么呢？因为2个如果都在0号位置，自己和自己比干嘛！

![2021-11-23 20-12-18.2021-11-23 20_13_40](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-11-23%2020-12-18.2021-11-23%2020_13_40.gif)

fast向前走，遇到和自己一样的就一直向前，遇到和自己不一样的，就赋值过去。

![2021-11-23 20-12-39.2021-11-23 20_14_16](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-11-23%2020-12-39.2021-11-23%2020_14_16.gif)

这里遇到2不一样，就把2给赋值过去。

最后结果slow+1就是长度了。

![image-20211123202231630](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211123202231630.png)整体的思路就是这样

- 定义2个快慢指针
- 快指针向前走，慢指针标记已经完成的位置。
- 最后返回slow+1（题目要求是长度）

### 代码实现1

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        int left = 0;
        for(int right = 1; right < nums.length; right++){
            // 如果不同，那么左指针向前走，右边数值赋值给左边
            // 如果相同，那么右指针就继续向前走
            if(nums[left] != nums[right]) {
                nums[++left] = nums[right];
            }
        }
        // 难点2 这里为什么返回left
        // 可以看图，去重后的数组长度就是left最后的index然后+1;
        return left+1;
    }
}
```

### 代码实现2(这个貌似稍快)

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int slow = 0;
        int fast = 1;
        while (fast < nums.length) {
            // 相等就向前走
            if (nums[fast] != nums[slow]) {
                // 注意要先移动，在交换
                // 因为slow没＋之前，指向的是最后一个未重复的
                // 直接这样赋值的话，就会抹掉最后了
                slow++;
                nums[slow] = nums[fast];
            }
            fast++;
        }
        // 此时slow指向的是未重复的，题解需要长度所以+1
        return slow + 1;
    }
}
```

## [LeetCode - 80. 删除有序数组中的重复项 II](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii/)

```
给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 最多出现两次 ，返回删除后数组的新长度。
不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。

说明：
为什么返回数值是整数，但输出的答案是数组呢？
请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
你可以想象内部操作如下:

// nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
int len = removeDuplicates(nums);

// 在函数里修改输入数组对于调用者是可见的。
// 根据你的函数返回的长度, 它会打印出数组中 该长度范围内 的所有元素。
for (int i = 0; i < len; i++) {
    print(nums[i]);
}
 
示例 1：
输入：nums = [1,1,1,2,2,3]
输出：5, nums = [1,1,2,2,3]
解释：函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。 不需要考虑数组中超出新长度后面的元素。
示例 2：
输入：nums = [0,0,1,1,1,1,2,3,3]
输出：7, nums = [0,0,1,1,2,3,3]
解释：函数应返回新长度 length = 7, 并且原数组的前五个元素被修改为 0, 0, 1, 1, 2, 3, 3 。 不需要考虑数组中超出新长度后面的元素。
 
提示：
1 <= nums.length <= 3 * 104
-104 <= nums[i] <= 104
nums 已按升序排列
```

为什么从第3个，也就是index为2的时候开始？

index是2。因为根据题意，重复不超过2个的话，index为2的话，如果和index为0重复的话，那么这个值就是重复的，如果不是重复的，那么肯定也就是不是重复的。这是一个**有序数组**。

![2021-11-23 20-41-46.2021-11-23 20_42_47](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-11-23%2020-41-46.2021-11-23%2020_42_47.gif)

- 快慢指针初始化
- 判断是否重复
- 最后返回slow就可以。（至于为什么不需要+1，这是根据你初始化定义的slow位置决定的）

上一题移动零的时候+1，是因为slow当时在的位置是**已经处理的最后一个区域的位置**，那肯定要先++。如果slow初始化的定义是**最后一个位置的下一个位置**。那么就不用++了。

### 代码实现

```java
// while 写法
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) return nums.length;
        // 初始化slow这个位置是因为前2个要么全部一样，要么全部不一样
        int slow = 2;
        int fast = 2;
        while (fast < nums.length) {
            if(nums[fast] != nums[slow - 2]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }
}
// for 写法
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) return nums.length;
        int slow = 2;
        for (int fast = 2; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow - 2]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }
}
```

**如果这个时候slow的位置是1呢**

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) return nums.length;
        int slow = 1; // 已经处理位置的最后1个位置
        int fast = 2;
        while (fast < nums.length) {
            if(nums[fast] != nums[slow - 1]) {
								slow++;
                nums[slow] = nums[fast];
            }
            fast++;
        }
        return slow + 1;
    }
}
```

评论区写法

能理解出来这个写法也是需要一点点实力的。

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int i = 0;
        for (int n : nums)
            if (i < 2 || n > nums[i-2])
                nums[i++] = n;
        return i;
    }
}
```

顺便学到了

```java
nums[slow] = nums[fast];
slow++;
// 可以直接合并成
nums[slow++] = nums[fast];
```

## 【对撞指针】

## [LeetCode - 27. 移除元素](https://leetcode-cn.com/problems/remove-element/)

````
给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。

说明:
为什么返回数值是整数，但输出的答案是数组呢?
请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
你可以想象内部操作如下:
// nums 是以“引用”方式传递的。也就是说，不对实参作任何拷贝
int len = removeElement(nums, val);
// 在函数里修改输入数组对于调用者是可见的。
// 根据你的函数返回的长度, 它会打印出数组中 该长度范围内 的所有元素。
for (int i = 0; i < len; i++) {
    print(nums[i]);
}
 
示例 1：
输入：nums = [3,2,2,3], val = 3
输出：2, nums = [2,2]
解释：函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。你不需要考虑数组中超出新长度后面的元素。例如，函数返回的新长度为 2 ，而 nums = [2,2,3,3] 或 nums = [2,2,0,0]，也会被视作正确答案。
示例 2：
输入：nums = [0,1,2,2,3,0,4,2], val = 2
输出：5, nums = [0,1,4,0,3]
解释：函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。注意这五个元素可为任意顺序。你不需要考虑数组中超出新长度后面的元素。
 
提示：
0 <= nums.length <= 100
0 <= nums[i] <= 50
0 <= val <= 100
````

这一题和上面的移除零的做法几乎是一样的。

#### 快慢指针

![image-20211123224747519](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211123224747519.png)

```java
// 因为这一题也可以看出来已处理区域和slow是相等的
class Solution {
    public int removeElement(int[] nums, int val) {
        if(nums.length == 0) return 0;
        int slow = 0;
        int fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }
}
```

#### 对撞指针

如果刚好删除的元素特别少，如何提升性能？

![image-20211123225539557](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211123225539557.png)

快慢指针就有问题了，因为目标如果在index第一个or最后一个的时候，效率会特别差。尤其是这种情况

`array=[1,4,2,2,3,4] val = 1` ，这个时候除了index为0，后面的全部要赋值一次。

快慢指针的2个指针都是从一个方向开始的，那么就需要到对撞指针了。

![2021-11-23 22-57-32.2021-11-23 22_57_58](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-11-23%2022-57-32.2021-11-23%2022_57_58.gif)

-  初始化前后2个指针
- right如果小于left就出来
- 如果左边的是目标，那么就把右边的复制过去，然后right向前走（**用于释放最后一个元素的区域，本质就是删除**）
- 否则（不是目标）left就向前走
- 最后返回的是right+1 ，left 也可以。如下图（这个位置才是长度）

↓ 可以看出来此时，right已经大于left，而这个时候的位置是right

![image-20211123230200333](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211123230200333.png)

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        if(nums.length == 0) return 0;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            if (nums[left] == val) {
                nums[left] = nums[right];
                right--;
            } else {
                left++;
            }
        }
        return left; // right + 1 也OK
    }
}
```

这种解法在目标值在靠前后位置，或者目标值很少（只有1个，2个）的时候会效率更高。

## [LeetCode - 344. 反转字符串](https://leetcode-cn.com/problems/reverse-string/)

```
编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。

示例 1：
输入：s = ["h","e","l","l","o"]
输出：["o","l","l","e","h"]
示例 2：
输入：s = ["H","a","n","n","a","h"]
输出：["h","a","n","n","a","H"]
 
提示：
1 <= s.length <= 105
s[i] 都是 ASCII 码表中的可打印字符
```

因为上面说了对撞指针，所以有这一题也可以用**对撞指针**，很经典

![2021-11-23 23-11-01.2021-11-23 23_11_40](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-11-23%2023-11-01.2021-11-23%2023_11_40.gif)

```java
// 使用while写法
class Solution {
    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        // 等于的情况下，走到一起，就是轴心，无需处理
        while (left < right) {
            // 交换
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            // left，right向前走
            left++;
            right--;
        }
    }
}
// 使用for写法
class Solution {
    public void reverseString(char[] s) {
        int n = s.length;
        for (int i = 0; i < n / 2; i++) {
            int j = n - 1 - i;
            char temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }
    }
}
```

## [LeetCode - 125. 验证回文串](https://leetcode-cn.com/problems/valid-palindrome/) 

```
给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
说明：本题中，我们将空字符串定义为有效的回文串。

示例 1:
输入: "A man, a plan, a canal: Panama"
输出: true
解释："amanaplanacanalpanama" 是回文串
示例 2:
输入: "race a car"
输出: false
解释："raceacar" 不是回文串
 
提示：
1 <= s.length <= 2 * 105
字符串 s 由 ASCII 字符组成
```

### 解题思路

- 因为只考虑①**字母**②**数组字符**，③**忽略大小写**。所以不符合条件的字符，要进行过滤。

### 代码实现

```java
class Solution {
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
           	// 忽略左边无效字符 left < right 一定要+
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
          	// 忽略右边无效字符
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
          	// 走到这里很有可能left是大于right的，那就要退出了。所以新的条件↓
          	// 左边小于右边
            if (left < right) {
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
    }
}
```

## [LeetCode - 11. 盛最多水的容器](https://leetcode-cn.com/problems/container-with-most-water/)

```
给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
说明：你不能倾斜容器。

示例 1：
输入：[1,8,6,2,5,4,8,3,7]
输出：49 
解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
示例 2：
输入：height = [1,1]
输出：1
示例 3：
输入：height = [4,3,2,1,4]
输出：16
示例 4：
输入：height = [1,2,1]
输出：2
 
提示：
n == height.length
2 <= n <= 105
0 <= height[i] <= 104
```

![img](https://aliyun-lc-upload.oss-cn-hangzhou.aliyuncs.com/aliyun-lc-upload/uploads/2018/07/25/question_11.jpg)

其实就是求红色线那种体积最大的值。

### 思路

#### 首先有暴力解法

固定第一根柱子，然后看体积。固定第二根柱子，然后看体积。就这样一直下去。。。

```java
public int maxArea(int[] height) {
  // 柱子个数
	int n = height.length;
  int res = 0;
  // 固定1根柱子i
  for (int i = 0; i < n; i++) {
    // 然后接下来继续看下一根柱子
    for (int j = i + 1; j < n; j++) {
      // 取最短的那个柱子，宽度就是 j - i
      int area = Math.min(height[i], height[j]) * (j - i);
      res = Math.max(res, area);
    }
  }
  return res;
}
```

但是上面的计算，首先复杂度是n的平方，不符合题意。肯定会超时。

#### 对撞指针

暴力解法里面有重复计算的

在宽度（横向坐标轴）一定的情况下，面积取决于最短的那跟柱子！

![2021-11-24 17-32-45.2021-11-24 17_34_07](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-11-24%2017-32-45.2021-11-24%2017_34_07.gif)

比如上面1-2，1-3...这些是没意义的，直接计算1-9就好。

那么接下来还有比1-9更大的面积吗？有可能，就看柱子的高度了。如果1号的柱子和9号的柱子，1号更低，所以1号向前走。所以移动短的那边，如↓

![image-20211124174238796](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211124174238796.png)

所以基本上思路就有了。

直接上代码

```java
// 暴力解法 超时
public int maxArea(int[] height) {
  // 柱子个数
  int n = height.length;
  int res = 0;
  // 固定1根柱子i
  for (int i = 0; i < n; i++) {
    // 然后接下来继续看下一根柱子
    for (int j = i + 1; j < n; j++) {
      int tempArea = Math.min(height[i], height[j]) * (j - i);
      res = Math.max(tempArea, res);
    }
  }
  return res;
}

// 对撞指针
public int maxArea1(int[] height) {
  int res = 0;
  int left = 0;
  int right = height.length - 1;
  while(left < right) {
    // 取得面积
    int area = Math.min(height[left], height[right]) * (right - left);
    res = Math.max(res,area);
    // 在面积固定的时候，宽度固定的时候
    if (height[left] <= height[right]) {
      left++;
    } else {
      right--;
    }
  }
  return res;
}
```

## 前缀和&前缀积

## [LeetCode - 1480. 一维数组的动态和](https://leetcode-cn.com/problems/running-sum-of-1d-array/)

```
给你一个数组 nums 。数组「动态和」的计算公式为：runningSum[i] = sum(nums[0]…nums[i]) 。
请返回 nums 的动态和。

示例 1：
输入：nums = [1,2,3,4]
输出：[1,3,6,10]
解释：动态和计算过程为 [1, 1+2, 1+2+3, 1+2+3+4] 。
示例 2：
输入：nums = [1,1,1,1,1]
输出：[1,2,3,4,5]
解释：动态和计算过程为 [1, 1+1, 1+1+1, 1+1+1+1, 1+1+1+1+1] 。
示例 3：
输入：nums = [3,1,2,10,1]
输出：[3,4,6,16,17]

提示：
1 <= nums.length <= 1000
-10^6 <= nums[i] <= 10^6
```

### ![image-20211124223255447](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211124223255447.png)

首先就可以想到暴力解法。

### 暴力解法

![2021-11-24 22-34-09.2021-11-24 22_34_43](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-11-24%2022-34-09.2021-11-24%2022_34_43.gif)

首先就是固定一个，然后分别计算。因为暴力解法虽然复杂度是n方，但是因为题意的数据规模是1000，所以OK的

- 指针i固定1个index，指针j计算从i到j所有数字的和。

```java
class Solution {
    public int[] runningSum(int[] nums) {
        int n = nums.length;
        int[] prefixSum = new int[n];
        // 一轮循环
        for (int i = 0; i < n; i++) {
            // 固定i,然后从j开始循环
            int sum = 0;
            for (int j = 0; j <= i; j++) {
                sum += nums[j];
            }
            // 得到一个前缀和的数组
            prefixSum[i] = sum;
        }
        return prefixSum;
    }
}
```

### 动态规划

因为上面的暴力写法会有很多的重复计算，

![2021-11-24 22-42-22.2021-11-24 22_42_36](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-11-24%2022-42-22.2021-11-24%2022_42_36.gif)

所以就有了下面这个解法。动态规划

> 通过中间值推导消除重复计算。动态规划 **prefix[i]  = prefix[i - 1] + num[i] **

![2021-11-24 22-43-03.2021-11-24 22_43_26](https://raw.githubusercontent.com/chihokyo/image_host/develop/2021-11-24%2022-43-03.2021-11-24%2022_43_26.gif)

代码实现

```java
// 从1开始
class Solution {
    public int[] runningSum(int[] nums) {
        int n = nums.length;
        int[] prefixSum = new int[n];
        prefixSum[0] = nums[0];
        // 为什么要从1开始
        // 其实是因为prefixSum[i - 1] + nums[i]的时候i为0的情况
        for (int i = 1; i < n; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i];
        }
        return prefixSum;
    }
}
// 从0开始 注意看改变了3个地方
class Solution {
    public int[] runningSum(int[] nums) {
        int n = nums.length;
        int[] prefixSum = new int[n];
        prefixSum[0] = nums[0];
        // 改变①int i = 0; ②i < n - 1
        // ③prefixSum[i + 1] = prefixSum[i] + nums[i + 1];
        for (int i = 0; i < n - 1; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i + 1];
        }
        return prefixSum;
    }
}
```

感觉对动态规划突然理解了好多。

## [LeetCode - 238. 除自身以外数组的乘积](https://leetcode-cn.com/problems/product-of-array-except-self/)

```
给你一个长度为 n 的整数数组 nums，其中 n > 1，返回输出数组 output ，其中 output[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。

示例:
输入: [1,2,3,4]
输出: [24,12,8,6]
 
提示：题目数据保证数组之中任意元素的全部前缀元素和后缀（甚至是整个数组）的乘积都在 32 位整数范围内。
说明: 请不要使用除法，且在 O(n) 时间复杂度内完成此题。
进阶：
你可以在常数空间复杂度内完成这个题目吗？（ 出于对空间复杂度分析的目的，输出数组不被视为额外空间。）
```

首先这一题有一个好玩的地方，就是不让你使用除法。这个道理很简单，因为把整体全部都乘掉之后，除以自己就可以得到结果了。

那样就没任何意思了。

### 暴力解法

首先暴力解法，就是先计算**自身以外**乘以<u>左边</u>的。在计算乘以<u>右边</u>的。然后<u>左右再次相乘</u>，不就OK啦？

```java
// 暴力解法，超时
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            // 计算左边
            int leftP = 1;
            for (int j = 0; j < i; j++) {
                leftP *= nums[j];
            }
            // 计算右边
            int rightP = 1;
            for (int j = i + 1; j < n; j++) {
                rightP *= nums[j];
            }
            // 左右相乘
            res[i] = leftP * rightP;
        }
        return res;
    }
}
```

于是就需要找规律了。那么可以看出来

![image-20211124232132291](https://raw.githubusercontent.com/chihokyo/image_host/develop/image-20211124232132291.png)

左乘积用的就是 `leftP[2] = left[2 - 1] * num[2 - 1] ` 

右乘积用的就是 `rightP[2] = right[2 + 1] * num[2 + 1] ` 

### 解法1 时间复杂度O（n）

时间复杂度O(n)，空间复杂度O(n)

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] leftProducts = new int[n];
        // 最左边肯定是1
        leftProducts[0] = 1;
        for (int i = 1; i < n; i++) {
            leftProducts[i] = leftProducts[i - 1] * nums[i - 1]; 
        }
        int[] rightProducts = new int[n];
        // 最右边肯定也是1
        rightProducts[n - 1] = 1;
        // 从倒数第2个开始算起
        for (int i = n - 2; i >= 0; i--) {
            rightProducts[i] = rightProducts[i + 1] * nums[i + 1]; 
        }
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = leftProducts[i] * rightProducts[i];
        }
        return res;
    }
}
```

### 解法2 时间复杂度O（n）

时间复杂度O(n)，空间复杂度O(1) 有点难理解

理解起来了，其实和上面那个本质就是。上面是先计算左边，在计算右边，然后左右相乘。是3个数组的空间。

但是完全可以把后2个操作放在一起，先计算左边乘积，然后弄个临时变量，左右相乘的时候就是 左边乘积 * 临时变量。同样可以得到结果。结论就是计算右边的同时，计算结果。

```java
public int[] productExceptSelf2(int[] nums) {
        int n = nums.length;
        // 先将每个元素的左边乘积放在res里
        int[] res = new int[n];
        res[0] = 1;
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        // 每个元素的右边所有元素的乘积存储在一个变量中
        int rightP = 1;
        for (int i = n - 1; i >= 0; i--) {
            // 对于索引 i，左边的乘积为 output[i]，右边的乘积为 rightP
            res[i] = res[i] * rightP;
            // 更新右边乘积
            rightP = rightP * nums[i];
        }

        return res;
    }
```

