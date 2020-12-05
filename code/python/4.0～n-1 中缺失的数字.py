# # 二分法
# class Solution:
#     def missingNumber(self, nums: List[int]) -> int:
#         i, j = 0, len(nums) - 1
#         while i <= j:
#             m = (i + j) // 2
#             if nums[m] == m:
#                 i = m + 1
#             else:
#                 j = m - 1
#         return i

# nums = [0,1,3,4]
# test = Solution()
# print(test.missingNumber(nums))

# 遍历
class Solution:
    def missingNumber(self, nums) -> int:
       # 这个应该就是为了排除[0]这个情况
        if len(nums) == (nums[-1] + 1):
            return nums[-1] + 1
        # 长度是n-1这个一定不能忘
        # 这里写 for i in range(len(nums)-1)是错误的！！
        # 因为不能判断这时候整个数组都是正确的连续的
        for i in range(nums[-1]):
            if i != nums[i]:
                return i

nums = [0]
test = Solution()
print(test.missingNumber(nums))            