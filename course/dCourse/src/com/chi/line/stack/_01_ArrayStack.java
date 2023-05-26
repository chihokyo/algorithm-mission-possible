package com.chi.line.stack;

import java.util.NoSuchElementException;

/**
 * 这里是使用【静态数组实现栈】
 */
public class _01_ArrayStack<T> implements Stack<T> {
    //1.初始化数组
    T[] data;
    int size;

    public _01_ArrayStack(int capacity) {
        data = (T[]) new Object[capacity];
        this.size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * push就是给数组最后一个增加元素
     * 时间复杂度O(1)
     *
     * @param e
     */
    @Override
    public void push(T e) {
        if (size == data.length) {
            throw new RuntimeException("push failed");
        }
        data[size] = e;
        size++;
    }

    /**
     * pop就是弹出也就是移除最后一个
     * 时间复杂度O(1)
     *
     * @return
     */
    @Override
    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("pop failed");
        T ret = data[size - 1];
        data[size - 1] = null;
        size--;
        return ret;
    }

    /**
     * 时间复杂度O(1)
     *
     * @return
     */
    @Override
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("pop failed");
        return data[size - 1];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stack:[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i != size - 1) sb.append(",");
        }
        sb.append("] top");
        return sb.toString();
    }
}
