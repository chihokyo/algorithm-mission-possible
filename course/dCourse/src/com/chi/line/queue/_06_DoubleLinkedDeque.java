package com.chi.line.queue;

import java.util.NoSuchElementException;

/**
 * 使用双向链表实现双端队列
 */
public class _06_DoubleLinkedDeque<T> {
    private class Node<T> {
        public Node prev;
        public Node next;
        private T e;

        public Node(T e) {
            this.e = e;
        }
    }

    // 作为一个双端队列 初始化头尾size
    Node<T> head;
    Node<T> tail;
    int size;

    public _06_DoubleLinkedDeque() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    // 插入头 时间复杂度O(1)
    public void addFirst(T e) {
        Node newNode = new Node(e);
        // 如果是空的
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // 插入尾巴 时间复杂度O(1)
    public void addLast(T e) {
        Node newNode = new Node(e);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            // 直接画图最清晰
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // 删除头节点 时间复杂度O(1)
    public T removeFirst() {
        if (isEmpty()) throw new RuntimeException("deque is empty");
        Node<T> delNode = head;
        // 说明此时只有1个元素
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            // 这里理解起来可能会有困难
            head = head.next;
            head.prev = null;
        }
        size--;
        return delNode.e;
    }

    public T removeLast() {
        if (isEmpty()) throw new RuntimeException("deque is empty");
        Node<T> delNode = head;
        // 说明此时只有1个元素
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            // 画图 双向链表图
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return delNode.e;
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return head.e;
    }

    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return tail.e;
    }
}
