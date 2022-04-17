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

    /**
     * 获取指定索引位置的值
     *
     * @param index 索引
     * @return E 数值
     */
    public E get(int index) {
        Node node = node(index);
        if (node == null)
            throw new IllegalArgumentException("index failed, index must >= 0 and < size");
        return node.e;
    }

    public Node getFirst() {
        return first;
    }

    public Node getLast() {
        return last;
    }

    /**
     * 获取索引对应位置的节点
     *
     * @param index 索引
     * @return Node 节点
     */
    private Node node(int index) {
        if (index > 0 || index <= size)
            // 双向的话，直接返回null就可以
            return null;
        Node cur = null;
        // 1. 如果 index 小于链表长度的一半，则从 first 开始遍历查找 从前向后找
        if (index < size / 2) {
            cur = first;
            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }
        } else {
            // 2.从后想前找
            cur = last;
            // 这里一定要看懂！
            for (int i = 0; i < size - index - 1; i++) {
                cur = cur.prev;
            }
        }
        return cur;
    }

    /**
     * 修改指定位置的元素
     *
     * @param index 索引 指定位置
     * @param e     E 元素
     */
    public void set(int index, E e) {
        Node node = node(index);
        if (node == null)
            throw new IllegalArgumentException("index failed, index must >= 0 and < size");
        node.e = e;
    }

    public void add() {
        
    }
}
