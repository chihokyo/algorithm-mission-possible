package com.chi.line.linkedist;

/**
 * 粗略的实现一个链表 没有任何增删改查用法
 * 只有简略的长度可以查询是否为空
 */
public class _01_LinkedList<T> {
    // 1 内部节点类
    private class Node {
        T e;
        Node next;

        public Node(T e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(T e) {
            // 可以没有下一个节点 但是一定要有element
            this(e, null);
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    // 2 初始化
    private Node head; // 头节点
    private int size; // 长度

    public _01_LinkedList() {
        head = null; // 初始化的时候可以为空
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

}
