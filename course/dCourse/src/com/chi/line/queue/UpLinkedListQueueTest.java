package com.chi.line.queue;

public class UpLinkedListQueueTest {
    public static void main(String[] args) {
        _05_UpLinkedListQueue<Integer> queue = new _05_UpLinkedListQueue<>();
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
