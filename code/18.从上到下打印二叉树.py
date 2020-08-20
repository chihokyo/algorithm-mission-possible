# Definition for a binary tree node.
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

class Solution:
    def levelOrder(self, root: TreeNode) -> List[int]:
        # 为0 直接跳出
        if not root: return 
        # 初始化 res作为打印层，然后queue先入先出数据作为队列存储数据
        res, queue = [], collections.deque()
        # 根节点拿出来
        queue.append(root)
        # 只要queue里面有节点
        while queue:
            # pop出来最开始的
            node = queue.popleft()
            # 添加node
            res.append(node.val)
            # 如果左边有值
            if node.left:
                # 添加到queue队列
                queue.append(node.left)
            if node.right
                queue.append(node.right)
        return res