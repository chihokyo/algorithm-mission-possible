package com.chi.line.linkedist;

public class DoubleLinkedList<E> {
    // 1. 内部节点类
    private class Node {
        E e;
        Node prev;
        Node next;

        public Node(Node prev, E e, Node next) {
            this.prev = prev;
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(null, e, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    // 2. 初始化
    private Node first;
    private Node last;
    private int size;

    public DoubleLinkedList() {
        first = last = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
