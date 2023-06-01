package com.chi.line.queue;

/**
 * 链表优化之后的队列
 *
 * @param <T>
 */
public class _05_UpLinkedListQueue<T> implements Queue<T> {

    private class Node {
        private Node next;
        private T e;

        public Node(T e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(T e) {
            this(e, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public _05_UpLinkedListQueue() {
        head = tail = null;
        size = 0;
    }


    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // 入队操作
    @Override
    public void enqueue(T e) {
        Node newNode = new Node(e);
        // 要分情况，队列为空和不为空
        if (tail == null) {
            tail = newNode;
            head = tail;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("queue is null");
        // 出队要分情况，队列只有1个和多个
        // 如何判断只有1个呢？
        Node delNode = head;
        head = head.next;
        // 如果此时队列只有1个元素，那么tail也要指向null
        if (head == null) tail = null;
        // 这一步很重要
        delNode.next = null;
        size--;
        return delNode.e;
    }

    @Override
    public T getFront() {
        if (isEmpty()) throw new RuntimeException("queue is null");
        return head.e;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Queue: 队首[");
        Node cur = head;
        while (cur != null) {
            sb.append(cur.e + "->");
            cur = cur.next;
        }
        sb.append("null ]");
        return sb.toString();
    }
}
