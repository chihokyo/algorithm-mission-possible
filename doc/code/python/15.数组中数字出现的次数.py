# # 自己写的思路
# class Solution:
#     def singleNumbers(self, nums: List[int]) -> List[int]:
#         from collections import Counter
#         dictNum = Counter(nums)
#         res = []
#         for k,v in dictNum.items():
#             if v == 1:
#                 res.append(k)
#         return res
    
# 官方给的分组异或运算思路

# 补充习题
class Solution:
    def singleNumber(self, nums: List[int]) -> int:
        single_number = 0
        for num in nums:
            single_number ^= nums

class Solution:
    def singleNumbers(self, nums: List[int]) -> List[int]:
        ret, index = 0, 0
        for n in nums:
            ret ^= n
        while ret & 1 == 0:
            index += 1
            ret >>= 1
        r1, r2 = 0, 0
        for n in nums:
            if (n >> index) & 1 == 0:
                r1 ^= n
            else:
                r2 ^= n
        return [r1, r2]