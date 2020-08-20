## Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

# 层序遍历 双端队列
class Solution:
    def levelOrder(self, root: TreeNode) -> List[List[int]]:
        from collections import deque
        if not root: return []
        # 引入queue 放入队列，新建res列表
        queue, res = deque([root]), []
        # 不为空
        while queue:
            # 新建一个临时存储node
            temp = deque()
            # 循环
            for _ in range(len(queue)):
                # 出来最前面
                node = queue.popleft()
                # 偶数层
                if len(res) % 2 :
                    # 左边进入
                    temp.appendleft(node.val)
                # 奇数层
                else:
                    # 直接添加
                    temp.append(node.val)
                # 接着左右节点
                if node.left: queue.append(node.left)
                if node.right: queue.append(node.right)
            # 适用于每一层循环结束
            # 这时候temp需要转换成列表添加到res里面形成新的列表
            res.append(list(temp))
        return res


# 层序遍历 双端队列 （奇偶层逻辑分离）
class Solution:
    def levelOrder(self, root: TreeNode) -> List[List[int]]:
        from collections import deque
        if not root: return []
        queue, res = deque(), []
        queue.append(root)
        while queue:
            temp = []
            # 奇数层
            for _ in range(len(queue)):
                # 区别点，这里是从左边开始pop
                node = queue.popleft()
                temp.append(node.val)
                if node.left:
                    queue.append(node.left)
                if node.right:
                    queue.append(node.right)
            res.append(temp)
            # 注意这里，如果queue没有了需要break
            if not queue: break
            temp = []
            # 偶数层            
            for _ in range(len(queue)):
                # 区别点 这里是从右边开始pop
                node = queue.pop()
                temp.append(node.val)
                if node.right:
                    # 记住这里要用左边开始添加 也就是队列的前面
                    queue.appendleft(node.right)
                if node.left:
                    queue.appendleft(node.left)
            res.append(temp)
        return res

# 层序遍历 + 列表倒序 左右遍历正常写，右左遍历直接[::-1]切片处理
class Solution:
    def levelOrder(self, root: TreeNode) -> List[List[int]]:
        from collections import deque
        if not root: return []
        queue, res = deque(), []
        queue.append(root)
        while queue:
            temp = []
            for _ in range(len(queue)):
                node = queue.popleft()
                temp.append(node.val)
                if node.left: queue.append(node.left)
                if node.right: queue.append(node.right)
            # 到这里都是和上一个方法几乎一样的 
            # 但是在判断长度的时候用了list切片 + 列表表达式 进行反转
            res.append(temp[::-1] if len(res) % 2 else temp)
        return res