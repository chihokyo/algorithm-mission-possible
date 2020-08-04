"""
从尾到头打印链表
"""

# 补充一个翻转stack的案例

class Stack(object):
    def __init__(self):
        self.stack = []
        self.top = -1
        
    def is_empty(self):
        if self.top == -1:
            return True
        else:
            return False
        
    def push(self, value):
        self.stack.append(value)
        self.top += 1
        
    def pop(self):
        if self.is_empty():
            return None
        value = self.stack.pop()
        self.top -= 1
        return value
    
class Queue(object):
    def __init__(self):
        self.queue = []
        self.front = 0
        self.rear = 0
        
    def is_empty(self):
        if self.front == self.rear:
            return True
        else:
            return False
    
    def enQueue(self, value):
        self.queue.append(value)
        self.rear += 1
 
    def deQueue(self):
        if self.is_empty():
            print("The queue is empty")
            return None
        self.front += 1
        return self.queue[self.front-1]
 
    def get_front(self):
        if self.is_empty():
            return None
        return self.queue[self.front]
 
    def get_rear(self):
        if self.is_empty():
            return None
        return self.queue[self.rear-1]
 
# 翻转
def reverseStack(s):
    q = Queue()
    while not s.is_empty():
        value = s.pop()
        q.enQueue(value)
    while not q.is_empty():
        s.push(q.get_front())
        q.deQueue()
    return s
            
# 测试
s = Stack()
s.push(1)      
s.push(2)      
s.push(3)      
s.push(7)
reverseStack(s)
# 这样打印就是无限循环。
# while not s.is_empty():
#     print(type(s),s)    

# 弹出打印
while not s.is_empty():
    print(s.pop(),s,type(s)) 
        
        
# 定义一个单链表
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None

# 栈方法stack     
# class Solution:
#     def reversePrint(self, head):
#         # 初始化一个栈
#         stack = []
#         # head可以理解成链表 只要链表还不为空
#         while head:
#             # 压栈
#             stack.append(head.val)
#             # 向下走
#             head = head.next
#         # 倒序输出
#         return stack[::-1]
# 递归
class Solution:
    def reversePrint(self, head: ListNode) -> List[int]:
        return self.reversePrint(head.next) + [head.val] if head else []