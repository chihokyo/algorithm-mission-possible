package com.chi.line.queue;

public class ArrayQueueTest {
    public static void main(String[] args) {
        _01_ArrayQueue<Integer> queue = new _01_ArrayQueue<>();
        queue.enqueue(10);
        System.out.println(queue);

        queue.enqueue(20);
        System.out.println(queue);

        queue.enqueue(30);
        System.out.println(queue);

        queue.dequeue();
        System.out.println(queue);

        queue.dequeue();
        System.out.println(queue);
    }
}
