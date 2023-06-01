package com.chi.line.queue;

import java.util.Stack;

public class _jianzhi_09_leetcode {

}

class CQueue {
    private Stack<Integer> stack1;
    private Stack<Integer> stack2;

    public CQueue() {
        this.stack1 = new Stack<>();
        this.stack2 = new Stack<>();
    }

    // 时间复杂度O(1)
    public void appendTail(int value) {
        stack1.push(value);
    }

    // 时间复杂度O(n)
    public int deleteHead() {
        // 这个时候就要判断 如果stack2为空，那么说明你还是要把stack1所有元素都加进去stack2 这样才能出来
        if(stack2.isEmpty()) {
            while (!stack1.isEmpty()) stack2.push(stack1.pop());
        }
        if(stack2.isEmpty()) return -1;
        return stack2.pop();
    }
}
