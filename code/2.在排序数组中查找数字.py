class Solution:
    def search(self, nums: [int], target: int) -> int:
    	i, j = 0, len(nums) - 1
    	# 找到right 
    	while i <= j:
    		m = (i + j) // 2
    		if nums[m] < target:
    			i = m + 1
    		else:
    			j = m - 1
    	# 这里为什么要记住右边界，是因为随着m的变大，i的数值会逐渐大于j
    	right = i
    	# 找到left
    	# 这里是一个判定，如果左边界里面最大的都不是target
    	# 说明左边的数组里面根本不存在target的值 那么也就不用找了
    	if j >= 0 and nums[j] != target:return 0
    	i = 0
    	while i <= j:
    		m = (i + j) // 2
    		if nums[m] < target:
    			i = m + 1
    		else:
    			j = m - 1
    	# 同理，是因为随着m的变小，j的数值会逐渐小于i
    	left = j

    	return right - left - 1

class Solution:
    def search(self, nums: [int], target: int) -> int:
        # 搜索右边界 right
        i, j = 0, len(nums) - 1
        while i <= j:
            m = (i + j) // 2
            if nums[m] <= target: i = m + 1
            else: j = m - 1
        right = i
        # 若数组中无 target ，则提前返回
        if j >= 0 and nums[j] != target: return 0
        # 搜索左边界 left
        i = 0
        while i <= j:
            m = (i + j) // 2
            if nums[m] < target: i = m + 1
            else: j = m - 1
        left = j
        return right - left - 1
