package com.chi.line.stack;

import java.util.Stack;

/**
 * 最小栈
 */
public class _155_leetcode {

    // 实现方法1 直接就是遍历
    // 复杂度O(n)
    class MinStack1 {

        private Stack<Integer> stack;

        public MinStack1() {
            stack = new Stack<>();
        }

        public void push(int val) {
            stack.push(val);
        }

        public void pop() {
            stack.pop();
        }

        public int top() {
            return stack.peek();
        }

        // 重点在这里 其实就跟求最小值没啥区别
        public int getMin() {
            int minValue = stack.peek();
            for (Integer n : stack) {
                minValue = Math.min(minValue, n);
            }
            return minValue;
        }
    }

    // 实现方法2
    // 有了辅助栈
    class MinStack2 {

        private Stack<Integer> dataStack;
        private Stack<Integer> minStack;

        public MinStack2() {
            dataStack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int val) {
            // 先push
            dataStack.push(val);
            // 如果此时min为空或者小于等于 都要压栈
            // 记住这里要<= 必须要等于
            // 如果去掉等于的话，可能会出现 dataStack 不为空，但是 minStack 为空了
            // 这样下面的 getMin 就会出了
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }

        public void pop() {
            int val = dataStack.pop();
            // 如果此时minStack是等于你要pop的这个值 那么要弹出的
            // 不然会产生不存在的最小值
            if (val == minStack.peek()) {
                minStack.pop();
            }
        }

        public int top() {
            return dataStack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    // 实现方法3 维护一个Node
    class MinStack3 {

        private Stack<Node> stack;

        public MinStack3() {
            stack = new Stack<>();
        }

        public void push(int x) {
            // 新建一个node
            Node node = new Node();
            node.val = x; // 复制给val是必须的
            // =========重点是如何赋值给min 假设目前x是最小值=========
            int minVal = x; //
            // 如果此时min比你x还小 那么说明min还是最小
            // 为什么不能为空 因为空就不能peek出来了
            // 然后拿peek出stack现在的最小值对比一下
            if (!stack.isEmpty() && stack.peek().min < x) {
                // 此时就求出来了最小值
                minVal = stack.peek().min;
            }
            node.min = minVal;
            // =========一直到这里才是给node赋值完成=========
            stack.push(node); // 此时push的node的min才是最小的min
        }

        public void pop() {
            // pop的是整体
            stack.pop();
        }

        public int top() {
            // 这里是值
            return stack.peek().val;
        }

        public int getMin() {
            // 这里是值
            return stack.peek().min;
        }
    }

    // 辅助节点
    class Node {
        int val;
        int min;

        public Node() {
            this.val = val;
            this.min = min;
        }
    }

    // 实现方法4
    // 使用单向链表实现
    class MinStack4 {
        private NodeLinked dummnyHead;

        public MinStack4() {
            dummnyHead = new NodeLinked();
        }

        public void push(int val) {
            int minVal = val;
            // 找到当前头
            NodeLinked head = dummnyHead.next;
            // 找到了当前最小值
            if (head != null && head.min < minVal) {
                minVal = head.min;
            }
            // 开始进行插入操作
            NodeLinked nodeLinked = new NodeLinked(val, minVal);
            // 这个就是插入操作 画图连线更容易理解
            nodeLinked.next = dummnyHead.next;
            dummnyHead.next = nodeLinked;


        }

        public void pop() {
            NodeLinked head = dummnyHead.next;
            // 删除表头
            if (head != null) dummnyHead.next = head.next;
            head.next = null;
        }

        public int top() {
            return dummnyHead.next.val;
        }

        public int getMin() {
            return dummnyHead.next.min;
        }
    }

    // 辅助单向链表
    class NodeLinked {
        int val;
        int min;
        NodeLinked next;

        public NodeLinked() {

        }

        public NodeLinked(int val, int min) {
            this.val = val;
            this.min = min;
        }
    }

}
