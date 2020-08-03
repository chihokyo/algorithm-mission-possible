# # 切片
# class Solution:
#     def reverseLeftWords(self, s: str, n: int) -> str:
#         # 找出需要放到后面的切片
#         return s[n:] + s[:n]

# data = 'hellownshnsh'
# index = 3
# test = Solution()
# print(test.reverseLeftWords(data,index))

# # 字符串转换成列表
# class Solution:
#     def reverseLeftWords(self, s: str, n: int) -> str:
#         # 新建一个猎豹
#         res = []
#         # 遍历n到最后
#         for i in range(n,len(s)):
#             res.append(s[i])
#         # 从前面遍历到n
#         for i in range(n):
#             res.append(s[i])
#         # 为什么要用join，因为这样出来的是一个列表
#         # print(res)
#         # ['w', 'n', 's', 'h', 'n', 's', 'h', 'h', 'e', 'l', 'l', 'o']        
#         return ''.join(res)

# data = 'hellownshnsh'
# index = 5
# test = Solution()
# print(test.reverseLeftWords(data,index))

# # 字符串转换成列表 取余升级版本
# class Solution:
#     def reverseLeftWords(self, s: str, n: int) -> str:
#         res = []
#         for i in range(n, n+len(s)):
#             res.append(s[i % len(s)])
#         return ''.join(res)
# data = 'hellownshnsh'
# index = 5
# test = Solution()
# print(test.reverseLeftWords(data,index))

# # 字符串拼接
# class Solution:
#     def reverseLeftWords(self, s: str, n: int) -> str:
#         res = ''
#         for i in range(n,len(s)):
#             res += s[i]
#         for i in range(n):
#             res += s[i]
#         return res

# data = 'hellownshnsh'
# index = 5
# test = Solution()
# print(test.reverseLeftWords(data,index))

# 字符串拼接 取余升级版本
class Solution:
    def reverseLeftWords(self, s: str, n: int) -> str:
        res = ''
        for i in range(n,n+len(s)):
            res += s[i % len(s)]
        return res
data = 'hellownshnsh'
index = 5
test = Solution()
print(test.reverseLeftWords(data,index))
