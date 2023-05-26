package com.chi.line.stack;

public interface Stack<T> {

    /**
     * 查看元素个数
     *
     * @return
     */
    int getSize();

    /**
     * 查看是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 压栈
     *
     * @param e
     */
    void push(T e);

    /**
     * 出栈
     *
     * @return
     */
    T pop();


    /**
     * 查询栈顶元素
     *
     * @return
     */
    T peek();
}
