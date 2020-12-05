# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x # 数据部分 （大脑
#         self.next = None # 指针部分 指向下一个指针的头部

# 官方给的链表数据类型
class Solution:
    def getKthFromEnd(self, head: ListNode, k: int) -> ListNode:
        # 初始化到头部
        former,latter = head,head
        # 遍历k的长度（此时_临时变量，用完就销毁）
        for _ in range(k):
            # 特殊情况 链表为空
            if not former:return
            # 向下走（链表向下走都这么写）
            # 走k的长度
            former = former.next
        # 循环条件（直到former为空，意思就是走完了链表    
        while former:
            # 前后指针一起走
            former,latter = former.next,latter.next
            # 返回latter 就是剩下的链表了
        return latter