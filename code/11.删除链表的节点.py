"""
删除链表的节点
"""

# 双指针
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None
class Solution:
    def deleteNode(self, head: ListNode, val: int) -> ListNode:
        # 第一个头就是要找的，那么直接抛弃这个节点，直接指向next
        if head.val == val:
            return head.next
        # 初始化pre，cur（设置指针，代表现在和下一个）
        pre,cur = head,head.next
        while cur and cur.val != val:
            # 向下走
            pre = cur
            cur = cur.next
        if cur:
            # 这里cur已经被抛去砍掉了
            pre.next = cur.next
        return head

# 单指针
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None
class Solution:
    def deleteNode(self, head: ListNode, val: int) -> ListNode:
        # 空返回
        if not head:
            return head    
        if head.val == val:
            return head.next
        # 初始化单指针
        cur = head
        # 第二个链表开始
        while cur.next:
            # 下面链表的值
            if cur.next.val == val:
                # 中间直接砍掉 跳过
                cur.next = cur.next.next
                break
            cur = cur.next
        return head