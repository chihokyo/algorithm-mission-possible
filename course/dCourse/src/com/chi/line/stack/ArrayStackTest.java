package com.chi.line.stack;

public class ArrayStackTest {
    public static void main(String[] args) {
        Stack<Integer> stack = new _01_ArrayStack<>(5);
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
