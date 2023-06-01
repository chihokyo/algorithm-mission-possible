package com.chi.line.queue;

import com.chi.line.array._04_MyArrayResize;

/**
 * 这里使用以前自己实现的【动态数组】来实现队列这个结构
 *
 * @param <T>
 */
public class _01_ArrayQueue<T> implements Queue<T> {

    // 这里使用自己实现的的动态数组

    private _04_MyArrayResize<T> data;

    public _01_ArrayQueue() {
        data = new _04_MyArrayResize<>();
    }

    @Override
    public int getSize() {
        return data.getSize();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }


    /**
     * 入队 时间复杂度O(n)
     *
     * @param e
     */
    @Override
    public void enqueue(T e) {
        data.addLast(e);
    }

    /**
     * 出队 时间复杂度O(1)
     *
     * @return
     */
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

