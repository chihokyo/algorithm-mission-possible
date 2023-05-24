package com.chi.line.linkedist;

import com.chi.line.linkedlist.DummyLinkedList;

/**
 * 虚拟节点
 * 因为增加和删除的时候都需要考虑头结点
 * 因为头节点没有prev 所以这里
 */
public class _03_DummyLinkedList<T> {
    private class Node {
        T e;
        Node next;

        public Node(T e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(T e) {
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

    private Node dummyHead;
    private int size;

    public _03_DummyLinkedList() {
        dummyHead = new Node();
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 在任意位置获取元素
     *
     * @param index
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("get Failed, index is illegal");
        }
        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.e;
    }

    /**
     * 修改任意index元素
     *
     * @param index
     */
    public void set(int index, T e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("get Failed, index is illegal");
        }
        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.e = e;
    }

    /**
     * 在任意位置添加元素
     *
     * @param index
     */
    public void add(int index, T e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("get Failed, index is illegal");
        }
        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        // 此时已经找到了prev
        prev.next = new Node(e, prev.next);
        size++;
    }

    public void addFirst(T e) {
        add(0, e);
    }

    public void addLast(T e) {
        add(size, e);
    }

    /**
     * 删除任意index的元素
     *
     * @param index
     * @return
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("get Failed, index is illegal");
        }

        Node pre = dummyHead;
        for (int i = 0; i < index; i++) {
            pre = pre.next;
        }

        Node delNode = pre.next;
        pre.next = delNode.next;
        delNode.next = null;
        size--;
        return delNode.e;
    }

    public T removeFirst() {
        return remove(0);
    }

    public T removeLast() {
        return remove(size - 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node cur = dummyHead.next;
        while (cur != null) {
            // 这里+e,不+e都是可以的 因为上面重写了toString()
            // sb.append(cur.e).append("=>");
            sb.append(cur).append("->");
            cur = cur.next;
        }
        sb.append("null");
        return sb.toString();
    }
}
