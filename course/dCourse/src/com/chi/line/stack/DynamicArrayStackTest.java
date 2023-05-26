package com.chi.line.stack;

public class DynamicArrayStackTest {
    public static void main(String[] args) {
        Stack<Integer> stack = new _02_DynamicArrayStack<>(5);
        stack.push(11);
        System.out.println(stack);
        stack.push(22);
        System.out.println(stack);
        stack.push(33);
        System.out.println(stack);

        stack.pop();
        System.out.println(stack);
        stack.pop();
        System.out.println(stack);
    }
}
