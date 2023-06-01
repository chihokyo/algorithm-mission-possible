package com.chi.line.queue;

public interface Queue<T> {
    /**
     * 查询队列中的元素个数
     *
     * @return
     */
    int getSize();

    /**
     * 判断队列是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 入队
     *
     * @param e
     */
    void enqueue(T e);

    /**
     * 出队
     *
     * @return
     */
    T dequeue();

    /**
     * 查看队首的元素
     *
     * @return
     */
    T getFront();
}
