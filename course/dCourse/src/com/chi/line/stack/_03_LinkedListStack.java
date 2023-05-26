package com.chi.line.stack;

import com.chi.line.linkedist._02_LinkedList;

public class _03_LinkedListStack<T> implements Stack<T> {

    // 1. 使用已有的链表数据
    private _02_LinkedList<T> linkedList;

    // 2.进行初始化
    public _03_LinkedListStack() {
        linkedList = new _02_LinkedList<>();
    }

    @Override
    public int getSize() {
        return linkedList.getSize();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public void push(T e) {
        linkedList.addFirst(e);
    }

    @Override
    public T pop() {
        return linkedList.removeFirst();
    }

    @Override
    public T peek() {
        return linkedList.get(0);
    }
}










