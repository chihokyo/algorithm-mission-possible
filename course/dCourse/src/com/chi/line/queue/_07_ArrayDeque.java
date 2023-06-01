package com.chi.line.queue;

import java.util.NoSuchElementException;

public class _07_ArrayDeque<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] array;
    private int size;
    private int front;
    private int rear;

    public _07_ArrayDeque() {
        // 初始化
        array = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
        front = 0;
        rear = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T element) {
        if (size == array.length) {
            resizeArray();
        }
        // 这里使用了循环
        front = (front - 1 + array.length) % array.length;
        array[front] = element;
        size++;
    }

    public void addLast(T element) {
        if (size == array.length) {
            resizeArray();
        }
        array[rear] = element;
        rear = (rear + 1) % array.length;
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        T removed = array[front];
        array[front] = null;
        front = (front + 1) % array.length;
        size--;
        return removed;
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        rear = (rear - 1 + array.length) % array.length;
        T removed = array[rear];
        array[rear] = null;
        size--;
        return removed;
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return array[front];
    }

    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return array[(rear - 1 + array.length) % array.length];
    }

    private void resizeArray() {
        T[] newArray = (T[]) new Object[array.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(front + i) % array.length];
        }
        array = newArray;
        front = 0;
        rear = size;
    }
}
