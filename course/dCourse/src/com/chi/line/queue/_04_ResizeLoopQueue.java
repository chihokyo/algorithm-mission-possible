package com.chi.line.queue;

import java.util.Arrays;

/**
 * 这里要写一个可以进行扩容缩容的队列
 */
public class _04_ResizeLoopQueue<T> implements Queue<T> {
    private T[] data;
    private int head;
    private int tail;
    private int size; // 实际长度

    public _04_ResizeLoopQueue() {
        this(10);
    }

    public _04_ResizeLoopQueue(int capacity) {
        data = (T[]) new Object[capacity];
        head = tail = 0;
        size = 0;
    }

    // 这里是实际个数
    @Override
    public int getSize() {
        return size;
    }

    // 获取当前队列容量
    public int getCapacity() {
        // 因为为了判断循环队列是否已满 需要牺牲一个空间
        return data.length - 1;
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }
    // 时间复杂度(1)
    @Override
    public void enqueue(T e) {
        if (isFull()) {
            // 队列满了
            resize(getCapacity() * 2);
        }
        data[tail] = e;
        size++;
        tail = (tail + 1) % data.length;
    }

    // 时间复杂度(1)
    @Override
    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("queue is empty");
        T res = data[head];
        data[head] = null;
        size--;
        head = (head + 1) % data.length;
        if (size == getCapacity() / 4) {
            resize(getCapacity() / 2);
        }
        return res;
    }

    @Override
    public T getFront() {
        return data[head];
    }

    public boolean isFull() {
        if ((tail + 1) % data.length == head) {
            return true;
        }
        return false;
    }


    public void resize(int newCapacity) {
        T[] newData = (T[]) new Object[newCapacity + 1];
        for (int i = 0; i < size; i++) {
            // 这里很难理解 很重要 建议一步步画图
            newData[i] = data[(i + head) % data.length];
        }
        // 初始化为新的扩容后数组
        data = newData;
        head = 0;
        tail = size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Queue : size = %d, capacity = %d\n", size, getCapacity()));
        sb.append("队首[");
        for (int i = head; i != tail; i = (i + 1) % data.length) {
            sb.append(data[i]);
            if ((i + 1) % data.length != tail) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
