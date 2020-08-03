"""找出数组中重复的数字。
在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。

示例 1：

输入：
[2, 3, 1, 0, 2, 5, 3]
输出：2 或 3 

"""

"""思路1 排序
先进行排序，然后查找重复数字 注意这一题题干写了是任意的数字都可以 所以没必要找出全部的
"""
class Solution:
    def findRepeatNumber(self, nums: List[int]) -> int:
    	nums.sort() # 排序
    	pre = nums[0] # 默认第一个为基准
    	for index in range(1, len(nums)): # 从第二个开始遍历
    		if pre == nums[index]: # 如果第一个和第二个重复的话
    			return pre # 结束
    		pre = nums[index]

""" 思路2 字典
python字典特性不能有重复的 所以看是否有重复
有重复直接return
"""
class Solution:
    def findRepeatNumber(self, nums: List[int]) -> int:
    	ans_dic = {}
    	for i in range(len(nums)):
    		if i not in ans_dic:
    			ans_dic[i] += 1
    		return i
""" 思路3 hash
这个在我一开始的时候我有想到 但是具体并没有写出来。
因为这个数组的长度就是在值的范围内的，所以可以不用开辟多余的空间，自己就是哈希表。
第三种的思路我终于知道了，就是如果说一个数组是这样的
数组：[1,6,5,4,3,2,2,1]
下标：[0,1,2,3,4,5,6,7]
这是原始的一个数组
其实按照正确的hash排列应该是这样 因为这一题数组中的最大数组不超过最大长度-1
这一个数组长度是8 ，那么减1就是7 可以发现确实数组里的数组也没有大于7的，于是我们可以这样设计一个哈希表
数组：[0,1,2,3,4,5,6,7]
下标：[0,1,2,3,4,5,6,7]
↑ 这样就一一对应并且不重复
那么具体怎么操作的
比如
数组：[1,6,5,4,3,2,2,1]
      ↑ 这里下标为0 对应也应该为0，但结果是1，就找下标为1的value 是6
      发现nums[0] = 1 和 nums[1] = 6 不一致，于是就进行交换，交换来去之后会发现中间会有一个重复，重复就return
     [6,1,5,4,3,2,2,1]
     [2,1,2,3,4,5,6,1] ====> 这里需要多次进行排查会发现第一个出现问题的就是2会有重复
"""

class Solution:
    def findRepeatNumber(self, nums: List[int]) -> int:
        n = len(nums)
        for i in range(n):
            while i != nums[i]:
                if nums[i] == nums[nums[i]]:
                    return nums[i]
                nums[nums[i]], nums[i] = nums[i], nums[nums[i]]