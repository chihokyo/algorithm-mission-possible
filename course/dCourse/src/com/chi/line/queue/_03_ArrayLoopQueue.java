package com.chi.line.queue;


/**
 * 使用静态数组实现循环队列
 * 为什么使用静态呢？根据循环队列的特性 为了省空间
 * 你动态数组可以无限扩容 不符合我文档写的特性
 *
 * @param <T>
 */
public class _03_ArrayLoopQueue<T> implements Queue<T> {
    private T[] data; // 表示静态数组
    private int head; // 表示头
    private int tail; // 表示尾
    private int size; // 表示长度

    // 初始化各种数据
    public _03_ArrayLoopQueue(int capacity) {
        data = (T[]) new Object[capacity];
        head = tail = 0;
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    /**
     * 只要头和尾相接 就一定为空
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    public boolean isFull() {
        if ((tail + 1) % data.length == head) {
            return true;
        }
        return false;
    }

    // 时间复杂度(1)
    @Override
    public void enqueue(T e) {
        if (isFull()) throw new RuntimeException("queue is full");
        data[tail] = e;
        // tail++;错误 重点！这样是不可以的 如果tail越界返回到前面怎么办
        // 所以你要取模！
        tail = (tail + 1) % data.length;
        size++;
    }

    // 时间复杂度(1)
    @Override
    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("queue is empty");
        T res = data[head];
        data[head] = null;
        size--;
        head = (head + 1) % data.length;
        return res;
    }

    @Override
    public T getFront() {
        return data[head];
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Queue 队首[");
        for (int i = 0; i < data.length; i++) {
            res.append(data[i]);
            if (i != data.length - 1) {
                res.append(", ");
            }
        }
        res.append("]");
        return res.toString();
    }
}
