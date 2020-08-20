# Definition for a binary tree node.
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

# 深度遍历 DFS
class Solution:
    def maxDepth(self, root: TreeNode) -> int:
        # 不成树，直接为0
        if not root: return 0
        # 左子树 + 右子树 + 1
        return max(self.maxDepth(root.left), self.maxDepth(root.right)) + 1

# 广度遍历 BFS
class Solution:
    def maxDepth(self, root: TreeNode) -> int:
        if not root: return 0
        # 初始化队列，计数器
        queue, res = [root], 0
        # 只要队列（树）还有node
        while queue:
            # 初始化临时变量存储下一层节点
            temp = []
            # 遍历所有节点
            for node in queue:
                # 左右添加到临时变量 用来遍历下一层
                if node.left: temp.append(node.left)
                if node.right: temp.append(node.right)
            # 这里最难理解，为什么要重新指向
            # 因为temp存放的是下一层的节点，遍历完的直接踢走的意思
            queue = temp
            res += 1
        return res
    
# 双端队列
from collections import deque
# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def maxDepth(self, root: TreeNode) -> int:
        from collections import deque
        if not root: return 0
        worklist = deque([root])
        num_node_level = 1
        level = 0
        while worklist:
            node = worklist.popleft()
            if node.left:
                worklist.append(node.left)
            if node.right:
                worklist.append(node.right)
            num_node_level -= 1
            if num_node_level == 0:
                level += 1
                num_node_level = len(worklist)
        return level