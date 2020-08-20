# Definition for a binary tree node.
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

# 迭代 BFS
class Solution:
    def levelOrder(self, root: TreeNode) -> List[List[int]]:
        from collections import deque
        if not root: return []
        #  初始化queue队列 和 res
        queue, res = deque(), []
        # 添加根节点到queue
        queue.append(root)
        # 循环用
        while queue:
            # 临时变量，保存每一层的数据
            temp = []
            # 迭代层数存放res
            for _ in range(len(queue)):
                # 取出node
                node = queue.popleft()
                # 添加到 res
                temp.append(node.val)
                # 左右添加
                if node.left: queue.append(node.left)
                if node.right: queue.append(node.right)
                # 到了这里，那一层应该都循环结束
            # 添加到结果里                
            res.append(temp)
        return res
# 递归
class Solution:
    def levelOrder(self, root: TreeNode) -> List[List[int]]:
        # 初始化结果
        res = []
        if not root: return res
        # 辅助函数用来判定递归出口 & 递归
        # 参数1：树
        # 参数2：第几层
        def helper(root, level):
            # 空 ＜ 1
            if len(res) < level:
            # 添加1个列表
                res.append([])
            # 为什么要建议，是因为index从0开始
            res[level - 1].append(root.val)
            if root.left:
                helper(root.left, level + 1)
            if root.right:
                helper(root.right, level + 1)
                
        helper(root,1) # 从这里开始，放进去树和1表示层数，刚开始把第一层放进去
        return res