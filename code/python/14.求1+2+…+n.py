"""
求1+2+…+n
"""

# 平均计算乘除法(不符合题意)
class Solution:
    def sumNums(self, n: int) -> int:
        return (1 + n) * n // 2
    
# 迭代(不符合题意)
class Solution:
    def sumNums(self, n: int) -> int:
        res = 0
        # 从0开始也行 但是必须要到n+1 range范围是左闭右开
        for i in range(1,n + 1):
            res += i
        return res

# 递归 (不符合题意)
class Solution:
    def sumNums(self, n: int) -> int:
        if n = 1:
            return 1
        n += sumNums(n - 1)
        return n 
    
# 递归的其实用了就是一个判断if，那么是否有其他方法可以判断
# 这时候就要用到了逻辑运算符的短路效应

class Solution:
    def __init__(self):
        self.res = 0
    def sumNums(self, n: int) -> int:
        n > 1 and self.sumNums(n - 1)
        self.res += n
        return self.res
    
# 骚操作
class Solution:
    def sumNums(self, n: int) -> int:
        return n and (n + self.sumNums(n-1))