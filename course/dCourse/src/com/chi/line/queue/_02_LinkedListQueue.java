package com.chi.line.queue;

import com.chi.line.linkedist._03_DummyLinkedList;

/**
 * 使用以前写过的链表实现队列
 *
 * @param <T>
 */
public class _02_LinkedListQueue<T> implements Queue<T> {
    // 这里使用自己已经实现过的有虚拟头节点的
    private _03_DummyLinkedList<T> data;

    public _02_LinkedListQueue() {
        data = new _03_DummyLinkedList<>();
    }

    @Override
    public int getSize() {
        return data.getSize();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public void enqueue(T e) {
        // 这里
        data.addLast(e);
    }

    @Override
    public T dequeue() {
        return data.removeFirst();
    }

    @Override
    public T getFront() {
        return data.get(0);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Queue 队首[");
        for (int i = 0; i < data.getSize(); i++) {
            res.append(data.get(i));
            if (i != data.getSize() - 1) {
                res.append(", ");
            }
        }
        res.append("]");
        return res.toString();
    }
}
