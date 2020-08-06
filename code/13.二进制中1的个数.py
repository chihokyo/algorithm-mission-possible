"""
二进制中1的个数
"""

#  位运算& + 移位
class Solution:
    def hammingWeight(self, n: int) -> int:
        # 设置res0 进行与运算。
        # 判断最右边是否为1
        # 等于1 res就加1次 返回到后面就是次数
        res = 0
        # 只要n不为0
        while n:
            # n & 1 的结果要么是0，要么是1
            # 为什么用1 原因如下
            # 10111100
            # 00000001
            # ---&----
            # 00000000
            res += n & 1
            # 判断1次右移一次，直到没有n
            n >>= 1
        return res


# 巧用 n & (n - 1)
class Solution:
    def hammingWeight(self, n: int) -> int:
        res = 0
        # 只要n不为0 bool(0)是False
        while n:
            # 先+1 因为是这时候n肯定不是0，说明里面还有1
            res += 1
            # 再次进行和-1的与预算。
            n &= (n-1)
        return res

# 骚操作


class Solution:
    def hammingWeight(self, n: int) -> int:
        return bin(n).count('1')


class Solution:
    def hammingWeight(self, n: int) -> int:
        return sum(map(int, list(bin(n)[2:])))
