package com.chin._05._2;

public class _2_add_two_numbers {

    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 虚拟链表头
        ListNode dummy = new ListNode();
        // 默认指向cur
        ListNode cur = dummy;
        int carry = 0;
        // 只要l1和l2这俩链表没有null
        while (l1 != null || l2 != null) {
            // 判断不为空就取值
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            int sum = x + y + carry;
            // 这里要注意，新建的节点是取模最后1位，并且是指向cur.next
            cur.next = new ListNode(sum % 10);
            cur = cur.next; // 这里新建只有cur要向前进
            carry = sum / 10;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        if (carry != 0) cur.next = new ListNode(carry);
        return dummy.next;
    }
}
