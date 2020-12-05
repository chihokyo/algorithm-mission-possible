"""
翻转单词顺序
"""

# 骚操作，不建议
class Solution:
    def reverseWords(self, s: str) -> str:
        # print(s.split())
        return ' '.join(reversed(s.split()))

data = 'hellow nshnsh i am LOVE!!   '
test = Solution()
print(test.reverseWords(data))

# 双指针
class Solution:
    def reverseWords(self, s: str) -> str:
        # 去掉收尾空格
        s = s.strip()
        # 初始化i，j双指针
        i = j = len(s) - 1
        # 初始化结果数组
        res = []
        while i >= 0:
            # 大于0 且 不为空格
            while i >=0 and s[i] !=' ':
                i -= 1
            # 截取对应单词
            # 为什么要+1，是因为索引问题
            # 0, 1，2，3，4，5，6
            # [he lloo]
            # 需要截取lloo那就是s[3,7]
            # 因为是左闭右开，而i当时又在空格的地方
            # 不加的话那就是[2,6]那样就截取不到最后            
            res.append(s[i+1]:j+1)
            while s[i] == ' ':
                i -= 1
                j = i
        return ''.join(res)
    

# 骚操作分步骤版
# 去掉空格 - 切割 - 反转 - 重组
class Solution:
    def reverseWords(self, s: str) -> str:
        s = s.strip()
        # 这里有个区别
        strs = s.split()
        strs.reverse()
        return ' '.join(strs)

data = 'hellow nshnsh i am LOVE!!   '
test = Solution()
print(test.reverseWords(data))