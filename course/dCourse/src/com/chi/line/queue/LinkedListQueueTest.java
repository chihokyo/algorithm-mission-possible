package com.chi.line.queue;

public class LinkedListQueueTest {
    public static void main(String[] args) {
        _02_LinkedListQueue<Integer> queue = new _02_LinkedListQueue<>();
        queue.enqueue(11);
        System.out.println(queue);

        queue.enqueue(22);
        System.out.println(queue);

        queue.enqueue(33);
        System.out.println(queue);

        queue.dequeue();
        System.out.println(queue);

        queue.dequeue();
        System.out.println(queue);
    }
}
