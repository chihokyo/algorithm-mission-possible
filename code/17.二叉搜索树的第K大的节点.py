# 深度遍历-中序遍历 左根右  
# Definition for a binary tree node.
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

def inorderLF(self, node):  
    if node is None:  
        return  
    self.inorder(node.lchild)  
    print(node.elem, end=' ')         
    self.inorder(node.rchild) 

# 深度遍历-中序遍历 右根左
def inorderFL(self, node):  
    if node is None:  
        return  
    self.inorder(node.rchild)  
    print(node.elem, end=' ')         
    self.inorder(node.lchild)

#深度遍历解题
class Solution:
    def kthLargest(self, root: TreeNode, k: int) -> int:
        if not root: return
        # 初始化一个stack 用来pop最后 
        stack = []
        res = []
        while root or stack:
            while root:
                stack.append(root)
                root = root.right
            root = stack.pop()
            res.append(root.val)
            if len(res)== k: return res[-1]
            root = root.left
        return
    
# python骚操作
class Solution:
    def kthLargest(self, root: TreeNode, k: int) -> int:
        def helper(root):
            return helper(root.left) + [root.val] + helper(root.right) if root else []
        return helper(root)[-k]