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
