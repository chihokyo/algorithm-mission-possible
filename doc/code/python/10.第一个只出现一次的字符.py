# 哈希表
class Solution:
    def firstUniqChar(self, s: str) -> str:
        dic = {}
        for c in s:
            dic[c] = not c in dic
        for c in s:
            if dic[c]: 
                return c
        return ' '

# 有序哈希表（3.6之后python字典都是有序的
# 所以上面的方法也可以写成
class Solution:
    def firstUniqChar(self, s: str) -> str:
        dic = {}
        for c in s:
            dic[c] = not c in dic
        for k, v in dic.items():
            if v:
                return k
        return ' '

# 如果字典不是有序的话，那么就可以使用
# collections.OrderedDict()
class Solution:
    def firstUniqChar(self, s: str) -> str:
        dic = collections.OrderedDict()
        for c in s:
            dic[c] = not c in dic
        for k, v in dic.items():
            if v:
                return k
        return ' '

# Python下的1行流
import collections
class Solution:
    def firstUniqChar(self, s: str) -> str:
        A = [ch for ch, n in collections.Counter(s).items() if n == 1]
        return A[0] if A else " "