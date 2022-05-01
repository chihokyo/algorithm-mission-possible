# class Solution:
#     def spiralOrder(self, matrix):
#         if not matrix:return []
#         # 这里代表左右上下
#         # 左：0，右：总共多少列
#         # 上：0，下：总共多少行
#         l,r,t,b,res = 0,len(matrix[0])-1,0,len(matrix)-1,[]
#         while True:
#             # 从左向右走
#             for i in range(l,r+1):
#                 # 从t为0也就是第一行第一个开始添加[0][0],[1][0],[2][0]
#                 res.append(matrix[t][i])
#             t += 1
#             if t > b:break
#             # 从上向下走
#             # 为什么都要+1是因为上面赋值的时候用的长度，而遍历要用边界
#             for i in range(t,b+1):
#                 #[0][列],[1][列],[2][列],
#                 res.append(matrix[i][r])
#             r -= 1
#             if l > r: break
#             # 从右向左走
#             for i in range(r,l-1,-1):
#                 # [行][0],[行][1],[行][2]
#                 res.append(matrix[b][i])
#             b -= 1
#             if t > b:break
#             # 从下向上走
#             for i in range(b,t-1,-1):
#                 # [0][0],[1][0],[2][0]
#                 res.append(matrix[i][l])
#             l += 1
#             if l > r:break
#         return res

# test = Solution()
# matrix = [[1,2,3],[4,5,6],[7,8,9]]
# print(test.spiralOrder(matrix)) #[1, 2, 3, 6, 9, 8, 7, 4, 5]

class Solution:
    def spiralOrder(self, matrix):
        result = []
        # 不为空
        while matrix:
           # 弹出来最后一个添加到末尾
            result.extend(list(matrix.pop(0)))
            matrix = list(zip(*matrix)) # [(4, 7), (5, 8), (6, 9)]
            matrix.reverse()
        return result

test = Solution()
matrix = [[1,2,3],[4,5,6],[7,8,9]]
print(test.spiralOrder(matrix)) #[1, 2, 3, 6, 9, 8, 7, 4, 5]