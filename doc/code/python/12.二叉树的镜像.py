"""
二叉树的镜像
"""

# 递归法

# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def mirrorTree(self, root: TreeNode) -> TreeNode:
        if not root: return
        # 暂存的目的是为了防止下面在右边在递归的时候
        # 找不到left，因为left已经交换过了
        temp = root.left
        root.left = self.mirrorTree(root.right)
        root.right = self.mirrorTree(temp)
        return root
    
# python特殊写法
# 原理，并行赋值的时候右边会打包成一个tuple()在进行操作
# 这里相当于已经给你存储了left
# 貌似这个方法要快一点
class Solution:
    def mirrorTree(self, root: TreeNode) -> TreeNode:
        if not root:return
        root.left,root.right = self.mirrorTree(root.right),self.mirrorTree(root.left)
        return root

class Solution:
    def mirrorTree(self, root: TreeNode) -> TreeNode:
        if not root: return 
        # 全部把二叉树放入stack里
        stack = [root]
        # 只要stack里面不为空，意思就是说没pop完
        while stack:
            # pop出来根节点
            node = stack.pop()
            # pop出来的是包含左右子树的根节点（也就是是一颗树干）
            # [2,4,1,8,9]
            # 如果理解成知识pop出来了一个2就错，应该pop出来了241，这样才能交换
            # 开始进行判断左子树，左子树添加到栈里
            if node.left:
                # 只要有左子树就进行添加到stack里
                stack.append(node.left)
            # 同理
            if node.right:
                stack.append(node.right)
            # 最后进行交换，这里交换的是
            # 这里进行的是4和1的交换 最后也就是成了[2,1,4]
            node.left, node.right = node.right, node.left
        return root