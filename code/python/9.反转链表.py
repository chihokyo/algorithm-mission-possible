# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None

# 双指针
class Solution:
    def reverseList(self, head: ListNode) -> ListNode:
        # 初始化 pre，cur
        pre = None
        cur = head
        # 只要不为空
        while cur:
            # 初始化 temp指向下一个
            temp = cur.next
            # 下一个指向pre
            cur.next = pre
            # pre和cur这时候在一起
            pre = cur
            # cur 指向temp
            cur = temp
        # 返回的pre就是最后一个，但链表箭头已经全部改了    
        return pre

# 递归
class Solution:
    def reverseList(self, head: ListNode) -> ListNode:
        if head == None or head.next == None:
            return head
        cur = self.reverseList(head.next)
        # 这里比较难理解，可以
        # 1->2->3->4->5 cur：5 head：4：head.next.next：None（空
        head.next.next = head
        # 递归条件在这里
        head.next = None
        # 每一层递归都返回cur，那就是最后一个节点
        return cur

# 普通翻转
class Solution:
    def reverseList(self, head: ListNode) -> ListNode:
        cur = head
        node_list = []
        #遍历单链表
        while cur:
            node_list.append(cur.val)
            cur = cur.next
        node_list = node_list[::-1] 
        #遍历单链表，重新赋值
        cur = head
        i = 0 
        while cur:
            cur.val = node_list[i]
            cur = cur.next
            i +=1
        return head