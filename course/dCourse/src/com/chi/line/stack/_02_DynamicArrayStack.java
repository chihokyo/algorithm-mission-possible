package com.chi.line.stack;

import com.chi.line.array._04_MyArrayResize;

/**
 * 动态数组实现栈
 *
 * @param <T>
 */
public class _02_DynamicArrayStack<T> implements Stack<T> {
    // 这里就用我自己写的动态数组
    // 因为是已经实现过的数组 所以data此时不用写成 data[] 也是数组
    private _04_MyArrayResize<T> data;

    // 2.初始化一个数组充当stack
    public _02_DynamicArrayStack(int capacity) {
        this.data = new _04_MyArrayResize<>(capacity);
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
    public void push(T e) {
        data.addLast(e);
    }

    @Override
    public T pop() {
        // 直接删除掉最后一个
        return data.removeIndex(data.getSize() - 1);
    }

    @Override
    public T peek() {
        return data.get(0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stack:[");
        for (int i = 0; i < data.getSize(); i++) {
            sb.append(data.get(i));
            if (i != data.getSize() - 1) sb.append(",");
        }
        sb.append("] top");
        return sb.toString();
    }
}
